package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.constant.*;
import org.bytedesk.jpa.model.*;
import org.bytedesk.jpa.model.Thread;
import org.bytedesk.jpa.repository.*;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.mq.service.ThreadService;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.impl.RouteServiceImpl;
import org.bytedesk.mq.service.redis.*;
import org.bytedesk.rest.controller.v1.BaseController;
import org.bytedesk.rest.service.IpService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.*;

/**
 * 会话
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/thread")
public class ThreadController extends BaseController {

    @Autowired
    AppRepository appRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    QueueRepository queueRepository;

    @Autowired
    WorkGroupRepository workgroupRepository;

    @Autowired
    BrowseRepository browseRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    InviteRepository inviteRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisService redisService;

    @Autowired
    RedisConnectService redisConnectService;

    @Autowired
    RedisQueueService redisQueueService;

    @Autowired
    RedisThreadService redisThreadService;

    @Autowired
    RedisStatisticService redisStatisticService;

    @Autowired
    RouteServiceImpl routeService;

    @Autowired
    ThreadService threadService;

    @Autowired
    UserService userService;

    @Autowired
    IpService ipService;

    /**
     * 访客端发起请求会话
     * 请求工作组会话 + 请求一对一会话
     *
     * FIXME: 离线 以及非工作时间 提示
     *
     * 返回结果代码：
     *
     * 200：请求会话成功-创建新会话
     * 201：请求会话成功-继续进行中会话
     * 202：请求会话成功-排队中
     * 203：请求会话成功-当前非工作时间，请自助查询或留言
     * 204：请求会话成功-当前无客服在线，请自助查询或留言
     * 205：咨询前问卷
     * 206：(FIXME: 为兼容旧版，暂时增加此来代替201，待几个版本之后直接去掉205替换为201)请求会话成功-继续进行中会话
     *
     * -1: 请求会话失败-access token无效
     * -2：请求会话失败-wId不存在
     * -3：被拉入黑名单
     *
     * @return
     */
    @GetMapping("/request")
    public JsonResult request(Principal principal,
                              @RequestParam(value = "wId") String workGroupWid,
                              @RequestParam(value = "type") String requestType,
                              @RequestParam(value = "aId") String agentUid,
                              @RequestParam(value = "client") String client) {

        logger.info("workGroupWid: {}, requestType {}, agentUid {}, client: {}",
                workGroupWid, requestType, agentUid, client);

        // 检查是否指定客服
        if (requestType.equals(TypeConsts.THREAD_REQUEST_TYPE_APPOINTED)) {

            return appoint(principal, agentUid, client);

        } else {

            return workGroup(principal, workGroupWid, client);
        }
    }


    /**
     * TODO: 电商客服，携带商品信息等
     *
     * @param principal
     * @return
     */
    @GetMapping("/request/shop")
    public JsonResult requestShop(Principal principal) {

        JsonResult jsonResult = new JsonResult();


        return jsonResult;
    }


    /**
     * 指定坐席
     *
     * @param principal
     * @param agentUid
     * @param client
     * @return
     */
    @GetMapping("/appoint")
    public JsonResult appoint(Principal principal,
                              @RequestParam(value = "aId") String agentUid,
                              @RequestParam(value = "client") String client) {

        logger.info("agentUid {}, client: {}", agentUid, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 检查访客是否存在
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            // 检查是否被拉入黑名单
            Optional<Block> blockOptional = blockRepository.findByBlockedUser(visitorOptional.get());
            if (blockOptional.isPresent()) {
                jsonResult.setMessage("您已经被禁言，请稍后访问");
                jsonResult.setStatus_code(-3);
                return jsonResult;
            }

            // 获取系统用户
            User notificationUser = userService.getNotificationUser();

            // 指定客服
            Optional<User> agentOptional = userRepository.findByUid(agentUid);

            // 首先查找是否已经存在进行中的会话，如果没有则创建
            Thread thread = threadService.getAppointThread(visitorOptional.get(), agentOptional.get());

            // 检查指定客服是否在线
            if (redisConnectService.isConnectedAgent(agentUid)) {

                // 最新一条消息是否是默认欢迎语，如果是，则不重复插入，否则插入
                Optional<Message> messageOptional = messageRepository.findFirstByThreadAndTypeOrderByIdDesc(thread, TypeConsts.MESSAGE_TYPE_NOTIFICATION_THREAD);
                Message message;
                if (messageOptional.isPresent()) {

                    message = messageOptional.get();
                } else {

                    message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(thread);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_THREAD);
                    message.setContent(agentOptional.get().getWelcomeTip());
                    message.setUser(notificationUser);
                    messageRepository.save(message);
                }

                // 通知客服
                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                // 返回结果
                jsonResult.setMessage("开始新会话");
                jsonResult.setStatus_code(200);
                jsonResult.setData(message);

            } else {
                // 提示留言

                // TODO: 关闭会话thread
                threadService.closeOfflineThread(thread);

                // 判断最后一条，如果是，则不重新插入，直接读取最近一条
                Optional<Message> messageOptional = messageRepository.findFirstByThreadAndTypeOrderByIdDesc(thread, TypeConsts.MESSAGE_TYPE_NOTIFICATION_OFFLINE);
                Message message;
                if (messageOptional.isPresent()) {

                    message = messageOptional.get();
                } else {

                    message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(thread);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_OFFLINE);
                    message.setContent(BdConstants.DEFAULT_AGENT_OFFLINE_TIP);
                    message.setUser(notificationUser);
                    messageRepository.save(message);
                }

                // 无客服可接待访客
                jsonResult.setMessage("指定客服不在线，请自助查询或留言");
                jsonResult.setStatus_code(204);
                jsonResult.setData(message);
            }

            return jsonResult;

        } else {

            jsonResult.setMessage("请求会话失败-access token无效");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 工作组会话
     *
     * @param principal
     * @param workGroupWid
     * @param client
     * @return
     */
    @GetMapping("/workGroup")
    public JsonResult workGroup(Principal principal,
                                @RequestParam(value = "wId") String workGroupWid,
                                @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 检查访客是否存在
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            // 检查是否被拉入黑名单
            // FIXME: 如果IM用户被好友拉入黑名单，此种方法会误杀
//            Optional<Block> blockOptional = blockRepository.findByBlockedUser(visitorOptional.get());
//            if (blockOptional.isPresent()) {
//                jsonResult.setMessage("您已经被禁言，请稍后访问");
//                jsonResult.setStatus_code(-3);
//                return jsonResult;
//            }

            // 获取系统用户
            User notificationUser = userService.getNotificationUser();

            Optional<WorkGroup> workGroupOptional = workgroupRepository.findByWid(workGroupWid);
            // TODO: 优化，不需要每次都从库里面读取
            if (workGroupOptional.isPresent()) {

                // 首先查找是否已经存在进行中的会话，如果没有则创建
                Thread thread = threadService.getWorkGroupThread(visitorOptional.get(), workGroupOptional.get());

                // 插入业务路由，相当于咨询前提问问卷（选择 或 填写表单），询前表单
                if (workGroupOptional.get().isQuestionnaire()) {

                    // 查询最近一条是否是问卷消息，如果是，则不需要重新插入
                    // 判断最后一条，如果是，则不重新插入，直接读取最近一条
                    Optional<Message> messageOptional = messageRepository.findFirstByThreadAndTypeOrderByIdDesc(thread,
                            TypeConsts.MESSAGE_TYPE_QUESTIONNAIRE);
                    Message message;
                    if (messageOptional.isPresent()) {

                        message = messageOptional.get();
                    } else {

                        message = new Message();
                        message.setWid(workGroupWid);
                        message.setMid(JpaUtil.randomId());
                        message.setClient(ClientConsts.CLIENT_SYSTEM);
                        message.setThread(thread);
                        message.setType(TypeConsts.MESSAGE_TYPE_QUESTIONNAIRE);
                        message.setContent(BdConstants.DEFAULT_WORK_GROUP_QUESTIONNAIRE_TIP);
                        message.setQuestionnaire(workGroupOptional.get().getQuestionnaire());
                        message.setUser(notificationUser);
                        messageRepository.save(message);
                    }

                    // 返回调查问卷内容
                    jsonResult.setMessage("调查问卷");
                    jsonResult.setStatus_code(205);
                    jsonResult.setData(message);

                    return jsonResult;
                }

                return routeService.route(workGroupOptional.get(), thread, visitorOptional.get(), notificationUser);

            } else {

                jsonResult.setMessage("请求会话失败-wId不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("请求会话失败-access token无效");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 咨询前问卷答案：
     *  比如大学长业务类型选择完毕之后，通知访客
     *
     * @param principal
     * @return
     */
    @GetMapping("/questionnaire")
    public JsonResult questionnaire(Principal principal,
                                    @RequestParam(value = "tId") String threadTid,
                                    @RequestParam(value = "itemQid") String questionnaireItemItemQid,
                                    @RequestParam(value = "client") String client) {

        logger.info("threadId {}, itemQid {}, client {}", threadTid, questionnaireItemItemQid, client);

        JsonResult jsonResult = new JsonResult();

        // TODO: 优化，不需要每次都从库里面读取
        Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
        Optional<Thread> threadOptional = threadRepository.findByTid(threadTid);

        //
        if (principal != null) {

            //
            if (threadOptional.isPresent()) {

                // TODO：首先根据ip匹配城市，获取城市所对应的分公司，如果ip没有匹配到城市，则根据省份
//                Ip ip = ipService.getIPZone();
                Company company = new Company();

                // TODO: 匹配region的city
//                Optional<Region> regionCityOptional = regionRepository.findFirstByNameContaining(ip.getCity());
//                if (regionCityOptional.isPresent()) {
//
//                    // 过滤相应管理员创建的company
//                    Set<Company> companies = regionCityOptional.get().getCompanies();
//                    Iterator iterator = companies.iterator();
//                    while (iterator.hasNext()) {
//                        Company myCompany = (Company) iterator.next();
//
//                        if (myCompany.getUser().getUid().equals(threadOptional.get().getWorkGroup().getUser().getUid())) {
//                            company = myCompany;
//                        }
//                    }
//
//                } else {
//
//                    // TODO: 匹配city失败，匹配province
//                    Optional<Region> regionOptional = regionRepository.findFirstByNameContaining(ip.getRegion());
//                    if (regionOptional.isPresent()) {
//
//                        // 过滤相应管理员创建的company
//                        Set<Company> companies = regionCityOptional.get().getCompanies();
//                        Iterator iterator = companies.iterator();
//                        while (iterator.hasNext()) {
//                            Company myCompany = (Company) iterator.next();
//
//                            if (myCompany.getUser().getUid().equals(threadOptional.get().getWorkGroup().getUser().getUid())) {
//                                company = myCompany;
//                            }
//                        }
//
//                    } else {

                        // TODO: 均匹配失败，默认分配 北京分公司
                        Optional<Company> companyOptional = companyRepository.findFirstByNameContaining("北京");
                        if (companyOptional.isPresent()) {
                            company = companyOptional.get();
                        }

//                    }
//                }

                Set<WorkGroup> workGroups = workgroupRepository.findByCompany(company);
                if (workGroups.size() > 1) {
                    //
                    Message message = new Message();
                    message.setWid(threadOptional.get().getWorkGroup().getWid());
                    message.setMid(JpaUtil.randomId());
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(threadOptional.get());
                    message.setType(TypeConsts.MESSAGE_TYPE_WORK_GROUP);
                    message.setContent("请选择工作组");
                    message.setWorkGroups(workGroups);
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);

                    // 返回结果
                    jsonResult.setMessage("获取工作组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(message);

                } else if (workGroups.size() == 1) {

                    // 路由通知给一个工作组
                    WorkGroup workGroup = new WorkGroup();
                    Iterator iterator = workGroups.iterator();
                    while (iterator.hasNext()) {
                        workGroup = (WorkGroup) iterator.next();
                    }

                    //
                    return routeService.route(workGroup, threadOptional.get(), visitorOptional.get(), userService.getNotificationUser());

                } else {

                    // 报错
                    jsonResult.setMessage("未找到相应工作组");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData("failed");
                }

            } else {

                jsonResult.setMessage("获取国家失败-会话不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 选择工作组
     *
     * @param principal
     * @param workGroupWid
     * @param client
     * @return
     */
    @GetMapping("/choose/workGroup")
    public JsonResult chooseWorkGroup(Principal principal,
                              @RequestParam(value = "wId") String workGroupWid,
                              @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        // TODO: 优化缓存
        Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
        Optional<WorkGroup> workGroupOptional = workgroupRepository.findByWid(workGroupWid);
        Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);

        //
        if (principal != null) {

            // 首先查找是否已经存在进行中的会话，如果没有则创建
            Thread thread = threadService.getWorkGroupThread(visitorOptional.get(), workGroupOptional.get());

            return routeService.route(workGroupOptional.get(), thread, visitorOptional.get(), userNotification.get());

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 转接会话
     * 注：只能转接一个客服账号
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/transfer")
    @ResponseBody
    public JsonResult transfer(Principal principal, @RequestBody Map map) {

        // 被邀请用户uid
        String uid = (String) map.get("uid");
        // 会话tid
        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 发起转接的客服账号
            Optional<User> fromUserOptional = userRepository.findByUsername(principal.getName());
            if (fromUserOptional.isPresent()) {

                // 被转接的客服账号
                Optional<User> toUserOptional = userRepository.findByUid(uid);
                if (toUserOptional.isPresent()) {

                    // 判断客服账号是否在线
                    if (redisConnectService.isConnectedAgent(toUserOptional.get().getUid())) {

                        // 被转接的会话
                        Optional<Thread> threadOptional = threadRepository.findByTid(tid);
                        if (threadOptional.isPresent()) {

                            // 转接记录
                            Transfer threadTransfer = new Transfer();
                            threadTransfer.settTid(JpaUtil.randomId());
                            threadTransfer.setThread(threadOptional.get());
                            threadTransfer.setFromUser(fromUserOptional.get());
                            threadTransfer.setFromClient(client);
                            threadTransfer.setToUser(toUserOptional.get());
                            transferRepository.save(threadTransfer);

                            // 通知相关客服账号
                            Message message = new Message();
                            message.setMid(JpaUtil.randomId());
                            if (threadOptional.get().getWorkGroup() != null) {
                                message.setWid(threadOptional.get().getWorkGroup().getWid());
                            } else {
                                message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                            }
                            message.setClient(ClientConsts.CLIENT_SYSTEM);
                            message.setThread(threadOptional.get());
                            message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_TRANSFER);
                            message.setContent(BdConstants.DEFAULT_WORK_GROUP_TRANSFER);
                            message.setUser(userService.getNotificationUser());
                            message.setTransfer(threadTransfer);
                            messageRepository.save(message);
                            //
                            rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                            // 返回结果
                            jsonResult.setMessage("转接会话成功");
                            jsonResult.setStatus_code(200);
                            jsonResult.setData(threadTransfer);

                        } else {

                            jsonResult.setMessage("会话tid不存在");
                            jsonResult.setStatus_code(-4);
                            jsonResult.setData(false);
                        }

                    } else {

                        jsonResult.setMessage("客服账号" + toUserOptional.get().getRealName() + "不在线");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(false);
                    }

                } else {

                    jsonResult.setMessage("被转接客服账号不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

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
     * 转接会话给某个工作组/技能组
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/transfer/workGroup")
    @ResponseBody
    public JsonResult transferWorkGroup(Principal principal, @RequestBody Map map) {

        // 会话tid
        String tid = (String) map.get("tid");
        // 被转接工作组
        String wid = (String) map.get("wid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<WorkGroup> workGroupOptional = workgroupRepository.findByWid(wid);
            //
            if (threadOptional.isPresent() && workGroupOptional.isPresent()) {
                //
                User visitor = threadOptional.get().getVisitor();
                User notificationUser = userService.getNotificationUser();
                //
                return routeService.route(workGroupOptional.get(), threadOptional.get(), visitor, notificationUser);

            } else {

                jsonResult.setMessage("转接失败-tid或者wid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        }  else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 接受转接会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/transfer/accept")
    @ResponseBody
    public JsonResult transferAccept(Principal principal, @RequestBody Map map) {

        String tTid = (String) map.get("ttid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 被转接客服
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 转接记录
                Optional<Transfer> threadTransferOptional = transferRepository.findByTTid(tTid);
                if (threadTransferOptional.isPresent()) {

                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);

                    // 修改thread会话主agent客服
                    threadTransferOptional.get().getThread().setAgent(userOptional.get());
                    threadTransferOptional.get().getThread().getAgents().remove(threadTransferOptional.get().getFromUser());
                    threadTransferOptional.get().getThread().getAgents().add(threadTransferOptional.get().getToUser());

                    // 更新转接记录
                    threadTransferOptional.get().setToClient(client);
                    threadTransferOptional.get().setAccepted(true);
                    threadTransferOptional.get().setActionedAt(new Date());
                    transferRepository.save(threadTransferOptional.get());

                    // 通知相关客服账号
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    if (threadTransferOptional.get().getThread().getWorkGroup() != null) {
                        message.setWid(threadTransferOptional.get().getThread().getWorkGroup().getWid());
                    } else {
                        message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    }
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(threadTransferOptional.get().getThread());
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_TRANSFER_ACCEPT);
                    message.setContent(BdConstants.DEFAULT_WORK_GROUP_TRANSFER_ACCEPT);
                    message.setUser(userNotification.get());
                    message.setTransfer(threadTransferOptional.get());
                    messageRepository.save(message);

                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("接受转接会话成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(threadTransferOptional.get());

                } else {

                    jsonResult.setMessage("会话转接记录不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

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
     * 拒绝转接会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/transfer/reject")
    @ResponseBody
    public JsonResult transferReject(Principal principal, @RequestBody Map map) {

        String tTid = (String) map.get("ttid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 被转接客服
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 转接记录
                Optional<Transfer> threadTransferOptional = transferRepository.findByTTid(tTid);
                if (threadTransferOptional.isPresent()) {

                    // 更新转接记录
                    threadTransferOptional.get().setToClient(client);
                    threadTransferOptional.get().setAccepted(false);
                    threadTransferOptional.get().setActionedAt(new Date());
                    transferRepository.save(threadTransferOptional.get());

                    // TODO: 通知相关客服账号
                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    if (threadTransferOptional.get().getThread().getWorkGroup() != null) {
                        message.setWid(threadTransferOptional.get().getThread().getWorkGroup().getWid());
                    } else {
                        message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    }
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(threadTransferOptional.get().getThread());
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_TRANSFER_REJECT);
                    message.setContent(BdConstants.DEFAULT_WORK_GROUP_TRANSFER_REJECT);
                    message.setUser(userNotification.get());
                    message.setTransfer(threadTransferOptional.get());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("拒绝转接会话成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(threadTransferOptional.get());

                } else {

                    jsonResult.setMessage("会话转接记录不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

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
     * 邀请会话
     * 注：可以同时邀请多个客服加入
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/invite")
    @ResponseBody
    public JsonResult invite(Principal principal, @RequestBody Map map) {

        // 被邀请的所有客服uids
        List<String> uids = (List<String>) map.get("uids");
        // 会话tid
        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 发起邀请的客服账号
            Optional<User> fromUserOptional = userRepository.findByUsername(principal.getName());
            if (fromUserOptional.isPresent()) {

                // 被邀请的会话
                Optional<Thread> threadOptional = threadRepository.findByTid(tid);
                if (threadOptional.isPresent()) {

                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);
                    // 遍历被邀请客服
                    for (int i = 0; i < uids.size(); i++) {

                        // 被邀请的客服账号
                        Optional<User> toUserOptional = userRepository.findByUid(uids.get(i));
                        if (toUserOptional.isPresent()) {

                            // 判断客服账号是否在线
                            if (redisConnectService.isConnectedAgent(toUserOptional.get().getUid())) {

                                // 保存邀请记录
                                Invite threadInvite = new Invite();
                                threadInvite.settIid(JpaUtil.randomId());
                                threadInvite.setType(TypeConsts.INVITE_TYPE_THREAD);
                                threadInvite.setThread(threadOptional.get());
                                threadInvite.setFromUser(fromUserOptional.get());
                                threadInvite.setFromClient(client);
                                threadInvite.setToUser(toUserOptional.get());
                                inviteRepository.save(threadInvite);

                                // TODO: 通知被邀请客服
                                Message message = new Message();
                                message.setMid(JpaUtil.randomId());
                                if (threadOptional.get().getWorkGroup() != null) {
                                    message.setWid(threadOptional.get().getWorkGroup().getWid());
                                } else {
                                    message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                                }
                                message.setClient(ClientConsts.CLIENT_SYSTEM);
                                message.setThread(threadOptional.get());
                                message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_INVITE);
                                message.setContent(BdConstants.DEFAULT_WORK_GROUP_INVITE);
                                message.setUser(userNotification.get());
                                message.setInvite(threadInvite);
                                messageRepository.save(message);
                                //
                                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                                // 返回结果
                                jsonResult.setMessage("邀请会话成功");
                                jsonResult.setStatus_code(200);
                                jsonResult.setData(threadInvite);

                            } else {

                                jsonResult.setMessage("客服账号" + toUserOptional.get().getRealName() + "不在线");
                                jsonResult.setStatus_code(-4);
                                jsonResult.setData(false);
                            }
                        }
                    }

                } else {

                    jsonResult.setMessage("会话tid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

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
     * 接受邀请会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/invite/accept")
    @ResponseBody
    public JsonResult inviteAccept(Principal principal, @RequestBody Map map) {

        String tIid = (String) map.get("tiid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 被邀请客服
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 邀请记录
                Optional<Invite> threadInviteOptional = inviteRepository.findByTIid(tIid);
                if (threadInviteOptional.isPresent()) {

                    // 将客服加入thread会话客服列表
                    threadInviteOptional.get().getThread().getAgents().add(threadInviteOptional.get().getThread().getAgent());

                    // 保存记录
                    threadInviteOptional.get().setToClient(client);
                    threadInviteOptional.get().setAccepted(true);
                    threadInviteOptional.get().setActionedAt(new Date());
                    inviteRepository.save(threadInviteOptional.get());

                    // 通知发起邀请客服
                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    if (threadInviteOptional.get().getThread().getWorkGroup() != null) {
                        message.setWid(threadInviteOptional.get().getThread().getWorkGroup().getWid());
                    } else {
                        message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    }
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(threadInviteOptional.get().getThread());
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_INVITE_ACCEPT);
                    message.setContent(BdConstants.DEFAULT_WORK_GROUP_INVITE_ACCEPT);
                    message.setUser(userNotification.get());
                    message.setInvite(threadInviteOptional.get());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("接受邀请会话成功");
                    jsonResult.setStatus_code(200);

                } else {

                    jsonResult.setMessage("被邀请记录不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

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
     * 拒绝邀请会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/invite/reject")
    @ResponseBody
    public JsonResult inviteReject(Principal principal, @RequestBody Map map) {

        String tIid = (String) map.get("tiid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 被邀请客服
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 邀请记录
                Optional<Invite> threadInviteOptional = inviteRepository.findByTIid(tIid);
                if (threadInviteOptional.isPresent()) {

                    // 保存记录
                    threadInviteOptional.get().setToClient(client);
                    threadInviteOptional.get().setAccepted(false);
                    threadInviteOptional.get().setActionedAt(new Date());
                    inviteRepository.save(threadInviteOptional.get());

                    // TODO: 通知发起邀请客服
                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    if (threadInviteOptional.get().getThread().getWorkGroup() != null) {
                        message.setWid(threadInviteOptional.get().getThread().getWorkGroup().getWid());
                    } else {
                        message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    }
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(threadInviteOptional.get().getThread());
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_INVITE_REJECT);
                    message.setContent(BdConstants.DEFAULT_WORK_GROUP_INVITE_REJECT);
                    message.setUser(userNotification.get());
                    message.setInvite(threadInviteOptional.get());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("拒绝邀请会话成功");
                    jsonResult.setStatus_code(200);

                } else {

                    jsonResult.setMessage("被邀请记录不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

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
     * 退出邀请会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/invite/exit")
    @ResponseBody
    public JsonResult inviteExit(Principal principal, @RequestBody Map map) {

        String tIid = (String) map.get("tiid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 被邀请客服
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {


            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 更新当前会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update/current")
    @ResponseBody
    public JsonResult updateCurrent(Principal principal, @RequestBody Map map) {

        String preTid = (String) map.get("preTid");
        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 更新之前
            Optional<Thread> preThreadOptional = threadRepository.findByTid(preTid);
            if (preThreadOptional.isPresent()) {

                preThreadOptional.get().setCurrent(false);
                threadRepository.save(preThreadOptional.get());
            }

            //
            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            if (threadOptional.isPresent()) {

                // 清空未读
                threadOptional.get().clearUnreadCount();
                threadOptional.get().setCurrent(true);
                threadRepository.save(threadOptional.get());

                // TODO: 通知当前客服其他客户端

                //
                Map<String, String> objectMap = new HashMap<>(2);
                objectMap.put("preTid", preTid);
                objectMap.put("tid", tid);

                // 返回结果
                jsonResult.setMessage("更新当前会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(objectMap);

            } else {

                jsonResult.setMessage("更新当前会话失败-preTid 或 tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 会话置顶
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/mark/top")
    @ResponseBody
    public JsonResult markTop(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (threadOptional.isPresent()) {
                //
                threadOptional.get().getTopSet().add(userOptional.get());
                threadRepository.save(threadOptional.get());

                //
                jsonResult.setMessage("设置会话置顶成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tid);

            } else {

                jsonResult.setMessage("设置会话置顶失败-tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 取消会话置顶
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/unmark/top")
    @ResponseBody
    public JsonResult unMarkTop(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (threadOptional.isPresent()) {

                threadOptional.get().getTopSet().remove(userOptional.get());
                threadRepository.save(threadOptional.get());

                //
                jsonResult.setMessage("取消会话置顶成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tid);

            } else {

                jsonResult.setMessage("取消会话置顶失败-tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 设置会话消息免打扰
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/mark/disturb")
    @ResponseBody
    public JsonResult markDisturb(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (threadOptional.isPresent()) {

                //
                threadOptional.get().getDisturbSet().add(userOptional.get());
                threadRepository.save(threadOptional.get());

                //
                jsonResult.setMessage("设置会话免打扰成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tid);

            } else {

                jsonResult.setMessage("设置会话免打扰失败-tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 取消会话消息免打扰
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/unmark/disturb")
    @ResponseBody
    public JsonResult unMarkDisturb(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (threadOptional.isPresent()) {

                threadOptional.get().getDisturbSet().remove(userOptional.get());
                threadRepository.save(threadOptional.get());

                //
                jsonResult.setMessage("取消会话免打扰成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tid);

            } else {

                jsonResult.setMessage("取消会话免打扰失败-tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 标记会话未读
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/mark/unread")
    @ResponseBody
    public JsonResult markUnread(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (threadOptional.isPresent()) {

                //
                threadOptional.get().getUnreadSet().add(userOptional.get());
                threadRepository.save(threadOptional.get());

                //
                jsonResult.setMessage("标记会话未读成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tid);

            } else {

                jsonResult.setMessage("标记会话未读失败-tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 取消标记会话未读
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/unmark/unread")
    @ResponseBody
    public JsonResult unmarkUnread(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (threadOptional.isPresent()) {

                //
                threadOptional.get().getUnreadSet().remove(userOptional.get());
                threadRepository.save(threadOptional.get());

                //
                jsonResult.setMessage("取消标记会话未读成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tid);

            } else {

                jsonResult.setMessage("取消标记会话未读失败-tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 客户端标记删除，之后不再出现在其会话列表
     * TODO: 在收到新消息之后，取消标记删除
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/mark/deleted")
    @ResponseBody
    public JsonResult markDeleted(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (threadOptional.isPresent()) {

                //
                threadOptional.get().getDeletedSet().add(userOptional.get());
                threadRepository.save(threadOptional.get());

                //
                jsonResult.setMessage("标记会话删除成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tid);

            } else {

                jsonResult.setMessage("标记会话删除失败-tid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 客服端获取24小时内会话
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 获取24小时内的会话
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, -24);

                Pageable pageable = PageRequest.of(0, 100, Sort.Direction.DESC, "timestamp");

                Page<Thread> threadPage = threadRepository.findByTimestampAfterAndAgentsContains(calendar.getTime(), userOptional.get(), pageable);
                List<Thread> agentThreadList = new LinkedList<>(threadPage.getContent());

                // 去重, 客户端仅需要显示某个访客一条会话
                // FIXME: 未考虑一个访客同时进行多个不同工作组会话且同时被同一个客服接待的的情况
                Set<String> visitorUidSet = new HashSet<>(16);
                Iterator iterator = agentThreadList.iterator();
                while (iterator.hasNext()) {
                    Thread thread = (Thread) iterator.next();
                    if (visitorUidSet.contains(thread.getVisitor().getUid())) {
                        iterator.remove();
                    } else {
                        visitorUidSet.add(thread.getVisitor().getUid());
                    }
                }

                // 加载联系人一对一会话
                List<Thread> contactThreadList = threadRepository.findByAgentAndType(userOptional.get(), TypeConsts.THREAD_TYPE_CONTACT);

                // 群组会话
                List<Thread> groupThreadList = threadRepository.findByGroup_MembersContainsAndType(userOptional.get(), TypeConsts.THREAD_TYPE_GROUP);

                //
                Map<String, Object> objectMap = new HashMap<>(3);
                objectMap.put("agentThreads", agentThreadList);
                objectMap.put("contactThreads", contactThreadList);
                objectMap.put("groupThreads", groupThreadList);

                // 返回结果
                jsonResult.setMessage("获取当前会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(objectMap);

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
     * 获取联系人会话
     *
     * @param principal
     * @return
     */
    @GetMapping("/contact")
    public JsonResult contact(Principal principal,
                              @RequestParam(value = "cid") String contactCid,
                              @RequestParam(value = "client") String client) {

        logger.info("cid {}, client {}", contactCid, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            Optional<User> contactOptional = userRepository.findByUid(contactCid);
            if (contactOptional.isPresent()) {

                // 获取消息发送者的thread
                Thread thread = threadService.getContactThread(userOptional.get(), contactOptional.get());
                //
                jsonResult.setMessage("获取联系人会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(thread);

            } else {

                jsonResult.setMessage("");
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
     * 获取群组会话
     *
     * @param principal
     * @return
     */
    @GetMapping("/group")
    public JsonResult group(Principal principal,
                              @RequestParam(value = "gid") String groupGid,
                              @RequestParam(value = "client") String client) {

        logger.info("gid {}, client {}", groupGid, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // group 群组会话
            Thread thread = threadService.getGroupThread(groupGid);
            //
            if (thread != null) {
                //
                jsonResult.setMessage("获取群组会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(thread);

            } else {
                //
                jsonResult.setMessage("获取群组会话失败");
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
     * 获取 历史会话threads
     *
     * FIXME: 仅显示成功接入客服的会话
     *
     * TODO：管理员获取所有历史会话
     * TODO：客服组长获取组内成员所以历史会话
     * TODO：客服账号获取自己的历史会话
     *
     * FIXME: [org.springframework.http.converter.HttpMessageNotWritableException:
     * FIXME: Could not write JSON: (was java.lang.NullPointerException); nested exception is com.fasterxml.jackson.databind.JsonMappingException:
     * FIXME: (was java.lang.NullPointerException) (through reference chain: org.bytedesk.jpa.util.JsonResult["data"]
     * FIXME: ->org.springframework.data.domain.PageImpl["content"]->java.util.Collections$UnmodifiableRandomAccessList[0]
     * FIXME: ->org.bytedesk.jpa.model.Thread["agent"]->org.bytedesk.jpa.model.User["workGroupAdmin"])]
     *
     * @param principal
     * @return
     */
    @GetMapping("/history")
    public JsonResult history(Principal principal,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                Page<Thread> threadPage;

                if (adminOptional.get().isAdmin()) {

                    // 管理员返回所管理的所有会话记录，
//                    threadPage = threadRepository.findByWorkGroup_UserAndClosedAndAgentNotNullOrAgentOrAgent_User(adminOptional.get(),
//                            true, adminOptional.get(), adminOptional.get(), pageable);

                    // 构建动态查询条件
                    Specification specification = (Specification<Thread>) (root, criteriaQuery, criteriaBuilder) -> {

                        List<Predicate> predicateList = new ArrayList<>();

                        // 访客都属于本管理员账号
                        predicateList.add(criteriaBuilder.equal(root.get("visitor").get("subDomain"), adminOptional.get().getSubDomain()));

                        // 会话类型：thread or appointed
                        predicateList.add(criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("type"), TypeConsts.THREAD_TYPE_WORK_GROUP),
                                criteriaBuilder.equal(root.get("type"), TypeConsts.THREAD_TYPE_APPOINTED)
                        ));

                        // 会话已经关闭
                        predicateList.add(criteriaBuilder.equal(root.get("closed"), true));

                        // agent not null
                        predicateList.add(criteriaBuilder.isNotNull(root.get("agent")));

                        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
                    };

                    threadPage = threadRepository.findAll(specification, pageable);

                } else if (adminOptional.get().isWorkGroupAdmin()) {

                    // FIXME: 缺少指定坐席
                    // FIXME: 可能为多个工作组组长
                    // MessageRepository中有Query实例

                    // 客服组长查看工作组内部成员的所有数据
                    threadPage = threadRepository.findByWorkGroup_UsersContainsAndClosedAndWorkGroup_AdminAndAgentNotNull(adminOptional.get(),
                            true, adminOptional.get(),  pageable);




                } else {

                    // 普通用户返回自己的会话历史记录
                    threadPage = threadRepository.findByAgentsContainsAndClosed(adminOptional.get(), true,  pageable);
                }

                // 返回结果
                jsonResult.setMessage("获取历史会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(threadPage);

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
     * 获取监控会话
     *
     * TODO: 获取所有正在进行中的会话，分页，
     * FIXME: 公司管理员可以获取全部会话，小组长可以获取本组内 正在进行会话
     *
     * @param principal
     * @return
     */
    @GetMapping("/monitor")
    public JsonResult monitor(Principal principal,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                 Page<Thread> threadPage = threadRepository.findByAgentsContainsAndClosed(adminOptional.get(), false, pageable);

                jsonResult.setMessage("获取监控会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(threadPage);

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
     * 获取质量检查会话
     *
     * TODO: 获取所有已经结束的会话，分页，
     * FIXME: 公司管理员可以获取全部会话，小组长可以获取本组内 已经结束会话
     *
     * @param principal
     * @return
     */
    @GetMapping("/quality")
    public JsonResult quality(Principal principal,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                 Page<Thread> threadPage = threadRepository.findByAgentsContainsAndClosed(adminOptional.get(), true, pageable);

                jsonResult.setMessage("获取质量检查会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(threadPage);

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
     * 客服关闭会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/agent/close")
    @ResponseBody
    public JsonResult agentClose(Principal principal, @RequestBody Map map) {

        // 会话tid
        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            if (threadOptional.isPresent() &&
                    !threadOptional.get().isClosed()) {

                // 关闭会话
                threadService.agentCloseThread(threadOptional.get());

                // 更新可接待 数量 和当前接待 数量
                Set<User> userSet = threadOptional.get().getAgents();
                Iterator iterator = userSet.iterator();
                while (iterator.hasNext()) {
                    User user = (User) iterator.next();
                    //
                    redisService.increaseAgentIdleCount(user.getUid());
                    //
                    if (threadOptional.get().getWorkGroup() != null) {
                        redisThreadService.decreaseWorkGroupAgentThreadCount(threadOptional.get().getWorkGroup().getWid(), user.getUid());
                    } else {
                        redisThreadService.decreaseAgentThreadCount(user.getUid());
                    }
                }

                // 发送消息通知访客
                Message message = new Message();
                message.setMid(JpaUtil.randomId());
                if (threadOptional.get().getWorkGroup() != null) {
                    message.setWid(threadOptional.get().getWorkGroup().getWid());
                } else {
                    message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                }
                message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_AGENT_CLOSE);
                message.setContent(BdConstants.DEFAULT_WORK_GROUP_AGENT_CLOSE_TIP);
                message.setThread(threadOptional.get());
                message.setUser(userService.getNotificationUser());
                messageRepository.save(message);

                // 通知各个端 (包括所有订阅此thread的端)
                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_THREAD_MESSAGE, message);

                // FIXME: 没有更新？
                // 同步缓存：减少当前会话数
                if (threadOptional.get().getWorkGroup() != null) {
                    redisStatisticService.removeCurrentThread(threadOptional.get().getWorkGroup().getUser().getUid(), threadOptional.get().getTid());
                }

                // 返回结果
                jsonResult.setMessage("关闭会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(true);

            } else if (threadOptional.get().isClosed()){

                jsonResult.setMessage("客服关闭会话失败-会话已经关闭，不能重复关闭");
                jsonResult.setStatus_code(-3);
                jsonResult.setData(false);

            } else {
                jsonResult.setMessage("客服关闭会话失败-未找到会话");
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
     * 访客关闭会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/visitor/close")
    @ResponseBody
    public JsonResult visitorClose(Principal principal, @RequestBody Map map) {

        // 会话tid
        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            if (threadOptional.isPresent() && !threadOptional.get().isClosed()) {

                // 关闭会话
                threadService.visitorCloseThread(threadOptional.get());

                // 更新可接待 数量 和当前接待 数量
                Set<User> userSet = threadOptional.get().getAgents();
                Iterator iterator = userSet.iterator();
                while (iterator.hasNext()) {
                    User user = (User) iterator.next();
                    //
                    redisService.increaseAgentIdleCount(user.getUid());
                    //
                    if (threadOptional.get().getWorkGroup() != null) {
                        redisThreadService.decreaseWorkGroupAgentThreadCount(threadOptional.get().getWorkGroup().getWid(), user.getUid());
                    } else {
                        redisThreadService.decreaseAgentThreadCount(user.getUid());
                    }
                }

                // 发送消息通知访客
                Message message = new Message();
                message.setMid(JpaUtil.randomId());
                if (threadOptional.get().getWorkGroup() != null) {
                    message.setWid(threadOptional.get().getWorkGroup().getWid());
                } else {
                    message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                }
                message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_VISITOR_CLOSE);
                message.setContent(BdConstants.DEFAULT_WORK_GROUP_VISITOR_CLOSE_TIP);
                message.setThread(threadOptional.get());
                message.setUser(userService.getNotificationUser());
                messageRepository.save(message);

                // 通知各个端 (包括所有订阅此thread的端)
                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_THREAD_MESSAGE, message);

                // FIXME: 没有更新？
                // 同步缓存：减少当前会话数
                if (threadOptional.get().getWorkGroup() != null) {
                    redisStatisticService.removeCurrentThread(threadOptional.get().getWorkGroup().getUser().getUid(), threadOptional.get().getTid());
                }

                // 返回结果
                jsonResult.setMessage("访客关闭会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(true);

            } else if (threadOptional.get().isClosed()){

                jsonResult.setMessage("访客关闭会话失败-会话已经关闭，不能重复关闭");
                jsonResult.setStatus_code(-3);
                jsonResult.setData(false);

            } else {
                jsonResult.setMessage("访客关闭会话失败-未找到会话");
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
     * 搜索过滤会话
     *
     * FIXME: 仅显示成功接入客服的会话
     *
     * TODO：管理员获取所有历史会话
     * TODO：客服组长获取组内成员所以历史会话
     * TODO：客服账号获取自己的历史会话
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
                // 构建动态查询条件
                Specification specification = getSpecification(adminOptional.get(), TypeConsts.SPECIFICATION_TYPE_THREAD, nickname, createdAtStart,
                        createdAtEnd, workGroupNickname, agentRealName, client);

                Page<Thread> threadPage = threadRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索历史会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(threadPage);

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


    /**
     * 访客端获取访客所有历史会话
     *
     * @param principal
     * @return
     */
    @GetMapping("/visitor/history")
    public JsonResult visitorHistory(Principal principal,
                                     @RequestParam(value = "page") int page,
                                     @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, 20, Sort.Direction.DESC, "id");
                Page<Thread> threadPage = threadRepository.findByVisitor(visitorOptional.get(), pageable);

                //
                jsonResult.setMessage("获取访客所有历史会话成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(threadPage);

            } else {

                jsonResult.setMessage("获取访客所有历史会话-访客账号不存在");
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


