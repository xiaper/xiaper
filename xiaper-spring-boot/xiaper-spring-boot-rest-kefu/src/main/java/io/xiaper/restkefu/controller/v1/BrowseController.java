package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.constant.*;
import io.xiaper.jpa.model.*;
import io.xiaper.jpa.repository.*;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import org.bytedesk.mq.service.BrowseService;
import org.bytedesk.mq.service.ThreadService;
import io.xiaper.rest.controller.v1.BaseController;
import io.xiaper.rest.util.Util;
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

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * 浏览中 访客
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/browse")
public class BrowseController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BrowseController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrowseRepository browseRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    WorkGroupRepository workgroupRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    BrowseInviteRepository browseInviteRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    BrowseService browseService;

    @Autowired
    ThreadService threadService;

    /**
     * 分页获取当前网页浏览记录
     *
     * TODO：访客未离线，浏览网页中...
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
                Page<Browse> browsePage = browseRepository.findByWorkGroup_UserAndActioned(adminOptional.get(), null, pageable);
                //
                jsonResult.setMessage("获取浏览访客成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(browsePage);

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
     * 分页获取网页历史浏览记录
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

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                //
                Page<Browse> browsePage = browseRepository.findByWorkGroup_UserAndActionedNotNull(adminOptional.get(), pageable);

                //
                jsonResult.setMessage("获取历史浏览成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(browsePage);

            } else {

                jsonResult.setMessage("获取历史浏览失败-管理员用户不存在");
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
     * 通知服务器，访客浏览网页中
     *
     * TODO: 获取客户端ip 和 地理位置
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/notify")
    @ResponseBody
    public JsonResult notify(Principal principal, @RequestBody Map map) {

        String adminUid = (String) map.get("adminUid");
        String workGroupWid = (String) map.get("workGroupWid");
        String client = (String) map.get("client");
        String sessionId = (String) map.get("sessionId");
        String referrer = (String) map.get("referrer");
        String url = (String) map.get("url");
        String title = (String) map.get("title");
        String keywords = (String) map.get("keywords");
        String description = (String) map.get("description");

        logger.info("adminUid: {}, workGroupId: {}, client: {}, sessionId {}, referrer: {}, url: {}, ip: {}, title {}, keywords {}, description {}",
                adminUid, workGroupWid, client, sessionId, referrer, url, Util.getIpAddress(request), title, keywords, description);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            Optional<User> adminOptional = userRepository.findByUid(adminUid);
            Optional<WorkGroup> workGroupOptional = workgroupRepository.findByWid(workGroupWid);
            if (adminOptional.isPresent() && workGroupOptional.isPresent()) {

                //
                Url urlObject;
                Optional<Url> urlOptional = urlRepository.findByUrl(url);
                if (!urlOptional.isPresent()) {
                    urlObject = new Url();
                    urlObject.setUrl(url);
                } else {
                    urlObject = urlOptional.get();
                }
                //
                try {
                    urlObject.setTitle(URLDecoder.decode(title, "UTF-8"));
                    urlObject.setKeywords(URLDecoder.decode(keywords, "UTF-8"));
                    urlObject.setDescription(URLDecoder.decode(description, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                urlRepository.save(urlObject);

                Url refererObject;
                Optional<Url> refererOptional = urlRepository.findByUrl(referrer);
                if (!refererOptional.isPresent()) {
                    refererObject = new Url();
                    refererObject.setUrl(referrer);
                    urlRepository.save(refererObject);
                } else {
                    refererObject = refererOptional.get();
                }

                // FIXME：存储浏览轨迹
                Browse browse = new Browse();
                browse.setBid(JpaUtil.randomId());
                browse.setSessionId(sessionId);
                browse.setReferrer(refererObject);
                browse.setUrl(urlObject);
                browse.setWorkGroup(workGroupOptional.get());
                browse.setVisitor(visitorOptional.get());
                browseRepository.save(browse);

                // 通知工作组内的客服
                browseService.notifyBrowseStart(visitorOptional.get(), workGroupOptional.get(), browse);

                //
                jsonResult.setMessage("访客浏览网页通知成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(true);

            } else {

                jsonResult.setMessage("site or workGroup not exist");
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
     * 更新session id
     *
     * @param principal
     * @param map
     */
    @PostMapping("/update/sessionId")
    @ResponseBody
    public JsonResult updateSessionId(Principal principal, @RequestBody Map map) {

        String sessionId = (String) map.get("sessionId");
        String preSessionId = (String) map.get("preSessionId");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            if (sessionId.trim().length() == 0 || preSessionId.trim().length() == 0) {

                jsonResult.setMessage("sessionId 或者 preSessionId 不能为空");
                jsonResult.setStatus_code(-4);
                jsonResult.setData(false);

                return jsonResult;
            }

            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {
                Optional<Browse> browseOptional = browseRepository.findFirstBySessionIdAndVisitor(preSessionId, visitorOptional.get());
                if (browseOptional.isPresent()) {

                    //
                    browseOptional.get().setSessionId(sessionId);
                    browseRepository.save(browseOptional.get());
                    //
                    jsonResult.setMessage("更新sessionId成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(true);

                } else {

                    jsonResult.setMessage("pre session id 不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("username 不存在");
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
     * 邀请访客
     *
     * @param principal
     * @return
     */
    @PostMapping("/invite")
    @ResponseBody
    public JsonResult invite(Principal principal, @RequestBody Map map) {

        //
        String bid = (String) map.get("bid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // TODO: 检查访客是否已经离开

                // TODO: 检查访客是否已经被其他客服接走

                //
                Optional<Browse> browseOptional = browseRepository.findByBid(bid);
                if (browseOptional.isPresent()) {


                    // 保存邀请记录
                    BrowseInvite browseInvite = new BrowseInvite();
                    browseInvite.setbIid(JpaUtil.randomId());
                    browseInvite.setBrowse(browseOptional.get());
                    browseInvite.setFromUser(userOptional.get());
                    browseInvite.setFromClient(client);
                    browseInvite.setToUser(browseOptional.get().getVisitor());
                    browseInvite.setWorkGroup(browseOptional.get().getWorkGroup());
                    browseInviteRepository.save(browseInvite);

                    // 持久化
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setWid(browseOptional.get().getWorkGroup().getWid());
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE);
                    message.setContent(BdConstants.DEFAULT_BROWSE_INVITE);
                    message.setBrowseInvite(browseInvite);

                    //
                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);
                    message.setUser(userNotification.get());
                    messageRepository.save(message);

                    // 通知访客端
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("发送邀请访客会话成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(message);

                } else {

                    jsonResult.setMessage("邀请访客失败-uid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("邀请访客失败-管理员用户不存在");
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
     * 访客接受邀请会话：
     *
     * 直接返回会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/invite/accept")
    @ResponseBody
    public JsonResult inviteAccept(Principal principal, @RequestBody Map map) {

        String biid = (String) map.get("biid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                //
                Optional<BrowseInvite> browseInviteOptional = browseInviteRepository.findByBIid(biid);
                if (browseInviteOptional.isPresent()) {

                    // 记录接入
                    browseInviteOptional.get().getBrowse().accept();
                    // 记录反馈
                    browseInviteOptional.get().setToClient(client);
                    browseInviteOptional.get().setAccepted(true);
                    browseInviteOptional.get().setActionedAt(new Date());
                    browseInviteRepository.save(browseInviteOptional.get());

                    // 创建thread
                    Thread thread = threadService.getWorkGroupThread(visitorOptional.get(), browseInviteOptional.get().getWorkGroup());
                    thread.setAgent(browseInviteOptional.get().getFromUser());
                    thread.getAgents().add(browseInviteOptional.get().getFromUser());
                    threadRepository.save(thread);

                    // 通知用户
                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);

                    // 保存聊天记录
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setWid(browseInviteOptional.get().getWorkGroup().getWid());
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setThread(thread);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE_ACCEPT);
                    message.setContent(BdConstants.DEFAULT_WORK_GROUP_INVITE_ACCEPT);
                    message.setUser(userNotification.get());
                    message.setBrowseInvite(browseInviteOptional.get());
                    messageRepository.save(message);

                    // 通知客服端
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // TODO: 接入会话之后，通知删除客服端browse信息


                    // 返回结果
                    jsonResult.setMessage("接受邀请会话成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(thread);

                } else {

                    jsonResult.setMessage("访客接受邀请会话-uid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("访客接受邀请会话-管理员用户不存在");
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
     * 访客拒绝邀请会话
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/invite/reject")
    @ResponseBody
    public JsonResult inviteReject(Principal principal, @RequestBody Map map) {

        String biid = (String) map.get("biid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                //
                Optional<BrowseInvite> browseInviteOptional = browseInviteRepository.findByBIid(biid);
                if (browseInviteOptional.isPresent()) {

                    // 记录反馈
                    browseInviteOptional.get().setToClient(client);
                    browseInviteOptional.get().setAccepted(false);
                    browseInviteOptional.get().setActionedAt(new Date());
                    browseInviteRepository.save(browseInviteOptional.get());

                    // 通知用户
                    Optional<User> userNotification = userRepository.findByUsername(UserConsts.USERNAME_NOTIFICATION);

                    // 保存聊天记录
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setWid(browseInviteOptional.get().getWorkGroup().getWid());
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE_REJECT);
                    message.setContent(BdConstants.DEFAULT_WORK_GROUP_INVITE_REJECT);
                    message.setUser(userNotification.get());
                    message.setBrowseInvite(browseInviteOptional.get());
                    messageRepository.save(message);

                    // 通知客服端
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("拒绝邀请会话成功");
                    jsonResult.setStatus_code(200);

                } else {

                    jsonResult.setMessage("访客拒绝邀请会话-uid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("访客拒绝邀请会话-管理员用户不存在");
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
     * 搜索过滤浏览记录
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
                //
                Specification specification = getSpecification(adminOptional.get(), TypeConsts.SPECIFICATION_TYPE_BROWSE, nickname, createdAtStart, createdAtEnd,
                        workGroupNickname, agentRealName, client);
                Page<Browse> browsePage = browseRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索浏览记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(browsePage);

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



