package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.constant.BdConstants;
import io.xiaper.jpa.constant.MqConsts;
import io.xiaper.jpa.constant.TypeConsts;
import io.xiaper.jpa.model.Message;
import io.xiaper.jpa.model.Rate;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.Thread;
import io.xiaper.jpa.model.WorkGroup;
import io.xiaper.jpa.repository.MessageRepository;
import io.xiaper.jpa.repository.RateRepository;
import io.xiaper.jpa.repository.ThreadRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.redis.RedisStatisticService;
import io.xiaper.rest.controller.v1.BaseController;
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
import java.util.Map;
import java.util.Optional;

/**
 * 满意度
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/rate")
public class  RateController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RateController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisStatisticService redisStatisticService;

    @Autowired
    UserService userService;

    /**
     * 分页获取满意度评分
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
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                Page<Rate> ratePage;
                if (adminOptional.get().isAdmin()) {

                    // ratePage = rateRepository.findByAgentOrAgent_User(adminOptional.get(), adminOptional.get(), pageable);
                    ratePage = rateRepository.findByUser(adminOptional.get(), pageable);

                } else if (adminOptional.get().isWorkGroupAdmin()) {

                    // FIXME: 多个工作组
                    WorkGroup workGroup = (WorkGroup) adminOptional.get().getWorkGroups().toArray()[0];
                    ratePage = rateRepository.findByAgent_WorkGroupsContains(workGroup, pageable);
                } else {

                    ratePage = rateRepository.findByAgent(adminOptional.get(), pageable);
                }

                // 返回结果
                jsonResult.setMessage("获取满意度成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(ratePage);

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
     * 客服发起评价
     *
     * @param principal
     * @return
     */
    @PostMapping("/invite")
    @ResponseBody
    public JsonResult invite(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> agentOptional = userRepository.findByUsername(principal.getName());
            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            if (agentOptional.isPresent() && threadOptional.isPresent()) {

                // TODO: 判断此会话是否为有效会话，比如：聊天记录数必须大于1


                // 判断此会话thread是否已经rate过，防止重复邀请
                if (threadOptional.get().isRated()) {
                    jsonResult.setMessage("邀请评价失败-访客已经评价");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                    return jsonResult;
                }

                // 记录到聊天记录
                Message message = new Message();
                message.setMid(JpaUtil.randomId());
                if (threadOptional.get().getWorkGroup() != null) {
                    message.setWid(threadOptional.get().getWorkGroup().getWid());
                } else {
                    message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                }
                message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_INVITE_RATE);
                message.setClient(client);
                message.setContent(BdConstants.DEFAULT_WORK_GROUP_INVITE_RATE);
                message.setThread(threadOptional.get());
                message.setUser(userService.getNotificationUser());
                messageRepository.save(message);

                // 通知访客端(在此thread内的所有端都会收到，包括客服端)
                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_THREAD_MESSAGE, message);

                // 返回结果
                jsonResult.setMessage("成功发送邀请评价");
                jsonResult.setStatus_code(200);
                jsonResult.setData(message);

            } else {

                jsonResult.setMessage("邀请评价失败-客服账号或者会话tid不存在");
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
     * 访客评价
     *
     * @param principal
     * @return
     */
    @PostMapping("/do")
    @ResponseBody
    public JsonResult rate(Principal principal, @RequestBody Map map) {

//        String uid = (String) map.get("uid");
//        String wid = (String) map.get("wid");
//        String aid = (String) map.get("aid");
//        String type = (String) map.get("type");
        String tid = (String) map.get("tid");
        //
        // 考虑到兼容ios客户端，需要转换为字符串
        String score = (String) map.get("score");
        String note = (String) map.get("note");
        // 考虑到兼容ios客户端，需要转换为字符串
        String invite = (String) map.get("invite");
        String client = (String) map.get("client");
        //
        logger.info("invite: " + invite + " note:" + note);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

//            Optional<User> adminOptional = userRepository.findByUid(uid);
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            if (visitorOptional.isPresent() && threadOptional.isPresent()) {

                // TODO: 判断此会话是否为有效会话，比如：聊天记录数必须大于1

                if (threadOptional.get().getAgent() == null) {
                    jsonResult.setMessage("访客评价-未成功对接客服之前不能评价");
                    jsonResult.setStatus_code(-4);
                    jsonResult.setData(false);
                    return jsonResult;
                }

                // 判断此会话thread是否已经评价过，防止重复多次评价
                if (threadOptional.get().isRated()) {
                    jsonResult.setMessage("访客评价-不能重复评价");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                    return jsonResult;
                }
                threadOptional.get().setRated(true);
                threadRepository.save(threadOptional.get());

                // 记录到满意度评价表
                Rate rate = new Rate();
                rate.setScore(Integer.valueOf(score));
                rate.setNote(note);
                // TODO: 默认5分，允许管理员设置
                rate.setTotal(5);
                rate.setAuto(false);
                rate.setInvite(invite.equals("1") ? true : false);
                rate.setClient(client);
                rate.setThread(threadOptional.get());
                rate.setVisitor(visitorOptional.get());
                //
                User agent = threadOptional.get().getAgent();
                rate.setAgent(agent);
                rate.setUser(agent.getAdmin());
                //
                if (threadOptional.get().getType().equals(TypeConsts.THREAD_REQUEST_TYPE_WORK_GROUP)) {
                    // 工作组会话,同步缓存
                    // 参评数
                    redisStatisticService.increaseRateCount(agent.getAdmin().getUid());
                    if (Integer.valueOf(score) >= 4) {
                        // 满意数
                        redisStatisticService.increaseSatisfyCount(agent.getAdmin().getUid());
                    }
                }
                rateRepository.save(rate);

                // 记录到聊天记录
                Message message = new Message();
                message.setMid(JpaUtil.randomId());
                if (threadOptional.get().getWorkGroup() != null) {
                    message.setWid(threadOptional.get().getWorkGroup().getWid());
                } else {
                    message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                }
                message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_RATE_RESULT);
                message.setContent(BdConstants.DEFAULT_WORK_GROUP_VISITOR_RATE);
                message.setThread(threadOptional.get());
                message.setUser(userService.getNotificationUser());
                messageRepository.save(message);

                // 通知客服端 (包括所有订阅此thread的端)
                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_THREAD_MESSAGE, message);

                // 返回结果
                jsonResult.setMessage("成功评价");
                jsonResult.setStatus_code(200);
                jsonResult.setData(message);

            } else {

                jsonResult.setMessage("访客评价-访客账号不存在");
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
     * 搜索过滤评价
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

                Specification specification = getSpecification(adminOptional.get(), TypeConsts.SPECIFICATION_TYPE_RATE, nickname, createdAtStart, createdAtEnd,
                        workGroupNickname, agentRealName, client);
                Page<Rate> ratePage = rateRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索评价成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(ratePage);
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
