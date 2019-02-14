package org.bytedesk.jpa.constant;


/**
 *
 * @author bytedesk.com
 */
public class BdConstants {

    /**
     * 项目名称
     */
    public static final String PROJECT_NAME = "ByteDesk";
    public static final boolean IS_DEBUG = false;

    /**
     * 是否私有部署
     */
    public static final boolean IS_DEPLOY = false;

    /**
     * 创建web默认值
     */
    public static final String DEFAULT_WEB_NAME = "默认网站";
    public static final String DEFAULT_WEB_URL = "www.bytedesk.com";
    public static final String DEFAULT_AT_EMAIL = "@bytedesk.com";
    public static final String DEFAULT_BROWSE_START = " 网页浏览开始";
    public static final String DEFAULT_BROWSE_END = " 网页浏览结束";
    public static final String DEFAULT_BROWSE_INVITE = "邀请访客会话";
    public static final String DEFAULT_ROBOT_NICKNAME = "智能客服";
    public static final String DEFAULT_ROBOT_DESCRIPTION = "智能客服描述";
    public static final String DEFAULT_ONLINE = "上线";
    public static final String DEFAULT_OFFLINE = "离线";
    public static final String DEFAULT_QUEUE_LEAVE = "退出排队";

    /**
     * 创建workgroup默认值
     */
    public static final String DEFAULT_WORK_GROUP_NAME = "默认工作组";
    public static final String DEFAULT_WORK_GROUP_SLOGAN = "全心全意为您服务";
    public static final String DEFAULT_WORK_GROUP_WELCOME_TIP = "您好，请稍候，客服将很快为您服务";
    public static final String DEFAULT_WORK_GROUP_ROBOT_WELCOME_TIP = "您好，我是智能助理，请直接输入问题";
    /**
     *  TODO: 支持管理后台自定义
     */
    public static final String DEFAULT_WORK_GROUP_ACCEPT_TIP = "您好，有什么可以帮您的？";
    /**
     * TODO: 支持管理后台自定义
     */
    public static final String DEFAULT_WORK_GROUP_NON_WORKING_TIME_TIP = "当前非工作时间，请自助查询或留言";
    /**
     * TODO: 支持管理后台自定义
     */
    public static final String DEFAULT_WORK_GROUP_OFFLINE_TIP = "当前无客服在线，请自助查询或留言";
    public static final String DEFAULT_WORK_GROUP_DESCRIPTION = "这是一个默认工作组，系统自动生成";
    public static final String DEFAULT_WORK_GROUP_AGENT_CLOSE_TIP = "客服关闭会话";
    public static final String DEFAULT_WORK_GROUP_VISITOR_CLOSE_TIP = "访客关闭会话";
    public static final String DEFAULT_WORK_GROUP_AUTO_CLOSE_TIP = "长时间没有对话，系统自动关闭会话";
    public static final String DEFAULT_WORK_GROUP_NON_WORKING_TIME_CLOSE_TIP = "非工作时间关闭会话";
    public static final String DEFAULT_WORK_GROUP_OFFLINE_CLOSE_TIP = "客服离线关闭会话";
    public static final String DEFAULT_WORK_GROUP_INVITE_RATE = "邀请评价";
    public static final String DEFAULT_WORK_GROUP_VISITOR_RATE = "已评价";
    public static final String DEFAULT_WORK_GROUP_QUESTIONNAIRE_TIP = "咨询前问卷";
    public static final String DEFAULT_WORK_GROUP_ROBOT_ANSWER_NOT_FOUND = "抱歉，未找到相应答案";

    /**
     * 工作组
     */
    public static final String DEFAULT_WORK_GROUP_REQUEST_THREAD = " 请求会话";
    public static final String DEFAULT_WORK_GROUP_RE_REQUEST_THREAD = " 继续会话";
    public static final String DEFAULT_WORK_GROUP_JOIN_THREAD = " 加入会话";
    public static final String DEFAULT_WORK_GROUP_TRANSFER = "转接会话";
    public static final String DEFAULT_WORK_GROUP_TRANSFER_ACCEPT = "接受转接会话";
    public static final String DEFAULT_WORK_GROUP_TRANSFER_REJECT = "拒绝转接会话";
    public static final String DEFAULT_WORK_GROUP_INVITE = "邀请会话";
    public static final String DEFAULT_WORK_GROUP_INVITE_ACCEPT = "接受邀请会话";
    public static final String DEFAULT_WORK_GROUP_INVITE_REJECT = "拒绝邀请会话";
    public static final String DEFAULT_WORK_GROUP_QUEUE_ACCEPT = "接入队列会话";

    /**
     * 群组
     */
    public static final String DEFAULT_GROUP_DESCRIPTION = "群组描述：这个家伙很懒，什么也没写";
    public static final String DEFAULT_GROUP_ANNOUNCEMENT = "群组公告：这个家伙很懒，什么也没写";


    /**
     * 请求一对一会话
     */
    public static final String DEFAULT_VISITOR_REQUEST_THREAD = " 请求会话";
    public static final String DEFAULT_AGENT_OFFLINE_TIP = "当前客服不在线，请留言";
    public static final String DEFAULT_AGENT_WELCOME_TIP = "您好，有什么可以帮您的?";
    public static final String DEFAULT_AGENT_DESCRIPTION = "全心全意为您服务";
    public static final String DEFAULT_AGENT_AUTO_REPLY_CONTENT = "无自动回复";

    /**
     * 通过淘宝接口查询ip所属城市：
     * http://ip.taobao.com/service/getIpInfo.php?ip=60.186.185.142
     */
    public static final String IP_INFO_TAOBAO_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    /**
     * 私有部署用户验证有效性URL
     */
    public static final String PRIVATE_SERVER_VALIDATE_URL = "https://api.bytedesk.com/visitor/api/server/validate?uId=";

    /**
     * 时间格式
     */
    public static final String DATETIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 微信授权事件类型
     */
    public static final String WECHAT_OPEN_PLATFORM_INFO_TYPE_AUTHORIZED = "authorized";
    public static final String WECHAT_OPEN_PLATFORM_INFO_TYPE_UNAUTHORIZED = "unauthorized";
    public static final String WECHAT_OPEN_PLATFORM_INFO_TYPE_UPDATE_AUTHORIZED = "updateauthorized";
    public static final String WECHAT_OPEN_PLATFORM_INFO_TYPE_COMPONENT_VERIFY_TICKET = "component_verify_ticket";

    /**
     * 跳转去授权URL
     */
    public static final String WECHAT_OPEN_PLATFORM_REDIRECT_URI ="https://wechat.bytedesk.com/wechat/mp/oauth/redirect/";
    /**
     * 授权事件接收URL
     */
    public static final String WECHAT_OPEN_PLATFORM_OAUTH_CALLBACK_URI = "https://wechat.bytedesk.com/wechat/mp/oauth/callback";
    /**
     * 绑定公众后跳转URL
     */
    public static final String WECHAT_OPEN_PLATFORM_ADMIN_CALLBACK_URL = ".bytedesk.com/admin#/admin/setting/app";





}

