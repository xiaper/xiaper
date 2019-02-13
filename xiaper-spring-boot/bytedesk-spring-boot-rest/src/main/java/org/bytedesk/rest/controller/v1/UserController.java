package org.bytedesk.rest.controller.v1;

import io.swagger.annotations.Api;
import org.bytedesk.jpa.constant.RoleConsts;
import org.bytedesk.jpa.constant.StatusConsts;
import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.constant.UserConsts;
import org.bytedesk.jpa.model.*;
import org.bytedesk.jpa.model.Queue;
import org.bytedesk.jpa.model.Thread;
import org.bytedesk.jpa.repository.*;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.mq.service.StatusService;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.ValidateService;
import org.bytedesk.mq.service.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.*;

/**
 *
 * 客服端接口
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/user")
@Api(value="用户", tags="用户登录之后接口", produces = "application/json;charset=utf-8")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    FingerPrint2Repository fingerPrint2Repository;

    @Autowired
    BrowseRepository browseRepository;

    @Autowired
    QueueRepository queueRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    WorkGroupRepository workgroupRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    StatusService statusService;

    @Autowired
    ValidateService validateService;


    /**
     * 客服登录成功之后，通知服务器，
     *
     * 并从服务器获取基本信息
     * 1. 所属工作组
     * 2. 进行中会话
     *
     * TODO: 验证登录客服数量是否超过购买 最大坐席数，如超过，需要做处理
     *
     * 实例：
     * localhost:8000/api/user/init?access_token=token...
     *
     * @param principal
     * @return
     */
    @GetMapping("/init")
    public JsonResult init(Principal principal,
                           @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {
            //
            Optional<User> agentOptional = userRepository.findByUsername(principal.getName());
            if (agentOptional.isPresent()) {

                // 获取所属工作组
                Set<WorkGroup> workGroupSet = agentOptional.get().getWorkGroups();

                // 获取排队中访客
                Pageable pageableQueue = PageRequest.of(0, 100, Sort.Direction.ASC, "id");
                Page<Queue> queuePage = queueRepository.findByWorkGroup_UsersContainsAndStatus(agentOptional.get(), StatusConsts.QUEUE_STATUS_QUEUING, pageableQueue);

                // 获取所属群组
                List<Group> groupList = groupRepository.findByDismissedAndMembersContains(false, agentOptional.get());

                // 获取联系人
                List<User> contactList;
                if (agentOptional.get().isAdmin()) {
                    contactList = userRepository.findByUserOrUsername(agentOptional.get(), principal.getName());
                } else {
                    User admin = agentOptional.get().getAdmin();
                    contactList = userRepository.findByUserOrUsername(admin, admin.getUsername());
                }

                // 一天，也即24小时之内
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, -24);

                //
                Pageable pageable = PageRequest.of(0, 100, Sort.Direction.DESC, "id");
                Page<Thread> threadPage = threadRepository.findByTimestampAfterAndAgentsContains(calendar.getTime(), agentOptional.get(), pageable);
                List<Thread> agentThreadList = new LinkedList<>(threadPage.getContent());

                // 去重, 客户端仅需要显示某个访客一条会话
                // FIXME: 未考虑一个访客同时进行多个不同工作组会话且同时被同一个客服接待的的情况
                Integer chattingThreadCount = 0;
                Set<String> visitorUidSet = new HashSet<>(16);
                Iterator threadIterator = agentThreadList.iterator();
                while (threadIterator.hasNext()) {
                    Thread thread = (Thread) threadIterator.next();
                    if (visitorUidSet.contains(thread.getVisitor().getUid())) {
                        threadIterator.remove();
                    } else {
                        visitorUidSet.add(thread.getVisitor().getUid());
                        // 统计未结束会话数量
                        if (!thread.isClosed()) {
                            chattingThreadCount++;
                        }
                    }
                }

                // 加载联系人一对一会话
                List<Thread> contactThreadList = threadRepository.findByAgentAndType(agentOptional.get(), TypeConsts.THREAD_TYPE_CONTACT);

                // 群组会话
                List<Thread> groupThreadList = threadRepository.findByGroup_MembersContainsAndType(agentOptional.get(), TypeConsts.THREAD_TYPE_GROUP);

                // 初始化可接待数量到redis
                redisService.initAgentIdleCount(agentOptional.get().getUid(), agentOptional.get().getMaxThreadCount() - chattingThreadCount);

                // 返回结果
                Map<String, Object> objectMap = new HashMap<>(4);
                objectMap.put("info", agentOptional.get());
                objectMap.put("workGroups", workGroupSet);
                objectMap.put("queues", queuePage);
                objectMap.put("groups", groupList);
                objectMap.put("contacts", contactList);
                //
                objectMap.put("agentThreads", agentThreadList);
                objectMap.put("contactThreads", contactThreadList);
                objectMap.put("groupThreads", groupThreadList);

                // 保存登录状态
                Status status = new Status();
                status.setStatus(StatusConsts.USER_STATUS_LOGIN);
                status.setClient(client);
                status.setUser(agentOptional.get());
                statusRepository.save(status);

                // 登录之后，默认为在线状态
                agentOptional.get().setAcceptStatus(StatusConsts.USER_STATUS_ONLINE);
                userRepository.save(agentOptional.get());

                // FIXME: 客户端请求init接口时，携带唯一id，当客户端收到此消息时，用于区分是否将该客户端踢掉线
                // TODO: PC客服端登录成功后，通知其他已经登录的客服端 退出登录，防止账号泄露
                // statusService.kickoff(agentOptional.get(), client);

                // 返回结果
                jsonResult.setMessage("初始化成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(objectMap);

            } else {
                jsonResult.setMessage("user not exist");
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
     * 获取用户标签
     *
     * @param principal
     * @param uid
     * @param client
     * @return
     */
    @GetMapping("/tag")
    public JsonResult tag(Principal principal,
                          @RequestParam(value = "uid") String uid,
                          @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUid(uid);
            if (userOptional.isPresent())  {

                Set<Tag> tagSet = userOptional.get().getTags();

                jsonResult.setMessage("获取用户标签成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tagSet);

            } else {

                jsonResult.setMessage("获取用户标签失败-访客不存在 ");
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
     * 验证客服用户名是否已经存在
     *
     * @param principal
     * @param username
     * @return
     */
    @GetMapping("/validate")
    public JsonResult validate(Principal principal,
                               @RequestParam(value = "username") String username) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                String usernameAtSubDomain = username + "@" + adminOptional.get().getSubDomain();
                Optional<User> userOptional = userRepository.findByUsername(usernameAtSubDomain);

                if (!userOptional.isPresent()) {

                    jsonResult.setMessage("用户名可用");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(username);

                } else {

                    jsonResult.setMessage("用户名已经存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("管理员用户名不存在");
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
     * 管理员拥有的客服账号 或者 客服账号的同事列表
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size,
                          @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                // 客服账号
                Page<User> userPage;
                if (userOptional.get().isAdmin()) {

                    userPage = userRepository.findByUserOrUsername(userOptional.get(), principal.getName(), pageable);
                } else {

                    User admin = userOptional.get().getAdmin();
                    userPage = userRepository.findByUserOrUsername(admin, admin.getUsername(), pageable);
                }

                // 返回结果
                jsonResult.setMessage("获取客服账号成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(userPage);

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
     * 客服全部联系人
     *
     * @return
     */
    @GetMapping("/contacts")
    public JsonResult contacts(Principal principal,
                               @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                List<User> contactList;
                if (userOptional.get().isAdmin()) {
                    contactList = userRepository.findByUserOrUsername(userOptional.get(), principal.getName());
                } else {
                    User admin = userOptional.get().getAdmin();
                    contactList = userRepository.findByUserOrUsername(admin, admin.getUsername());
                }

                // 返回结果
                jsonResult.setMessage("获取联系人成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(contactList);

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
     * 管理员获取全部成员
     * 客服组长获取同组全部组员
     * 客服账号返回自己
     *
     * 用于历史记录页面过滤查询字段：客服账号
     *
     * @param principal
     * @param client
     * @return
     */
    @GetMapping("/workGroupMembers")
    public JsonResult workGroupMembers(Principal principal,
                                       @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                List<User> workGroupMembersList = new ArrayList<>();
                if (userOptional.get().isAdmin()) {

                    // 管理员获取全部成员
                    workGroupMembersList = userRepository.findByUserOrUsername(userOptional.get(), principal.getName());

                } else if (userOptional.get().isWorkGroupAdmin()) {

                    // 客服组长获取同组全部组员
                    Set<WorkGroup> workGroupList = userOptional.get().getWorkGroups();
                    Iterator iterator = workGroupList.iterator();
                    while (iterator.hasNext()) {
                        WorkGroup workGroup = (WorkGroup) iterator.next();
                        List<User> userList = userRepository.findByWorkGroupsContains(workGroup);
                        workGroupMembersList.addAll(userList);
                    }

                } else {

                    // 客服账号返回自己
                    workGroupMembersList.add(userOptional.get());
                }

                // 返回结果
                jsonResult.setMessage("获取客服账号成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(workGroupMembersList);

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
     * 获取当前在线&&接待状态客服账号
     *
     * @param principal
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/online")
    public JsonResult online(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                // 客服账号
                Page<User> userPage = userRepository.findBySubDomainAndConnectionStatusAndAcceptStatus(userOptional.get().getSubDomain(),
                            StatusConsts.USER_STATUS_CONNECTED, StatusConsts.USER_STATUS_ONLINE, pageable);

                // 返回结果
                jsonResult.setMessage("获取当前在线客服账号成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(userPage);

            } else {

                jsonResult.setMessage("获取当前在线客服账号失败-用户不存在");
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
     * 创建客服账号
     *
     * 注意：
     *  同一个管理员账号下用户名username 唯一，全平台不唯一
     *
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String email = (String) map.get("email");
        String avatar = (String) map.get("avatar");
        String realName = (String) map.get("realName");
        String nickname = (String) map.get("nickname");
        Integer maxThreadCount = (Integer) map.get("maxThreadCount");
        String role = (String) map.get("role");
        boolean enabled = (boolean) map.get("enabled");
        // 工作组wid
        List<String> workGroupWids =  (List<String>) map.get("workGroups");

        logger.info("username {}, password {},  avatar {}, realName {}, nickname {}, maxThreadCount {}, workGroups {}, role {}",
                username, password, avatar, realName, nickname, maxThreadCount, workGroupWids, role);

        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            //
            String usernameAtSubDomain = username + "@" + adminOptional.get().getSubDomain();
            Optional<User> userOptional = userRepository.findByUsername(usernameAtSubDomain);
            if (!userOptional.isPresent()) {

                // 创建新用户
                User user = new User();
                user.setUid(JpaUtil.randomId());

                // 保证同一个管理员账号下用户名username 唯一，全平台允许不唯一
                user.setUsername(usernameAtSubDomain);
                user.setPassword(bCryptPasswordEncoder.encode(password.trim()));

                user.setEmail(email);
                user.setAvatar(avatar);
                user.setRealName(realName);
                user.setNickname(nickname);
                user.setWelcomeTip(UserConsts.DEFAULT_AGENT_WELCOME_TIP);
                user.setAutoReply(false);
                user.setAutoReplyContent(UserConsts.AUTO_REPLY_NO);
                user.setMaxThreadCount(maxThreadCount);
                user.setDescription(UserConsts.USER_DESCRIPTION);
                user.setSubDomain(adminOptional.get().getSubDomain());
                user.setUser(adminOptional.get());
                user.setEnabled(enabled);
                user.setAcceptStatus(StatusConsts.USER_STATUS_OFFLINE);
                user.setConnectionStatus(StatusConsts.USER_STATUS_DISCONNECTED);

                // 默认创建客服角色
                Optional<Role> roleOptional = roleRepository.findByValue(RoleConsts.ROLE_WORKGROUP_AGENT);
                if (roleOptional.isPresent()) {
                    user.getRoles().add(roleOptional.get());
                }

                // 保存客服账号
                userRepository.save(user);

                // 返回结果
                jsonResult.setMessage("创建客服成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(user);

            } else {

                jsonResult.setMessage("用户名已经存在");
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
     * 更新客服账号
     *
     * 注意：
     *  同一个管理员账号下用户名username 唯一，全平台不唯一
     *
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        Integer id = (Integer) map.get("id");
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String email = (String) map.get("email");
        String avatar = (String) map.get("avatar");
        String realName = (String) map.get("realName");
        String nickname = (String) map.get("nickname");
        Integer maxThreadCount = (Integer) map.get("maxThreadCount");
        String role = (String) map.get("role");
        boolean enabled = (boolean) map.get("enabled");
        // 工作组wid
        List<String> workGroups =  (List<String>) map.get("workGroups");

        logger.info("username {}, password {},  avatar {}, realName {}, nickname {}, maxThreadCount {}, workGroups {}, role {}",
                username, password, avatar, realName, nickname, maxThreadCount, workGroups, role);

        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            //
            Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
            if (userOptional.isPresent()) {

                User user = userOptional.get();

                if (!user.isAdmin()) {
                    // 保证同一个管理员账号下用户名username 唯一，全平台允许不唯一
                    String usernameAtSubDomain = username + "@" + adminOptional.get().getSubDomain();
                    user.setUsername(usernameAtSubDomain);
                }

                if (password != null && password.trim().length() > 0) {
                    user.setPassword(bCryptPasswordEncoder.encode(password.trim()));
                }

                user.setEmail(email);
                user.setAvatar(avatar);
                user.setRealName(realName);
                user.setNickname(nickname);
                user.setSubDomain(adminOptional.get().getSubDomain());
                user.setEnabled(enabled);

                // TODO: 更新可接待数量到redis
                int oldMaxThreadCount = user.getMaxThreadCount();
                int subtraction = maxThreadCount - oldMaxThreadCount;
                redisService.subtractAgentIdleCount(user.getUid(), subtraction);

                //
                user.setMaxThreadCount(maxThreadCount);

                //

                // 保存客服账号
                userRepository.save(user);

                // 返回结果
                jsonResult.setMessage("更新客服账号成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(user);

            } else {

                jsonResult.setMessage("客服账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(id);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 更新自身用户资料
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update/profile")
    @ResponseBody
    public JsonResult updateProfile(Principal principal, @RequestBody Map map) {

        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String nickname = (String) map.get("nickname");
        String avatar = (String) map.get("avatar");
        String mobile = (String) map.get("mobile");
        String email = (String) map.get("email");
        String description = (String) map.get("description");
        String welcomeTip = (String) map.get("welcomeTip");

        logger.info("username {}, avatar {}, description {}, nickname {}",
                username, avatar, description, nickname);

        //
        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> userOptional = userRepository.findByUsername(principal.getName());
        if (userOptional.isPresent()) {

            User user = userOptional.get();
            user.setUsername(username);

            if (password != null && password.trim().length() > 0) {
                user.setPassword(bCryptPasswordEncoder.encode(password.trim()));
            }

            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setMobile(mobile);
            user.setEmail(email);
            user.setDescription(description);
            user.setWelcomeTip(welcomeTip);

            // 保存个人资料
            userRepository.save(user);

            // 返回结果
            jsonResult.setMessage("更新个人资料成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(user);

        } else {

            jsonResult.setMessage("更新个人资料失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }



    /**
     * 设置、修改用户昵称
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/nickname")
    @ResponseBody
    public JsonResult updateNickname(Principal principal, @RequestBody Map map) {

        String nickname = (String) map.get("nickname");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                userOptional.get().setNickname(nickname);
                userRepository.save(userOptional.get());

                //
                jsonResult.setMessage("设置昵称成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(userOptional.get());

            } else {

                jsonResult.setMessage("设置访客昵称-访客账号不存在");
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
     * 设置、修改用户头像
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/avatar")
    @ResponseBody
    public JsonResult updateAvatar(Principal principal, @RequestBody Map map) {

        String avatar = (String) map.get("avatar");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                userOptional.get().setAvatar(avatar);
                userRepository.save(userOptional.get());

                //
                jsonResult.setMessage("设置头像成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(userOptional.get());

            } else {

                jsonResult.setMessage("设置访客头像-访客账号不存在");
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
     * 设置、修改用户realName
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/realName")
    @ResponseBody
    public JsonResult updateRealName(Principal principal, @RequestBody Map map) {

        String realName = (String) map.get("realName");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                userOptional.get().setRealName(realName);
                userRepository.save(userOptional.get());

                //
                jsonResult.setMessage("设置真名成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(userOptional.get());

            } else {

                jsonResult.setMessage("设置访客真名-访客账号不存在");
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
     * 设置自动回复内容
     * TODO: 用于普通用户
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update/autoReply")
    @ResponseBody
    public JsonResult updateAutoReply(Principal principal, @RequestBody Map map) {

        boolean isAutoReply = (boolean) map.get("isAutoReply");
        String content = (String) map.get("content");

        logger.info("isAutoReply: {}, content: {}", isAutoReply, content);

        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> userOptional = userRepository.findByUsername(principal.getName());
        if (userOptional.isPresent()) {

            // 保存自动回复设置
            userOptional.get().setAutoReply(isAutoReply);
            userOptional.get().setAutoReplyContent(content);
            userRepository.save(userOptional.get());

            // 返回结果
            jsonResult.setMessage("设置自动回答成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(userOptional.get());

        } else {

            jsonResult.setMessage("设置自动回答失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 修改密码
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/change/password")
    @ResponseBody
    public JsonResult changePassword(Principal principal, @RequestBody Map map) {

        String password = (String) map.get("password");

        logger.info("password: {}", password);

        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> agentOptional = userRepository.findByUsername(principal.getName());
        if (agentOptional.isPresent()) {

            // 修改密码
            agentOptional.get().setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(agentOptional.get());

            // 返回结果
            jsonResult.setMessage("修改密码成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(agentOptional.get());

        } else {

            jsonResult.setMessage("修改密码失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 获取陌生人，用于测试
     *
     * @param principal
     * @return
     */
    @GetMapping("/strangers")
    public JsonResult getStrangers(Principal principal,
                                   @RequestParam(value = "page") int page,
                                   @RequestParam(value = "size") int size,
                                   @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> user = userRepository.findByUsername(principal.getName());

            // TODO: 获取同一个subDomain下调用registerUser接口注册的角色为User的用户 + 所有的客服账号


            jsonResult.setMessage("user strangers success");
            jsonResult.setStatus_code(200);
            //  jsonResult.setData(user.get().getFans());

        }  else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 当前用户关注的：人
     *
     * @param principal
     * @return
     */
    @GetMapping("/follows")
    public JsonResult getFollows(Principal principal,
                                 @RequestParam(value = "page") int page,
                                 @RequestParam(value = "size") int size,
                                 @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> user = userRepository.findByUsername(principal.getName());

            // TODO: 从中剔除好友
            Set<User> follows = user.get().getFollows();
            Set<User> fans = user.get().getFans();
            //
            Iterator iterator = fans.iterator();
            while (iterator.hasNext()) {
                User user1 = (User) iterator.next();
                if (follows.contains(user1)) {
                    follows.remove(user1);
                }
            }

            // TODO：分页加载
            // FIXME: 模拟分页查询结果，
            Map<String, Object> objectMap = new HashMap<>(1);
            objectMap.put("content", follows);

            //
            jsonResult.setMessage("获取关注成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 当前用户的粉丝
     *
     *
     * @param principal
     * @return
     */
    @GetMapping("/fans")
    public JsonResult getFans(Principal principal,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size,
                              @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> user = userRepository.findByUsername(principal.getName());

            // TODO: 从中剔除好友
            Set<User> follows = user.get().getFollows();
            Set<User> fans = user.get().getFans();
            //
            Iterator iterator = follows.iterator();
            while (iterator.hasNext()) {
                User user1 = (User) iterator.next();
                if (fans.contains(user1)) {
                    fans.remove(user1);
                }
            }

            // TODO：分页加载
            // FIXME: 模拟分页查询结果，
            Map<String, Object> objectMap = new HashMap<>(1);
            objectMap.put("content", fans);

            jsonResult.setMessage("获取粉丝成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 当前用户的好友：互相关注
     *
     *
     * @param principal
     * @return
     */
    @GetMapping("/friends")
    public JsonResult getFriends(Principal principal,
                                 @RequestParam(value = "page") int page,
                                 @RequestParam(value = "size") int size,
                                 @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> user = userRepository.findByUsername(principal.getName());

            //
            Set<User> follows = user.get().getFollows();
            Set<User> fans = user.get().getFans();
            //
            List<User> friends = new ArrayList<>();
            Iterator iterator = follows.iterator();
            while (iterator.hasNext()) {
                User user1 = (User) iterator.next();
                if (fans.contains(user1)) {
                    friends.add(user1);
                }
            }

            // TODO：分页加载
            // FIXME: 模拟分页查询结果，
            Map<String, Object> objectMap = new HashMap<>(1);
            objectMap.put("content", friends);

            jsonResult.setMessage("获取好友成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 是否关注某个用户
     *
     * @param principal
     * @param uId
     * @return
     */
    @GetMapping("/isfollowed")
    public JsonResult isFollowed(Principal principal, @RequestParam(value = "uid") String uId) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> user = userRepository.findByUsername(principal.getName());
            Optional<User> other = userRepository.findByUid(uId);

            //
            if (other.isPresent()) {

                boolean result = user.get().getFollows().contains(other.get());

                //
                jsonResult.setMessage(result ? "已经关注" : "暂未关注");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(result);

            } else {

                jsonResult.setMessage("uid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        jsonResult.setMessage("is followed");
        jsonResult.setStatus_code(200);

        return jsonResult;
    }

    /**
     * 判断好友关系
     *
     * @param principal
     * @param uId
     * @return
     */
    @GetMapping("/relation")
    public JsonResult relation(Principal principal, @RequestParam(value = "uid") String uId) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> user = userRepository.findByUsername(principal.getName());
            Optional<User> other = userRepository.findByUid(uId);

            //
            if (other.isPresent()) {

                boolean result = user.get().getFollows().contains(other.get());

                // TODO: 返回四种关系：陌生人、关注、粉丝、好友之一

                //
                jsonResult.setMessage("获取好友关系成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(result);

            } else {

                jsonResult.setMessage("uid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        jsonResult.setMessage("is followed");
        jsonResult.setStatus_code(200);

        return jsonResult;
    }

    /**
     * 关注某个用户
     *
     * @param principal
     * @param map
     */
    @PostMapping("/follow")
    @ResponseBody
    public JsonResult addFollow(Principal principal, @RequestBody Map map) {

        String uId = (String) map.get("uid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 关注者
            Optional<User> follower = userRepository.findByUsername(principal.getName());
            // 被关注者
            Optional<User> followed = userRepository.findByUid(uId);
            //
            if (follower.get().getUid().equals(followed.get().getUid())) {
                // 判断是否关注自己
                //
                jsonResult.setMessage("不能关注自己");
                jsonResult.setStatus_code(-3);
                jsonResult.setData(uId);
            } else if (follower.get().getFollows().contains(followed)) {
                // 判断是否已经关注
                //
                jsonResult.setMessage("已经关注过，不需要重复关注");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(uId);

            } else {
                //
                follower.get().getFollows().add(followed.get());
                userRepository.save(follower.get());
                //
                jsonResult.setMessage("关注成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(uId);
            }
        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 取消关注某个用户
     *
     * @param principal
     * @param map
     */
    @PostMapping("/unfollow")
    @ResponseBody
    public JsonResult unFollow(Principal principal, @RequestBody Map map) {

        String uId = (String) map.get("uid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 取消关注者
            Optional<User> unfollower = userRepository.findByUsername(principal.getName());
            // 被关注者
            Optional<User> unfollowed = userRepository.findByUid(uId);
            //
            if (unfollower.get().getUid().equals(unfollowed.get().getUid())) {
                // 判断是否关注自己
                //
                jsonResult.setMessage("无需取消关注自己");
                jsonResult.setStatus_code(-3);
                jsonResult.setData(uId);

            }else if (unfollowed.get().getFans().contains(unfollower.get())) {
                // 判断是否曾经关注过
                //
                unfollowed.get().getFans().remove(unfollower.get());
                userRepository.save(unfollowed.get());

                // 返回结果
                jsonResult.setMessage("取消关注成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(uId);

            } else {

                // 返回结果
                jsonResult.setMessage("未曾关注，无需取消关注");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(uId);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 申请30天试用
     * 默认自动申请成功，直接开始试用
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/apply")
    @ResponseBody
    public JsonResult applyTry(Principal principal, @RequestBody Map map) {

        String mobile = (String) map.get("mobile");

        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 删除 role_free
            Iterator iterator = adminOptional.get().getRoles().iterator();
            while (iterator.hasNext()) {
                Role role = (Role) iterator.next();
                if (role.getValue().equals(RoleConsts.ROLE_FREE)) {
                    iterator.remove();
                }
            }

            // 加入 role_try
            Optional<Role> roleTryOptional = roleRepository.findByValue(RoleConsts.ROLE_TRY);
            if (roleTryOptional.isPresent()) {
                adminOptional.get().getRoles().add(roleTryOptional.get());
            }

            // 保存
            adminOptional.get().setMobile(mobile);
            userRepository.save(adminOptional.get());

            // 返回结果
            jsonResult.setMessage("申请试用成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(adminOptional.get());

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 删除客服账号
     *
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        Integer id = (Integer) map.get("id");

        JsonResult jsonResult = new JsonResult();

        logger.info("delete user id: {}", id);

        // 验证管理员
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {


            userRepository.deleteById(Long.valueOf(id));

            jsonResult.setMessage("删除客服账号成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(id);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 登出
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public JsonResult logout(Principal principal, @RequestBody Map map) {

        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 保存退出登录状态
                Status status = new Status();
                status.setStatus(StatusConsts.USER_STATUS_LOGOUT);
                status.setClient(client);
                status.setUser(userOptional.get());
                statusRepository.save(status);

                // 客服登出成功，设置为登出状态status
                 userOptional.get().setAcceptStatus(status.getStatus());
                 userRepository.save(userOptional.get());

                // 清空空闲数量
                redisService.removeAgentIdleCount(userOptional.get().getUid());


                // TODO：关闭当前正在会话的thread


                // TODO: 清空redis: ThreadCount统计


                //
                Map<String, String> objectMap = new HashMap<>(2);
                objectMap.put("uid", userOptional.get().getUid());
                objectMap.put("status", status.getStatus());

                // 返回结果
                jsonResult.setMessage("退出登录成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(objectMap);

            } else {

                jsonResult.setMessage("退出登录-管理员账号不存在");
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
     * 搜索过滤工作组
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             //
                             @RequestParam(value = "realName") String realName,
                             @RequestParam(value = "client") String client) {

        logger.info(" real_name {}, client {}", realName, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

            // 构建动态查询条件
            Specification specification = (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicateList = new ArrayList<>();

                predicateList.add(criteriaBuilder.like(root.get("realName"), "%" + realName + "%"));

                // 有可能查到其他企业的客服账号
                predicateList.add(criteriaBuilder.equal(root.get("subDomain"), adminOptional.get().getSubDomain()));


                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            };


            Page<User> userPage = userRepository.findAll(specification, pageable);

            // 返回结果
            jsonResult.setMessage("搜索客服成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(userPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

}


