package io.xiaper.restim.controller.v1;

import io.xiaper.jpa.constant.*;
import io.xiaper.jpa.model.*;
import io.xiaper.jpa.repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.ThreadService;
import io.xiaper.rest.controller.v1.BaseController;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * 群组相关接口
 * TODO: 增加接口版本管理，同时支持多个版本
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/group")
@Api(value="群组", tags="群组接口", produces = "application/json;charset=utf-8")
public class GroupController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    InviteRepository inviteRepository;

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserService userService;

    @Autowired
    ThreadService threadService;

    /**
     * 获取自己所属的群组
     *
     * @param principal
     * @param client
     * @return
     */
    @ApiOperation(value="获取群组", notes="获取自己所属的群组")
    @GetMapping(value = "/get", produces = "application/json;charset=utf-8")
    public JsonResult get(Principal principal, @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {
            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {
                //
                List<Group> groupList = groupRepository.findByDismissedAndMembersContains(false, userOptional.get());

                // 返回结果
                jsonResult.setMessage("获取群组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(groupList);

            } else {

                jsonResult.setMessage("用户不存在");
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
     * 获取某个群组详情：
     * TODO: 群组标准详情 + 每个人看见自己对群组的设置
     *
     * @param principal
     * @param client
     * @return
     */
    @ApiOperation(value="群组详情", notes="获取某个群组详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gid", value = "群组唯一id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "client", value = "来源客户端，服务器调用写死为 'web' ", required = true, dataType = "String"),
    })
    @GetMapping(value = "/detail", produces = "application/json;charset=utf-8")
    public JsonResult detail(Principal principal,
                              @RequestParam(value = "gid") String gid,
                              @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    // 返回结果
                    jsonResult.setMessage("获取群组详情成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(groupOptional.get());

                } else {

                    jsonResult.setMessage("获取群组详情失败-群组不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("用户不存在");
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
     * 获取某个群组全部成员
     *
     * @param principal
     * @param gid
     * @param client
     * @return
     */
    @GetMapping("/members")
    public JsonResult members(Principal principal,
                              @RequestParam(value = "gid") String gid,
                              @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    //
                    Set<User> members = groupOptional.get().getMembers();
                    Set<User> admins = groupOptional.get().getAdmins();

                    //
                    Map<String, Object> objectMap = new HashMap<>(2);
                    objectMap.put("members", members);
                    objectMap.put("admins", admins);

                    // 返回结果
                    jsonResult.setMessage("获取群组成员成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(objectMap);

                } else {

                    jsonResult.setMessage("获取群组成员失败-群组不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("创建群组失败-账号不存在");
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
     * 创建群组
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        String nickname = (String) map.get("nickname");
        List<String> selectedContacts =  (List<String>) map.get("selectedContacts");

        // TODO: 验证selectedContacts为有效uid，防止传入中文等无效字符串

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                //
                Group group = new Group();
                group.setGid(JpaUtil.randomId());
                // 根据群成员realName生成昵称
                group.setNickname(nickname);
                group.setType(TypeConsts.GROUP_TYPE_GROUP);
                group.setAvatar(AvatarConsts.DEFAULT_GROUP_AVATAR_URL);
                group.setDescription(BdConstants.DEFAULT_GROUP_DESCRIPTION);
                group.setAnnouncement(BdConstants.DEFAULT_GROUP_ANNOUNCEMENT);

                // 加入群成员
                String realNames = "";
                Iterator iterator = selectedContacts.iterator();
                while (iterator.hasNext()) {
                    String uid = (String) iterator.next();
                    Optional<User> contactOptional = userRepository.findByUid(uid);
                    if (contactOptional.isPresent()) {
                        group.getMembers().add(contactOptional.get());
                        realNames +=  "," + contactOptional.get().getRealName();
                    }
                }
                // 加入自己
                group.getMembers().add(adminOptional.get());

                // 管理员
                group.getAdmins().add(adminOptional.get());

                // 创建者
                group.setUser(adminOptional.get());
                groupRepository.save(group);

                // 通知所有群组成员
                Thread thread = threadService.getGroupThread(group.getGid());
                Message message = new Message();
                message.setMid(JpaUtil.randomId());
                message.setGid(group.getGid());
                message.setThread(thread);
                message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                message.setClient(ClientConsts.CLIENT_SYSTEM);
                message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_CREATE);
                message.setContent(adminOptional.get().getRealName() + "邀请" + realNames.substring(1) + "加入群");
                message.setGroup(group);
                message.setUser(userService.getNotificationUser());
                messageRepository.save(message);
                //
                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                // 返回结果
                jsonResult.setMessage("创建群组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(group);

            } else {

                jsonResult.setMessage("创建群组失败-账号不存在");
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
     * 更新群名称
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update/nickname")
    @ResponseBody
    public JsonResult updateNickname(Principal principal, @RequestBody Map map) {

        String gid = (String) map.get("gid");
        String nickname = (String) map.get("nickname");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    groupOptional.get().setNickname(nickname);
                    groupRepository.save(groupOptional.get());

                    // 通知所有群组成员
                    Thread thread = threadService.getGroupThread(gid);
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(gid);
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_UPDATE);
                    message.setContent("修改群名为:"+nickname);
                    message.setGroup(groupOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                    //
                    Map<String, String> objectMap = new HashMap<>(2);
                    objectMap.put("gid", gid);
                    objectMap.put("nickname", nickname);

                    // 返回结果
                    jsonResult.setMessage("更新群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(objectMap);

                } else {

                    jsonResult.setMessage("更新群组昵称失败-群组不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("更新群组昵称失败-账号不存在");
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
     * 更新群描述
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update/description")
    @ResponseBody
    public JsonResult updateDescription(Principal principal, @RequestBody Map map) {

        String gid = (String) map.get("gid");
        String description = (String) map.get("description");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    groupOptional.get().setDescription(description);
                    groupRepository.save(groupOptional.get());

                    // 通知所有群组成员
                    Thread thread = threadService.getGroupThread(gid);
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(gid);
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_UPDATE);
                    message.setContent("修改群描述为:"+description);
                    message.setGroup(groupOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                    //
                    Map<String, String> objectMap = new HashMap<>(2);
                    objectMap.put("gid", gid);
                    objectMap.put("description", description);

                    // 返回结果
                    jsonResult.setMessage("更新群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(description);

                } else {

                    jsonResult.setMessage("更新群组描述失败-群组不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("更新群组描述失败-账号不存在");
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
     * 更新群公告
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update/announcement")
    @ResponseBody
    public JsonResult updateAnnouncement(Principal principal, @RequestBody Map map) {

        String gid = (String) map.get("gid");
        String announcement = (String) map.get("announcement");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    groupOptional.get().setAnnouncement(announcement);
                    groupRepository.save(groupOptional.get());

                    // 通知所有群组成员
                    Thread thread = threadService.getGroupThread(gid);
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(gid);
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_UPDATE);
                    message.setContent("修改群公告为:"+announcement);
                    message.setGroup(groupOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                    //
                    Map<String, String> objectMap = new HashMap<>(2);
                    objectMap.put("gid", gid);
                    objectMap.put("announcement", announcement);

                    // 返回结果
                    jsonResult.setMessage("更新群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(objectMap);

                } else {

                    jsonResult.setMessage("更新群组公告失败-群组不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("更新群组公告失败-账号不存在");
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
     * 邀请多人加入群
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/invite")
    @ResponseBody
    public JsonResult invite(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String gid = (String) map.get("gid");
//         List<String> selectedContacts =  (List<String>) map.get("selectedContacts");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 被邀请人
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            Optional<User> contactOptional = userRepository.findByUid(uid);
            if (contactOptional.isPresent()) {

                // 所加入群组
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    // 加群群成员列表
                    groupOptional.get().getMembers().add(contactOptional.get());
                    groupRepository.save(groupOptional.get());

                    // 邀请记录
                    Invite invite = new Invite();
                    invite.settIid(JpaUtil.randomId());
                    invite.setType(TypeConsts.INVITE_TYPE_GROUP);
                    invite.setGroup(groupOptional.get());
                    invite.setFromUser(userOptional.get());
                    invite.setToUser(contactOptional.get());
                    inviteRepository.save(invite);

                    // 通知所有群组成员
                    Thread thread = threadService.getGroupThread(gid);
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(gid);
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_INVITE);
                    message.setContent("邀请" + contactOptional.get().getRealName() + "加入群组");
                    message.setGroup(groupOptional.get());
                    // 设置邀请信息
                    message.setInvite(invite);
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);
                    // TODO: 消息发送给被邀请者
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // TODO: 消息发送给所有群组成员
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("邀请加入群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(groupOptional.get());

                } else {

                    jsonResult.setMessage("更新群组昵称失败-群组不存在");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("邀请加入群组失败-被邀请用户不存在");
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
     * 不需要审核加入群组
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/join")
    @ResponseBody
    public JsonResult join(Principal principal, @RequestBody Map map) {

        String gid = (String) map.get("gid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    // 通知群组管理员
                    Thread thread = threadService.getGroupThread(gid);
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(gid);
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_JOIN);
                    message.setContent(userOptional.get().getRealName() + "加入群组");
                    message.setGroup(groupOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);

                    // 发送给群组成员
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("加入群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(groupOptional.get());

                }  else {

                    jsonResult.setMessage("加入群组失败-群组不存在");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("加入群组失败-账号不存在");
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
     * 主动申请加入群组
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/apply")
    @ResponseBody
    public JsonResult apply(Principal principal, @RequestBody Map map) {

        String gid = (String) map.get("gid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    //
                    Notice notice = new Notice();
                    notice.setNid(JpaUtil.randomId());
                    notice.setType(TypeConsts.NOTICE_TYPE_GROUP);
                    notice.setTitle("加群通知");
                    notice.setContent(userOptional.get().getRealName()+"申请加入群");
                    notice.setGroup(groupOptional.get());
                    // 通知群组内所有管理员
                    Set<User> admins = groupOptional.get().getAdmins();
                    Iterator iterator = admins.iterator();
                    while (iterator.hasNext()) {
                        User admin = (User) iterator.next();
                        notice.getUsers().add(admin);
                    }
                    notice.setUser(userOptional.get());
                    noticeRepository.save(notice);

                    // 通知群组管理员
                    Thread thread = threadService.getGroupThread(gid);
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(gid);
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY);
                    message.setContent(userOptional.get().getRealName() + "申请加入群组");
                    message.setGroup(groupOptional.get());
                    // 设置通知
                    message.setNotice(notice);
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);

                    // 发送给群组管理员
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("主动加入群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(groupOptional.get());

                }  else {

                    jsonResult.setMessage("主动申请加入群组失败-群组不存在");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("主动申请加入群组失败-账号不存在");
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
     * 同意：主动申请加群
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/apply/approve")
    @ResponseBody
    public JsonResult applyApprove(Principal principal, @RequestBody Map map) {

        // notice的nid
        String nid = (String) map.get("nid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 管理员
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 关联通知
                Optional<Notice> noticeOptional = noticeRepository.findByNid(nid);
                if (noticeOptional.isPresent()) {

                    // 相关群组
                    Group group = noticeOptional.get().getGroup();
                    // 申请入群者
                    User user = noticeOptional.get().getUser();

                    // 相关用户添加入群
                    group.getMembers().add(user);
                    groupRepository.save(group);

                    // 消息
                    Thread thread = threadService.getGroupThread(group.getGid());
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(group.getGid());
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY_APPROVE);
                    message.setContent(adminOptional.get().getRealName()+ "同意" + user.getRealName() + "加入群组");
                    message.setGroup(group);
                    // 设置通知
                    message.setNotice(noticeOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);

                    // 发送一条消息给申请者
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 发送一条消息给所有群组成员
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                } else {

                    jsonResult.setMessage("同意申请加群失败-nid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("同意申请加群失败-账号不存在");
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
     * 拒绝：主动申请加群
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/apply/deny")
    @ResponseBody
    public JsonResult applyDeny(Principal principal, @RequestBody Map map) {

        String nid = (String) map.get("nid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 管理员
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 关联通知
                Optional<Notice> noticeOptional = noticeRepository.findByNid(nid);
                if (noticeOptional.isPresent()) {

                    // 相关群组
                    Group group = noticeOptional.get().getGroup();
                    // 申请入群者
                    User user = noticeOptional.get().getUser();

                    // 消息
                    Thread thread = threadService.getGroupThread(group.getGid());
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(group.getGid());
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY_DENY);
                    message.setContent(adminOptional.get().getRealName()+ "拒绝" + user.getRealName() + "加入群组");
                    message.setGroup(group);
                    // 设置通知
                    message.setNotice(noticeOptional.get());
                    message.setUser(userService.getNotificationUser());
                    // FIXME: 不需要存储拒绝信息？
                    messageRepository.save(message);

                    // 发送一条消息给申请者
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                } else {

                    jsonResult.setMessage("拒绝申请加群失败-nid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("拒绝申请加群失败-账号不存在");
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
     * 踢人
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/kick")
    @ResponseBody
    public JsonResult kick(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String gid = (String) map.get("gid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 查询当前用户是否存在
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 不能踢自己
                if (uid.equals(userOptional.get().getUid())) {

                    jsonResult.setMessage("踢人失败-不能踢自己");
                    jsonResult.setStatus_code(-5);
                    jsonResult.setData(false);

                    return jsonResult;
                }

                // 查询被踢人是否存在
                Optional<User> contactOptional = userRepository.findByUid(uid);
                if (contactOptional.isPresent()) {

                    // 查询群组是否存在
                    Optional<Group> groupOptional = groupRepository.findByGid(gid);
                    if (groupOptional.isPresent()) {

                        // 踢出
                        Set<User> memberSet = groupOptional.get().getMembers();
                        Iterator iterator = memberSet.iterator();
                        while (iterator.hasNext()) {
                            User member = (User) iterator.next();
                            if (member.getUid().equals(uid)) {
                                memberSet.remove(member);
                            }
                        }
                        groupOptional.get().setMembers(memberSet);
                        groupRepository.save(groupOptional.get());

                        // 通知群组所有成员
                        Thread thread = threadService.getGroupThread(gid);
                        //
                        Message message = new Message();
                        message.setMid(JpaUtil.randomId());
                        message.setGid(gid);
                        message.setThread(thread);
                        message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                        message.setClient(ClientConsts.CLIENT_SYSTEM);
                        message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_KICK);
                        message.setContent(contactOptional.get().getRealName() + "被移出群组");
                        message.setGroup(groupOptional.get());
                        message.setUser(userService.getNotificationUser());
                        messageRepository.save(message);
                        // TODO: 通知被移除成员

                        // 通知所有群成员
                        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                        // 返回结果
                        jsonResult.setMessage("踢人成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData("");

                    } else {

                        jsonResult.setMessage("踢人失败-群组不存在");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(false);
                    }

                } else {

                    jsonResult.setMessage("踢人失败-被踢账号不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("创建群组失败-账号不存在");
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
     * 禁言
     * TODO: 群组成员role/authority
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/mute")
    @ResponseBody
    public JsonResult mute(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String gid = (String) map.get("gid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 不能禁言自己
                if (uid.equals(userOptional.get().getUid())) {

                    jsonResult.setMessage("禁言失败-不能禁言自己");
                    jsonResult.setStatus_code(-5);
                    jsonResult.setData(false);

                    return jsonResult;
                }

                // 查询被禁言人是否存在
                Optional<User> contactOptional = userRepository.findByUid(uid);
                if (contactOptional.isPresent()) {

                    // 查询群组是否存在
                    Optional<Group> groupOptional = groupRepository.findByGid(gid);
                    if (groupOptional.isPresent()) {

                        // 禁言
                        if (groupOptional.get().getMuted().contains(contactOptional.get())) {

                            jsonResult.setMessage("禁言失败-重复禁言");
                            jsonResult.setStatus_code(-4);
                            jsonResult.setData(false);

                            return jsonResult;
                        }

                        groupOptional.get().getMuted().add(contactOptional.get());
                        groupRepository.save(groupOptional.get());

                        // 通知群组所有成员
                        Thread thread = threadService.getGroupThread(gid);
                        Message message = new Message();
                        message.setMid(JpaUtil.randomId());
                        message.setGid(gid);
                        message.setThread(thread);
                        message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                        message.setClient(ClientConsts.CLIENT_SYSTEM);
                        message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_MUTE);
                        message.setContent(contactOptional.get().getRealName() + "被禁言");
                        message.setGroup(groupOptional.get());
                        message.setUser(userService.getNotificationUser());
                        messageRepository.save(message);
                        // TODO: 通知被禁用成员

                        // 通知所有群成员
                        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                        // 返回结果
                        jsonResult.setMessage("禁言成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData("");

                    } else {

                        jsonResult.setMessage("禁言失败-群组不存在");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(false);
                    }

                } else {

                    jsonResult.setMessage("禁言失败-被踢账号不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("禁言失败-账号不存在");
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
     * 移交群组：
     * 修改群组的user字段为被转接人
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/transfer")
    @ResponseBody
    public JsonResult transfer(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String gid = (String) map.get("gid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 不能移交给自己
                if (uid.equals(userOptional.get().getUid())) {

                    jsonResult.setMessage("移交失败-不能移交给自己");
                    jsonResult.setStatus_code(-5);
                    jsonResult.setData(false);

                    return jsonResult;
                }

                // 查询被移交人是否存在
                Optional<User> contactOptional = userRepository.findByUid(uid);
                if (contactOptional.isPresent()) {

                    // 查询群组是否存在
                    Optional<Group> groupOptional = groupRepository.findByGid(gid);
                    if (groupOptional.isPresent()) {

                        // 通知给被转移人
                        Notice notice = new Notice();
                        notice.setNid(JpaUtil.randomId());
                        notice.setType(TypeConsts.NOTICE_TYPE_GROUP);
                        notice.setTitle("移交群组通知");
                        notice.setContent(userOptional.get().getRealName()+"移交群组给"+contactOptional.get().getRealName());
                        notice.setGroup(groupOptional.get());
                        notice.getUsers().add(contactOptional.get());
                        notice.setUser(userOptional.get());
                        noticeRepository.save(notice);

                        // 通知群组被移交人
                        Thread thread = threadService.getGroupThread(gid);
                        //
                        Message message = new Message();
                        message.setMid(JpaUtil.randomId());
                        message.setGid(gid);
                        message.setThread(thread);
                        message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                        message.setClient(ClientConsts.CLIENT_SYSTEM);
                        message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER);
                        message.setContent(userOptional.get().getRealName() + "移交群组给" + contactOptional.get().getRealName());
                        message.setGroup(groupOptional.get());
                        // 设置通知
                        message.setNotice(notice);
                        message.setUser(userService.getNotificationUser());
                        messageRepository.save(message);
                        //
                        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                        // 返回结果
                        jsonResult.setMessage("移交群组成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData("");

                    } else {

                        jsonResult.setMessage("移交群组失败-群组不存在");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(false);
                    }

                } else {

                    jsonResult.setMessage("移交群组失败-被移交账号不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("移交群组失败-账号不存在");
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
     * 移交群组：同意、接受
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/transfer/accept")
    @ResponseBody
    public JsonResult transferAccept(Principal principal, @RequestBody Map map) {

        String nid = (String) map.get("nid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 关联通知
                Optional<Notice> noticeOptional = noticeRepository.findByNid(nid);
                if (noticeOptional.isPresent()) {

                    // 相关群组
                    Group group = noticeOptional.get().getGroup();
                    // 申请入群者
                    User user = noticeOptional.get().getUser();

                    // 相关用户添加入群
                    group.getMembers().add(user);
                    groupRepository.save(group);

                    // 消息
                    Thread thread = threadService.getGroupThread(group.getGid());
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(group.getGid());
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER_ACCEPT);
                    message.setContent(adminOptional.get().getRealName()+ "同意" + user.getRealName() + "移交群组");
                    message.setGroup(group);
                    // 设置通知
                    message.setNotice(noticeOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);

                    // 发送一条消息给申请者
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                    // 发送一条消息给所有群组成员
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                } else {

                    jsonResult.setMessage("同意申请加群失败-nid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("接受移交群组失败-账号不存在");
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
     * 移交群组：拒绝
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/transfer/reject")
    @ResponseBody
    public JsonResult transferReject(Principal principal, @RequestBody Map map) {

        String nid = (String) map.get("nid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 关联通知
                Optional<Notice> noticeOptional = noticeRepository.findByNid(nid);
                if (noticeOptional.isPresent()) {

                    // 相关群组
                    Group group = noticeOptional.get().getGroup();
                    // 申请入群者
                    User user = noticeOptional.get().getUser();

                    // 消息
                    Thread thread = threadService.getGroupThread(group.getGid());
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(group.getGid());
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER_REJECT);
                    message.setContent(adminOptional.get().getRealName()+ "拒绝" + user.getRealName() + "移交群组");
                    message.setGroup(group);
                    // 设置通知
                    message.setNotice(noticeOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);

                    // 发送一条消息给申请者
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_USER_MESSAGE, message);

                } else {

                    jsonResult.setMessage("拒绝申请加群失败-nid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }


            } else {

                jsonResult.setMessage("拒绝移交群组失败-账号不存在");
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
     * 退群
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/withdraw")
    @ResponseBody
    public JsonResult withdraw(Principal principal, @RequestBody Map map) {

        String gid = (String) map.get("gid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 查询群组是否存在
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    // 退群
                    Set<User> memberSet = groupOptional.get().getMembers();
                    Iterator iterator = memberSet.iterator();
                    while (iterator.hasNext()) {
                        User member = (User) iterator.next();
                        if (member.getUid().equals(userOptional.get())) {
                            memberSet.remove(member);
                        }
                    }
                    groupOptional.get().setMembers(memberSet);
                    groupRepository.save(groupOptional.get());

                    // 通知所有群组成员
                    Thread thread = threadService.getGroupThread(groupOptional.get().getGid());
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(groupOptional.get().getGid());
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_WITHDRAW);
                    message.setContent(userOptional.get().getRealName() + "退群");
                    message.setGroup(groupOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                    // 返回结果
                    jsonResult.setMessage("退出群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData("");

                } else {

                    jsonResult.setMessage("退出群组失败-群组不存在");
                    jsonResult.setStatus_code(-4);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("退出群组失败-账号不存在");
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
     * 解散群组
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/dismiss")
    @ResponseBody
    public JsonResult dismiss(Principal principal, @RequestBody Map map) {

        String gid = (String) map.get("gid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 查询群组是否存在
                Optional<Group> groupOptional = groupRepository.findByGid(gid);
                if (groupOptional.isPresent()) {

                    // TODO: 只有群组创建者才能够解散群组
                    if (!groupOptional.get().getUser().equals(userOptional.get())) {

                        jsonResult.setMessage("解散群组失败-账号不存在");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(false);

                        return jsonResult;
                    }

                    // 解散群组
                    groupOptional.get().setDismissed(true);
                    groupRepository.save(groupOptional.get());

                    // 通知所有群组成员
                    Thread thread = threadService.getGroupThread(groupOptional.get().getGid());
                    //
                    Message message = new Message();
                    message.setMid(JpaUtil.randomId());
                    message.setGid(groupOptional.get().getGid());
                    message.setThread(thread);
                    message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_GROUP);
                    message.setClient(ClientConsts.CLIENT_SYSTEM);
                    message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_GROUP_DISMISS);
                    message.setContent(userOptional.get().getRealName() + "解散群组");
                    message.setGroup(groupOptional.get());
                    message.setUser(userService.getNotificationUser());
                    messageRepository.save(message);
                    //
                    rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_GROUP_MESSAGE, message);

                    // FIXME: 群组解散之后，历史会话记录如何处理？


                    // 返回结果
                    jsonResult.setMessage("解散群组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData("");

                } else {

                    jsonResult.setMessage("解散群组失败-群组不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("解散群组失败-账号不存在");
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
     * 搜索群组:
     *  搜索客服账号所属管理员账号下所有客服创建的群组
     * TODO
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "keyword") String keyword) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {


            } else {

                jsonResult.setMessage("搜索群组失败-账号不存在");
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
     * 搜索群组成员
     * TODO
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter/members")
    public JsonResult filterMembers(Principal principal,
                                    @RequestParam(value = "gid") String gid,
                                    @RequestParam(value = "nickname") String nickname) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {



            } else {

                jsonResult.setMessage("搜索群组成员失败-账号不存在");
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



}






