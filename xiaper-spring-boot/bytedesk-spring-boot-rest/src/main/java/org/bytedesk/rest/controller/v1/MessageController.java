package org.bytedesk.rest.controller.v1;

import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.model.*;
import org.bytedesk.jpa.model.Thread;
import org.bytedesk.jpa.repository.*;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.mq.service.MessageService;
import org.bytedesk.mq.service.TransformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 聊天记录，消息
 *
 * TODO: 增加起始id查询聊天记录接口
 *
 * TODO: 支持消息撤回，普通用户支持1分钟内撤回，管理员支持随时撤回
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    MessageService messageService;

    @Autowired
    TransformService transformService;

    /**
     * 管理员，分页获取聊天记录
     *
     * @param principal
     * @return
     */
    @GetMapping("/request")
    public JsonResult request(Principal principal,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size,
                              @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByThread_WorkGroup_User(adminOptional.get(), pageable);

                //
                jsonResult.setMessage("获取消息成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

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
     * 按照会话thread加载会话聊天记录
     *
     * @param principal
     * @param tid
     * @param page
     * @param client
     * @return
     */
    @GetMapping("/thread")
    public JsonResult thread(Principal principal,
                             @RequestParam(value = "tid") String tid,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             @RequestParam(value = "client") String client) {

        logger.info("get thread messages: tid {}, page {}, client {}", tid, page, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Thread> threadOptional = threadRepository.findByTid(tid);
            if (threadOptional.isPresent()) {

                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByThread(threadOptional.get(), pageable);

                jsonResult.setMessage("加载会话聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

            } else {

                jsonResult.setMessage("tid错误");
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
     * 加载跟某个访客的会话聊天记录
     *
     * @param principal
     * @param uid
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GetMapping("/user")
    public JsonResult user(Principal principal,
                           @RequestParam(value = "uid") String uid,
                           @RequestParam(value = "page") int page,
                           @RequestParam(value = "size") int size,
                           @RequestParam(value = "client") String client) {

        logger.info("get user messages: uid {}, page {}, client {}", uid, page, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUid(uid);
            if (visitorOptional.isPresent()) {

                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByThread_Visitor(visitorOptional.get(), pageable);

                // TODO: 遍历所查出的消息，判断当前用户对此消息的状态：已收到、已读

                jsonResult.setMessage("加载访客聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

            } else {

                jsonResult.setMessage("uid错误");
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
     * 从某条id开始加载聊天记录
     * TODO: 通过在配置文件里面打印show sql查看执行过多sql语句，有待优化
     *
     * @param principal
     * @param uid
     * @param id
     * @param client
     * @return
     */
    @GetMapping("/user/from")
    public JsonResult userFrom(Principal principal,
                               @RequestParam(value = "uid") String uid,
                               @RequestParam(value = "id") int id,
                               @RequestParam(value = "client") String client) {

        logger.info("get group messages: cid {}, id {}, client {}", uid, id, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUid(uid);
            if (visitorOptional.isPresent()) {

                Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByIdAndThread_Visitor(Long.valueOf(id), visitorOptional.get().getId(), pageable);

                jsonResult.setMessage("加载访客聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

            } else {

                jsonResult.setMessage("uid错误");
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
     * 加载一对一会话消息
     *
     * @param principal
     * @param cid
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GetMapping("/contact")
    public JsonResult contact(Principal principal,
                           @RequestParam(value = "cid") String cid,
                           @RequestParam(value = "page") int page,
                           @RequestParam(value = "size") int size,
                           @RequestParam(value = "client") String client) {

        logger.info("get contact messages: cid {}, page {}, client {}", cid, page, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            Optional<User> contactOptional = userRepository.findByUid(cid);
            if (userOptional.isPresent() && contactOptional.isPresent()) {

                //
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByCidAndUserOrCidAndUser(cid, userOptional.get(),
                        userOptional.get().getUid(), contactOptional.get(), pageable);

                // 返回结果
                jsonResult.setMessage("加载一对一聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

            } else {

                jsonResult.setMessage("加载一对一聊天记录失败-联系人不存在");
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
     * 从某条id开始加载聊天记录
     * TODO: 通过在配置文件里面打印show sql查看执行过多sql语句，有待优化
     *
     * @param principal
     * @param cid
     * @param id
     * @param client
     * @return
     */
    @GetMapping("/contact/from")
    public JsonResult contactFrom(Principal principal,
                                @RequestParam(value = "cid") String cid,
                                @RequestParam(value = "id") int id,
                                @RequestParam(value = "client") String client) {

        logger.info("get group messages: cid {}, id {}, client {}", cid, id, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            Optional<User> contactOptional = userRepository.findByUid(cid);
            if (userOptional.isPresent() && contactOptional.isPresent()) {

                //
                Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByIdAndCidAndUserOrCidAndUser(Long.valueOf(id), cid, userOptional.get().getId(),
                        userOptional.get().getUid(), contactOptional.get().getId(), pageable);

                // 返回结果
                jsonResult.setMessage("加载一对一聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

            } else {

                jsonResult.setMessage("加载一对一聊天记录失败-联系人不存在");
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
     * 加载群组消息
     *
     * @param principal
     * @param gid
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GetMapping("/group")
    public JsonResult group(Principal principal,
                           @RequestParam(value = "gid") String gid,
                           @RequestParam(value = "page") int page,
                           @RequestParam(value = "size") int size,
                           @RequestParam(value = "client") String client) {

        logger.info("get group messages: gid {}, page {}, client {}", gid, page, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<Group> groupOptional = groupRepository.findByGid(gid);
            if (groupOptional.isPresent()) {

                //
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByGid(gid, pageable);

                // 返回结果
                jsonResult.setMessage("加载群组聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

            } else {

                jsonResult.setMessage("加载群组聊天记录失败-gid不存在");
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
     * 从某条id开始加载聊天记录
     * TODO: 通过在配置文件里面打印show sql查看执行过多sql语句，有待优化
     *
     * @param principal
     * @param gid
     * @param id
     * @param client
     * @return
     */
    @GetMapping("/group/from")
    public JsonResult groupFrom(Principal principal,
                              @RequestParam(value = "gid") String gid,
                              @RequestParam(value = "id") int id,
                              @RequestParam(value = "client") String client) {

        logger.info("get group messages: gid {}, id {}, client {}", gid, id, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<Group> groupOptional = groupRepository.findByGid(gid);
            if (groupOptional.isPresent()) {

                //
                Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
                Page<Message> messagePage = messageRepository.findByIdAndGid(Long.valueOf(id), gid, pageable);

                // 返回结果
                jsonResult.setMessage("加载群组聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);

            } else {

                jsonResult.setMessage("加载群组聊天记录失败-gid不存在");
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
     * 客服端拉取离线消息
     *
     * @param principal
     * @param client
     * @return
     */
    @GetMapping("/offline")
    public JsonResult offline(Principal principal, @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {




        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 发送消息http接口
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/send")
    @ResponseBody
    public JsonResult send(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String type = (String) map.get("type");
        String client = (String) map.get("client");
        String content  = (String) map.get("content");
        // String username = (String) map.get("username");
        String status = (String) map.get("status");
        String localId = (String) map.get("localId");
        String sessionType = (String) map.get("sessionType");

        logger.info("type {}, client {}, content: {}, tid {}, sessionType {}, localId {}, status {}",
                type, client, content, tid, sessionType, localId, status);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

             Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            // FIXME: 用principal获取当前用户
            // Optional<User> userOptional = userRepository.findByUsername(username);
            //
            Message message = new Message();
            message.setClient(client);
            message.setType(type);
            message.setLocalId(localId);
            message.setSessionType(sessionType);
            if (type.equals(TypeConsts.MESSAGE_TYPE_TEXT)) {
                message.setContent(content);
            } else if (type.equals(TypeConsts.MESSAGE_TYPE_IMAGE)) {
                message.setImageUrl(content);
            }

            // 区分访客会话、联系人会话、群组会话，推送消息给接收者
            if (sessionType.equals(TypeConsts.MESSAGE_SESSION_TYPE_WORK_GROUP)) {
                // TODO: 判断用户是否被拉黑，如果被拉黑，则返回发送失败

                Optional<Thread> threadOptional = threadRepository.findByTid(tid);
                if (threadOptional.isPresent() &&
                        threadOptional.get().getAgent() != null) {

                    Optional<Block> blockOptional = blockRepository.findByUserAndBlockedUser(threadOptional.get().getAgent(), userOptional.get());
                    if (blockOptional.isPresent()) {

                        jsonResult.setMessage("发送失败-您已经被拉黑");
                        jsonResult.setStatus_code(-2);
                        jsonResult.setData(localId);

                        return jsonResult;
                    }
                }

                messageService.routeThreadMessage(tid, message, userOptional.get());

            } else if (sessionType.equals(TypeConsts.MESSAGE_SESSION_TYPE_CONTACT)) {
                // TODO: 判断用户是否被拉黑，如果被拉黑，则返回发送失败

                Optional<User> contactOptional = userRepository.findByUid(tid);
                Optional<Block> blockOptional = blockRepository.findByUserAndBlockedUser(contactOptional.get(), userOptional.get());

                if (blockOptional.isPresent()) {

                    jsonResult.setMessage("发送失败-对方已经将您拉黑");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(localId);

                    return jsonResult;
                }

                messageService.routeContactMessage(tid, message, userOptional.get());

            } else if (sessionType.equals(TypeConsts.MESSAGE_SESSION_TYPE_GROUP)) {
                // TODO: 通过group_muted表判断用户是否被禁言，如果被禁言，则返回发送失败

                Optional<Group> groupOptional = groupRepository.findByGid(tid);
                if (groupOptional.isPresent()) {
                    //
                    if (groupOptional.get().getMuted().contains(userOptional.get())) {

                        jsonResult.setMessage("发送失败-您已经被禁言");
                        jsonResult.setStatus_code(-2);
                        jsonResult.setData(localId);

                        return jsonResult;
                    }
                }

                messageService.routeGroupMessage(tid, message, userOptional.get());
            }

            // 简化消息体, 返回给发送者
            Map<String, Object> objectMap = new HashMap<>();
            if (sessionType.equals(TypeConsts.MESSAGE_SESSION_TYPE_WORK_GROUP)) {

                objectMap = transformService.getThreadContentMessageMap(message);
            } else if (sessionType.equals(TypeConsts.MESSAGE_SESSION_TYPE_CONTACT)) {

                objectMap = transformService.getContactMessageMap(message);
            } else if (sessionType.equals(TypeConsts.MESSAGE_SESSION_TYPE_GROUP)) {

                objectMap = transformService.getGroupMessageMap(message);
            }

            // 返回结果
            jsonResult.setMessage("发送消息成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(localId);
        }

        return jsonResult;
    }


    /**
     * 搜索过滤消息
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
                             @RequestParam(value = "content") String content,
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

                Specification specification = getSpecification(adminOptional.get(), TypeConsts.SPECIFICATION_TYPE_MESSAGE, nickname, createdAtStart, createdAtEnd,
                        workGroupNickname, agentRealName, client);
                Page<Message> messagePage = messageRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索消息成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(messagePage);
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
     * 客户端标记删除，之后不再出现在其消息列表，非真正删除
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/mark/deleted")
    @ResponseBody
    public JsonResult markDeleted(Principal principal, @RequestBody Map map) {

        String mid = (String) map.get("mid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<Message> messageOptional = messageRepository.findByMid(mid);
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (messageOptional.isPresent()) {
                //
                messageOptional.get().getDeletedSet().add(userOptional.get());
                messageRepository.save(messageOptional.get());

                //
                jsonResult.setMessage("标记删除聊天记录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(mid);

            } else {

                jsonResult.setMessage("标记删除聊天记录失败-mid不存在");
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
     * 客户端标记删除，之后不再出现在其消息列表，非真正删除
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/mark/clear")
    @ResponseBody
    public JsonResult markClear(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {



        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }




}
