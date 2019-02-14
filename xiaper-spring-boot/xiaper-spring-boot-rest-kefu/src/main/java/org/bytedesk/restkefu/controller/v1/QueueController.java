package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.constant.*;
import org.bytedesk.jpa.model.Message;
import org.bytedesk.jpa.model.Queue;
import org.bytedesk.jpa.model.Thread;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.repository.MessageRepository;
import org.bytedesk.jpa.repository.QueueRepository;
import org.bytedesk.jpa.repository.ThreadRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.mq.service.redis.RedisQueueService;
import org.bytedesk.mq.service.redis.RedisStatisticService;
import org.bytedesk.rest.controller.v1.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * 排队
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/queue")
public class QueueController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(QueueController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    QueueRepository queueRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RedisQueueService redisQueueService;

    @Autowired
    RedisStatisticService redisStatisticService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 获取当前正在排队访客
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

                // 获取排队中访客
                Page<Queue> queuePage = queueRepository.findByWorkGroup_UsersContainsAndStatus(adminOptional.get(),
                        StatusConsts.QUEUE_STATUS_QUEUING, pageable);

                // 返回结果
                jsonResult.setMessage("获取排队访客成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(queuePage);

            } else {

                jsonResult.setMessage("管理员用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 接入队列访客
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/accept")
    @ResponseBody
    public JsonResult accept(Principal principal, @RequestBody Map map) {

        String qid = (String) map.get("qid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 客服账号
            Optional<User> agentOptional = userRepository.findByUsername(principal.getName());
            Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);
            if (agentOptional.isPresent()) {

                // 队列
                Optional<Queue> queueOptional = queueRepository.findByQid(qid);
                if (queueOptional.isPresent()) {

                    // 判断访客是否已经被其他客服接入
                    if (queueOptional.get().queuing()) {

                        // 从redis中删除排队访客
                        redisQueueService.deleteQueuedWorkGroup(queueOptional.get().getWorkGroup().getWid(), queueOptional.get().getVisitor().getUid());

                        // 接入队列
                        queueOptional.get().setAgent(agentOptional.get());
                        queueOptional.get().setAgentClient(client);
                        queueOptional.get().accept();
                        queueRepository.save(queueOptional.get());

                        // thread
                        Thread thread = queueOptional.get().getThread();
                        thread.setContent(BdConstants.DEFAULT_WORK_GROUP_QUEUE_ACCEPT);
                        thread.setTimestamp(new Date());
                        thread.setUnreadCount(0);
                        thread.setVisitor(queueOptional.get().getVisitor());
                        thread.setAgent(agentOptional.get());
                        thread.getAgents().add(agentOptional.get());
                        thread.setStartedAt(new Date());
                        threadRepository.save(thread);

                        // 保存消息
                        Message message = new Message();
                        message.setMid(JpaUtil.randomId());
                        message.setWid(queueOptional.get().getWorkGroup().getWid());
                        message.setClient(ClientConsts.CLIENT_SYSTEM);
                        message.setThread(thread);
                        message.setQueue(queueOptional.get());
                        message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_QUEUE_ACCEPT);
                        message.setContent(BdConstants.DEFAULT_WORK_GROUP_QUEUE_ACCEPT);
                        message.setUser(userNotification.get());
                        messageRepository.save(message);

                        // 通知访客
                        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);
                        // 通知 工作组内客服
                        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_WORK_GROUP_MESSAGE, message);

                        // 同步缓存, 减少排队数量
                        String agentUid = agentOptional.get().getUid();
                        String adminUid = thread.getWorkGroup().getUser().getUid();
                        redisStatisticService.removeCurrentQueue(adminUid, queueOptional.get().getQid());
                        // 增加接入量
                        redisStatisticService.increaseAcceptQueueCount(adminUid);
                        // 统计等待时长
                        if (queueOptional.get().waitLength() <= 60) {
                            // 1分钟以内
                            redisStatisticService.increaseAgentAcceptCountLt1m(agentUid);
                            redisStatisticService.increaseAcceptCountLt1m(adminUid);
                        } else if (queueOptional.get().waitLength() <= 60 * 3) {
                            // 3分钟以内
                            redisStatisticService.increaseAgentAcceptCountLt3m(agentUid);
                            redisStatisticService.increaseAcceptCountLt3m(adminUid);
                        } else if (queueOptional.get().waitLength() <= 60 * 5) {
                            // 5分钟以内
                            redisStatisticService.increaseAgentAcceptCountLt5m(agentUid);
                            redisStatisticService.increaseAcceptCountLt5m(adminUid);
                        } else {
                            // 大于5分钟
                            redisStatisticService.increaseAgentAcceptCountGt5m(agentUid);
                            redisStatisticService.increaseAcceptCountGt5m(adminUid);
                        }

                        // 返回结果
                        jsonResult.setMessage("接入队列访客成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(message);

                    } else {

                        //
                        jsonResult.setMessage("接入队列失败-已经被其他客服接入");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(false);
                    }

                } else {

                    jsonResult.setMessage("接入队列失败-qid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("接入队列失败-管理员用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("接入队列失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 分页获取历史排队记录
     *
     * @param principal
     * @return
     */
    @GetMapping("/request")
    public JsonResult request(Principal principal,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                //
                Page<Queue> queuePage = queueRepository.findByAgent_User(adminOptional.get(), pageable);

                //
                jsonResult.setMessage("获取排队记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(queuePage);

            } else {

                jsonResult.setMessage("管理员用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }


        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }
        return jsonResult;
    }


    /**
     * 搜索过滤队列
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             //
                             @RequestParam(value = "nickname") String nickname,
                             @RequestParam(value = "createdAtStart") String createdAtStart,
                             @RequestParam(value = "createdAtEnd") String createdAtEnd,
                             @RequestParam(value = "workGroupNickname") String workGroupNickname,
                             @RequestParam(value = "agentRealName") String agentRealName,
                             @RequestParam(value = "client") String client) {

        logger.info("page {}, size {}, nickname {}, createdAtStart {}, createdAtEnd {}, wgNickname {}, agentRealName {}, client {}",
                page, size, nickname, createdAtStart, createdAtEnd, workGroupNickname, agentRealName, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {
                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                Specification specification = getSpecification(adminOptional.get(), TypeConsts.SPECIFICATION_TYPE_QUEUE, nickname, createdAtStart, createdAtEnd,
                        workGroupNickname, agentRealName, client);
                Page<Queue> queuePage = queueRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索排队成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(queuePage);
            }  else {

                jsonResult.setMessage("管理员用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


}
