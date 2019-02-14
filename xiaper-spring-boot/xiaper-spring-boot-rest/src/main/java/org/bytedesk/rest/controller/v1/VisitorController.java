package org.bytedesk.rest.controller.v1;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bytedesk.jpa.constant.*;
import org.bytedesk.jpa.model.*;
import org.bytedesk.jpa.repository.*;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.redis.RedisService;
import org.bytedesk.rest.service.EmailService;
import org.bytedesk.rest.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 专门针对匿名未登录用户提供的api
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/visitor/api")
@Api(value="访客", tags="用户登录之前接口", produces = "application/json;charset=utf-8")
public class VisitorController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AppRepository appRepository;

    @Autowired
    WorkGroupRepository workgroupRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    RedisService redisService;

    @Autowired
    SimpUserRegistry simpUserRegistry;

    @Autowired
    BrowseRepository browseRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ValidationRepository validationRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    HttpServletRequest request;

    @Autowired
    IpService ipService;


    /**
     * 保留二级域名，不允许用户注册
     */
    public static final String[] SET_VALUES = new String[] { "im", "call", "bbs", "erp", "support", "rtc", "crm" };
    public static final Set<String> RESERVED_SUBDOMAINS = new HashSet<>(Arrays.asList(SET_VALUES));

    /**
     * 测试接口专用：
     *
     * GET
     * 通过Gateway访问：
     * localhost:8006/uaa/visitor/api/users
     * 注意：其中多了"uaa/"是在gateWay网关配置文件中配置，可以自行修改
     *
     * 直接访问：
     *  localhost:8000/visitor/api/users
     *
     * 获取ip地域信息：
     *  http://ip.taobao.com/service/getIpInfo.php?ip=60.186.185.142
     *
     * @return
     */
    @GetMapping("/users")
    public JsonResult test() {

        JsonResult jsonResult = new JsonResult();
        jsonResult.setMessage("online users");
        jsonResult.setStatus_code(200);

        // 获取当前在线用户信息, 仅包含stomp在线用户，不含有mqtt在线用户
        List<String> userList = new ArrayList<>();
        for (SimpUser user : simpUserRegistry.getUsers()) {
            userList.add(user.toString());
        }

        //
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("users", userList);
        objectMap.put("hostname", JpaUtil.hostname());
        objectMap.put("ip", JpaUtil.ipAddress());

        //
        RestTemplate restTemplate = new RestTemplate();
        String ipInfo = restTemplate.getForObject(BdConstants.IP_INFO_TAOBAO_URL + JpaUtil.ipAddress(), String.class);
        objectMap.put("ipInfo", ipInfo);

        JSONObject jsonObject = JSONObject.parseObject(ipInfo);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        objectMap.put("region", dataObject.getString("region"));
        objectMap.put("city", dataObject.getString("city"));
        objectMap.put("isp", dataObject.getString("isp"));

        //
        jsonResult.setData(objectMap);

        return jsonResult;
    }

    /**
     * 为访客端生成用户名:
     * localhost:8000/visitor/api/username?client=web
     *
     * TODO: 限制某个IP一天之内最多注册5个账号
     * TODO: 根据地理位置生成昵称
     *
     * @param client
     * @return
     */
    @ApiOperation(value="匿名注册接口", notes="获取匿名用户，用于获取访客咨询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subDomain", value = "企业号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "client", value = "来源客户端，服务器调用写死为 'web' ", required = true, dataType = "String"),
    })
    @GetMapping("/username")
    public JsonResult username(@RequestParam(value = "subDomain") String subDomain,
                               @RequestParam(value = "client") String client) {

        String username = JpaUtil.randomId();
        String email = username + BdConstants.DEFAULT_AT_EMAIL;
        String password = username;

        logger.info("username {}, email {}, password {}", username, email, password);

        // FIXME: 获取ip所对应的城市, 设置用户昵称
        // Ip ip = ipService.getIPZone();

        // 保存访客
        User user = new User();
        user.setUid(JpaUtil.randomId());
        user.setUsername(username);
        //
        user.setNickname(username.substring(3));
        user.setEmail(email);
        user.setPassword(password);
        user.setSubDomain(subDomain);
        user.setClient(client);

        userService.saveVisitor(user);

        //
        JsonResult jsonResult = new JsonResult();
        jsonResult.setMessage("创建用户名成功");
        jsonResult.setStatus_code(200);
        jsonResult.setData(user);

        return jsonResult;
    }

    /**
     * 注册自定义普通用户：用于IM,
     * post中含有下面四个：
     *
     * username：登录用户名
     * nickname：昵称
     * password：密码
     * subDomain：企业号，如：vip
     * client：如：web
     *
     * TODO: 限制某个IP一天之内最多注册5个账号
     *
     * @param map
     * @return
     */
    @ApiOperation(value="自定义注册接口", notes="注册自定义普通用户：用于IM")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "nickname", value = "昵称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "subDomain", value = "企业号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "client", value = "来源客户端，服务器调用写死为 'web' ", required = true, dataType = "String"),
    })
    @PostMapping("/register/user")
    @ResponseBody
    public JsonResult registerUser(@RequestBody Map map) {

        String username = (String) map.get("username");
        String nickname = (String) map.get("nickname");
        String password = (String) map.get("password");
        String subDomain = (String) map.get("subDomain");
        String client = (String) map.get("client");
        logger.info("username {}, nickname {}, subDomain {}, client ", username, nickname, subDomain, client);

        // 多租户平台需要添加企业号后缀
        username = username + "@" + subDomain;
        //
        JsonResult jsonResult = new JsonResult();

        Optional<User> userOptional = userRepository.findByUsernameAndSubDomain(username, subDomain);
        if (!userOptional.isPresent()) {

            String email = username + BdConstants.DEFAULT_AT_EMAIL;

            // 保存访客
            User user = new User();
            user.setUid(JpaUtil.randomId());
            user.setUsername(username);
            user.setNickname(nickname);
            user.setEmail(email);
            user.setPassword(password);
            user.setSubDomain(subDomain);
            user.setClient(client);
            userService.saveUser(user);

            // 返回结果
            jsonResult.setMessage("保存用户名成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(user);

        } else {

            // 返回结果
            jsonResult.setMessage("用户名已经存在");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 注册页面：验证邮箱唯一性
     *
     * @param email
     * @return
     */
    @GetMapping("/email/validate")
    public JsonResult emailValidate(@RequestParam(value = "email") String email) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            jsonResult.setMessage("email已经存在");
            jsonResult.setStatus_code(-1);
        }
        else {
            jsonResult.setMessage("email可以使用");
            jsonResult.setStatus_code(200);
        }
        jsonResult.setData(email);

        return jsonResult;
    }


    /**
     * 找回密码：验证邮箱是否存在
     *
     * @param email
     * @return
     */
    @GetMapping("/email/forget")
    public JsonResult emailForget(@RequestParam(value = "email") String email) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            jsonResult.setMessage("email验证通过");
            jsonResult.setStatus_code(200);
        }
        else {
            jsonResult.setMessage("email不存在");
            jsonResult.setStatus_code(-1);
        }
        jsonResult.setData(email);

        return jsonResult;
    }


    /**
     * 发送邮箱验证码
     * localhost:8000/visitor/api/email/code
     *
     * @return
     */
    @GetMapping("/email/code")
    public JsonResult emailCode(@RequestParam(value = "email") String email) {

        JsonResult jsonResult = new JsonResult();
        jsonResult.setMessage("email code success");
        jsonResult.setStatus_code(200);

        // 生成6位随机数
        int min = 100000;
        int max = 999999;
        int code = new Random().nextInt(max)%(max-min+1) + min;

        // 缓存
        redisService.cacheValidateCode(email, code);
        // 发送邮件
        emailService.sendRegisterMail(email, code);

        // 验证码返回前端，可以作为前端直接验证
        jsonResult.setData(code);

        return jsonResult;
    }


    /**
     * 服务端验证验证码是否正确
     *
     * @return
     */
    @GetMapping("/email/code/validate")
    public JsonResult emailCodeValidate(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "code") int code) {

        JsonResult jsonResult = new JsonResult();

        boolean isValid = redisService.validateCode(email, code);
        if (isValid) {
            jsonResult.setMessage("code validate success");
            jsonResult.setStatus_code(200);
        } else {
            jsonResult.setMessage("code validate failed");
            jsonResult.setStatus_code(-1);
        }
        jsonResult.setData(email);

        return jsonResult;
    }


    /**
     * 验证二级域名
     * 过滤掉某些保留subDomain, 不允许注册
     *
     * @param subDomain
     * @return
     */
    @GetMapping("/sub_domain/validate")
    public JsonResult subDomainValidate(@RequestParam(value = "sub_domain") String subDomain) {

        JsonResult jsonResult = new JsonResult();

        if (subDomain.trim().length() < 4) {

            jsonResult.setMessage("长度不能小于4个字符");
            jsonResult.setStatus_code(-1);

        } else if (RESERVED_SUBDOMAINS.contains(subDomain)) {

            jsonResult.setMessage("保留关键字，不能使用，请重新填写");
            jsonResult.setStatus_code(-2);

        } else {

            Long count = userRepository.countBySubDomain(subDomain);
            if (count > 0) {

                jsonResult.setMessage("已经存在，不能使用，请重新填写");
                jsonResult.setStatus_code(-3);

            } else {

                jsonResult.setMessage("subDomain is available");
                jsonResult.setStatus_code(200);
            }
        }

        jsonResult.setData(subDomain);

        return jsonResult;
    }

    /**
     * 注册管理员
     *
     * POST
     * 通过网关调用：
     * localhost:8006/uaa/visitor/api/register
     *
     * POSTMAN:
     * 1. 选择POST请求类型
     * 2. Headers 设置：Content-Type: application/json
     * 3. Body里面选择raw类型，并输入内容：
     * {
     * 	"username": "test1",
     * 	"password": "123456"
     * }
     * 4. 执行send
     *
     *
     * 登录实例：
     * 通过网关调用:
     * http://localhost:8006/uaa/oauth/token
     * 直接登录：
     * http://localhost:8007/oauth/token
     *
     * POSTMAN:
     *  1. 选择POST请求类型
     *  2. params里面设置参数：
     *    grant_type：password
     *    username: test1
     *    password: 123456
     *    client_id: ios
     *    client_secrect: XSf9jKCAPpeMwDZakt8AkvKppHEmXAb5sX0FtXwn
     *
     * 此接口默认生成企业账号
     *
     * TODO: 注册页面添加选择账号类型：增加注册个人账号类型
     *
     * 注册接口
     */
    @PostMapping("/register")
    @ResponseBody
    public JsonResult registerAdmin(@RequestBody Map map) {

        String email = (String) map.get("email");
        String password = (String) map.get("password");
        // 为简化注册，使用自动生成企业号(二级域名), 允许管理员后台修改，保留后6位
        String subDomain = JpaUtil.randomId().substring(9);

        JsonResult jsonResult = new JsonResult();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            jsonResult.setMessage("用户名已经存在");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(email);

        } else {
            jsonResult.setMessage("注册成功");
            jsonResult.setStatus_code(200);

            //
            User user = new User();
            user.setUid(JpaUtil.randomId());
            // 默认 username == email
            user.setUsername(email);
            user.setEmail(email);
            user.setPassword(password);
            user.setSubDomain(subDomain);
            userService.save(user);

            // 生成默认app、workgroup等，
            // 便于用户登录后直接生成url网站链接,

            // 创建默认网站
            App webApp = new App();
            webApp.setAid(JpaUtil.randomId());
            webApp.setName(BdConstants.DEFAULT_WEB_NAME);
            webApp.setAvatar(AvatarConsts.DEFAULT_WEB_AVATAR_URL);
            webApp.setUrl(BdConstants.DEFAULT_WEB_URL);
            webApp.setStatus(StatusConsts.APP_STATUS_DEBUG);
            webApp.setDescription(BdConstants.DEFAULT_WEB_NAME);
            webApp.setType(TypeConsts.APP_TYPE_WEB);
            webApp.setDefaulted(true);
            webApp.setUser(user);
            appRepository.save(webApp);

            // 创建默认工作组
            WorkGroup workGroup = new WorkGroup();
            workGroup.setWid(JpaUtil.randomId());
            workGroup.setNickname(BdConstants.DEFAULT_WORK_GROUP_NAME);
            workGroup.setAvatar(AvatarConsts.DEFAULT_WORK_GROUP_AVATAR_URL);
            workGroup.setDefaultRobot(false);
            workGroup.setOfflineRobot(false);
            workGroup.setForceRate(false);
            workGroup.setWelcomeTip(BdConstants.DEFAULT_WORK_GROUP_WELCOME_TIP);
            workGroup.setAcceptTip(BdConstants.DEFAULT_WORK_GROUP_ACCEPT_TIP);
            workGroup.setNonWorkingTimeTip(BdConstants.DEFAULT_WORK_GROUP_NON_WORKING_TIME_TIP);
            workGroup.setOfflineTip(BdConstants.DEFAULT_WORK_GROUP_OFFLINE_TIP);
            workGroup.setRouteType(RouteConsts.ROUTE_TYPE_ROBIN);
            workGroup.setDefaulted(true);
            workGroup.setDescription(BdConstants.DEFAULT_WORK_GROUP_DESCRIPTION);

            // 设置默认工作时间为 00:00:00 <= now <= 24:00:00
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            Date startTime = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endTime = calendar.getTime();

            WorkTime workTime = new WorkTime();
            workTime.setStartTime(startTime);
            workTime.setEndTime(endTime);
            workGroup.getWorkTimes().add(workTime);

            workGroup.getApps().add(webApp);
            workGroup.setUser(user);
            workgroupRepository.save(workGroup);

            // 把用户加入工作组
            user.getWorkGroups().add(workGroup);

            // TODO: 生成默认帮助中心Support分类


            // TODO: 生成一个意见反馈模板模板


            // TODO: 生成默认机器人账号
            User robotUser = new User();
            robotUser.setUid(JpaUtil.randomId());
            String robotUsername = JpaUtil.randomId();
            robotUser.setUsername(robotUsername);
            robotUser.setEmail(robotUsername + BdConstants.DEFAULT_AT_EMAIL);
            robotUser.setPassword(robotUsername);
            robotUser.setNickname(BdConstants.DEFAULT_ROBOT_NICKNAME);
            robotUser.setRealName(BdConstants.DEFAULT_ROBOT_NICKNAME);
            robotUser.setAvatar(AvatarConsts.DEFAULT_AGENT_AVATAR_URL);
            robotUser.setSubDomain(subDomain);
            robotUser.setEnabled(true);
            robotUser.setWelcomeTip(BdConstants.DEFAULT_WORK_GROUP_ROBOT_WELCOME_TIP);
            robotUser.setDescription(BdConstants.DEFAULT_ROBOT_DESCRIPTION);
            robotUser.setRobot(true);
            robotUser.setUser(user);
            // 设置为机器人角色
            Optional<Role> roleOptional = roleRepository.findByValue(RoleConsts.ROLE_ROBOT);
            if (roleOptional.isPresent()) {
                robotUser.getRoles().add(roleOptional.get());
            }
            user.getRobots().add(robotUser);

            // 保存
            userRepository.save(user);

            //
            jsonResult.setData(user);
        }

        return jsonResult;
    }






    /**
     * 忘记密码：修改密码
     *
     * @param map
     * @return
     */
    @PostMapping("/change")
    @ResponseBody
    public JsonResult change(@RequestBody Map map) {

        String email = (String) map.get("email");
        String password = (String) map.get("password");

        //
        JsonResult jsonResult = new JsonResult();

        //
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {

            //
            userOptional.get().setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(userOptional.get());

            // 返回结果
            jsonResult.setMessage("修改密码成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(true);

        } else {

            jsonResult.setMessage("email不存在");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 开放接口方便第三方修改访客资料
     *
     * @param map
     * @return
     */
    @PostMapping("/change/profile")
    @ResponseBody
    public JsonResult changeProfile(@RequestBody Map map) {

        String username = (String) map.get("username");
        String nickname = (String) map.get("nickname");

        //
        JsonResult jsonResult = new JsonResult();

        //
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {

            //
            userOptional.get().setNickname(nickname);
            userRepository.save(userOptional.get());


            // 返回结果
            jsonResult.setMessage("设置资料成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(true);

        } else {

            jsonResult.setMessage("设置资料失败-用户名不存在");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 获取所有角色,
     *
     * TODO: 增加自定义角色
     *
     * @return
     */
    @GetMapping("/roles")
    public JsonResult roles() {

        JsonResult jsonResult = new JsonResult();

        jsonResult.setMessage("所有角色");
        jsonResult.setStatus_code(200);

        // 所有客服组类型
        List<Role> roles = roleRepository.findByType(TypeConsts.ROLE_TYPE_WORKGROUP);
        //
//        Optional<Role> roleRobotOptional = roleRepository.findByValue(RoleConsts.ROLE_ROBOT);
//        if (roleRobotOptional.isPresent()) {
//            roles.add(roleRobotOptional.get());
//        }

        jsonResult.setData(roles);

        return jsonResult;
    }


    /**
     * 返回各个客户端版本号
     *
     * @param client
     * @return
     */
    @GetMapping("/version")
    public JsonResult version(@RequestParam(value = "aid") String aid,
                              @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<App> appOptional = appRepository.findByAid(aid);
        if (appOptional.isPresent()) {

            //
            jsonResult.setMessage(appOptional.get().getTip());
            jsonResult.setStatus_code(200);
            jsonResult.setData(appOptional.get().getVersion());

        } else {

            //
            jsonResult.setMessage("aid错误");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 获取帮助文档类别
     *
     * @return
     */
    @GetMapping("/categories")
    public JsonResult getCategories(@RequestParam(value = "uid") String uid) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUid(uid);
        if (adminOptional.isPresent()) {

            Set<Category> categorySet = categoryRepository.findByUserAndParent(adminOptional.get(), null);

            //
            jsonResult.setMessage("获取帮助文档类别成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(categorySet);

        } else {

            jsonResult.setMessage("获取帮助文档类别失败-管理员账号不存在");
            jsonResult.setStatus_code(-2);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 获取帮助文档
     *
     * @return
     */
    @GetMapping("/articles")
    public JsonResult getArticles(@RequestParam(value = "uid") String uid,
                                  @RequestParam(value = "page") int page,
                                  @RequestParam(value = "size") int size) {

        logger.info("uid {}, page: {}, size {}", uid, page, size);

        JsonResult jsonResult = new JsonResult();


        Optional<User> adminOptional = userRepository.findByUid(uid);
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");
            Page<Article> articlePage = articleRepository.findByUser(adminOptional.get(), pageable);

            //
            jsonResult.setMessage("获取帮助文档成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(articlePage);

        } else {

            jsonResult.setMessage("获取帮助文档失败-管理员账号不存在");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 远程验证私有部署是否有效
     */
    @GetMapping("/server/validate")
    public JsonResult serverValidate(@RequestParam(value = "uId") String uId) {

        JsonResult jsonResult = new JsonResult();

        Optional<Validation> validationOptional = validationRepository.findByUid(uId);
        if (validationOptional.isPresent()) {

            jsonResult.setMessage("远程验证成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(validationOptional.get());

        } else {

            jsonResult.setMessage("远程验证失败，请联系管理员");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(new Validation(false, "远程验证失败，请联系管理员"));
        }

        return jsonResult;
    }




}






