package io.xiaper.jpa.constant;

/**
 *
 * @author bytedesk.com
 */
public class TypeConsts {

    /**
     * 消息类型：文本
     */
    public static final String MESSAGE_TYPE_TEXT = "text";
    /**
     * 图片消息
     */
    public static final String MESSAGE_TYPE_IMAGE = "image";
    /**
     * 文件类型
     */
    public static final String MESSAGE_TYPE_FILE = "file";
    /**
     * 录音消息
     */
    public static final String MESSAGE_TYPE_VOICE = "voice";
    /**
     * 自定义消息类型：内容放在content字段
     */
    public static final String MESSAGE_TYPE_CUSTOM = "custom";
    /**
     * 短视频
     */
    public static final String MESSAGE_TYPE_VIDEO = "video";
    /**
     *
     */
    public static final String MESSAGE_TYPE_SHORT_VIDEO = "shortvideo";
    /**
     *
     */
    public static final String MESSAGE_TYPE_LOCATION = "location";
    /**
     *
     */
    public static final String MESSAGE_TYPE_LINK = "link";
    /**
     *
     */
    public static final String MESSAGE_TYPE_EVENT = "event";
    /**
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454
     * subscribe(订阅)
     * TODO: 用户未关注时，进行关注后的事件推送
     */
    public static final String MESSAGE_EVENT_TYPE_SUBSCRIBE = "subscribe";
    /**
     * unsubscribe(取消订阅)
     */
    public static final String MESSAGE_EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    /**
     * 用户已关注时的事件推送
     */
    public static final String MESSAGE_EVENT_TYPE_SCAN = "SCAN";
    /**
     * 上报地理位置事件
     */
    public static final String MESSAGE_EVENT_TYPE_LOCATION = "LOCATION";
    /**
     * 点击菜单跳转链接时的事件推送
     */
    public static final String MESSAGE_EVENT_TYPE_VIEW = "VIEW";
    /**
     * 自定义菜单事件
     */
    public static final String MESSAGE_EVENT_TYPE_CLICK = "CLICK";
    /**
     * 点击自定义菜单请求客服
     */
    public static final String MESSAGE_EVENT_KEY_AGENT = "event_agent";
    /**
     * 点击自定义菜单请求'关于我们'
     */
    public static final String MESSAGE_EVENT_KEY_ABOUT = "event_about";
    /**
     * 机器人, 自动回复
     */
    public static final String MESSAGE_TYPE_ROBOT = "robot";
    /**
     * 问卷
     */
    public static final String MESSAGE_TYPE_QUESTIONNAIRE = "questionnaire";
    /**
     * 分公司，方便提取分公司所包含的国家，金吉列大学长
     */
    public static final String MESSAGE_TYPE_COMPANY = "company";
    /**
     * 选择工作组
     */
    public static final String MESSAGE_TYPE_WORK_GROUP = "workGroup";
    /**
     *
     */
    public static final String MESSAGE_TYPE_NOTIFICATION = "notification";
    /**
     * 非工作时间
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_NON_WORKING_TIME = "notification_non_working_time";
    /**
     * 客服离线，当前无客服在线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_OFFLINE = "notification_offline";
    /**
     * 开始浏览页面
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_START = "notification_browse_start";
    /**
     * 浏览页面结束
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_END = "notification_browse_end";
    /**
     * 邀请访客
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE = "notification_browse_invite";
    /**
     * 访客接受邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE_ACCEPT = "notification_browse_invite_accept";
    /**
     * 访客拒绝邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_BROWSE_INVITE_REJECT = "notification_browse_invite_reject";
    /**
     * 新进入会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_THREAD = "notification_thread";
    /**
     * 重新进入会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_THREAD_REENTRY = "notification_thread_reentry";
    /**
     * 新进入队列
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE = "notification_queue";
    /**
     * 排队中离开
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE_LEAVE = "notification_queue_leave";
    /**
     * 接入队列访客
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_QUEUE_ACCEPT = "notification_queue_accept";
    /**
     * 自动接入会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_ACCEPT_AUTO = "notification_accept_auto";
    /**
     * 手动接入
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_ACCEPT_MANUAL = "notification_accept_manual";
    /**
     * 上线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_CONNECT = "notification_connect";
    /**
     * 离线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_DISCONNECT = "notification_disconnect";
    /**
     * 离开会话页面
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_LEAVE = "notification_leave";
    /**
     * 邀请评价
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE_RATE = "notification_invite_rate";
    /**
     * 评价结果
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RATE_RESULT = "notification_rate_result";
    /**
     * 客服关闭会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_AGENT_CLOSE = "notification_agent_close";
    /**
     * 访客关闭会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_VISITOR_CLOSE = "notification_visitor_close";
    /**
     * 自动关闭会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_AUTO_CLOSE = "notification_auto_close";
    /**
     * 邀请会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE = "notification_invite";
    /**
     * 接受邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE_ACCEPT = "notification_invite_accept";
    /**
     * 拒绝邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_INVITE_REJECT = "notification_invite_reject";
    /**
     * 转接会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_TRANSFER = "notification_transfer";
    /**
     * 接受转接
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_TRANSFER_ACCEPT = "notification_transfer_accept";
    /**
     * 拒绝转接
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_TRANSFER_REJECT = "notification_transfer_reject";
    /**
     * 满意度请求
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RATE_REQUEST = "notification_rate_request";
    /**
     * 评价
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RATE = "notification_rate";
    /**
     * 连接状态
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_CONNECTION_STATUS = "notification_connection_status";
    /**
     * 接待状态
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_ACCEPT_STATUS = "notification_accept_status";
    /**
     * 消息预知
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_PREVIEW = "notification_preview";
    /**
     * 消息回执：收到消息之后回复给消息发送方
     * 消息content字段存放status: 1. MESSAGE_STATUS_RECEIVED(received), 2. MESSAGE_STATUS_READ(read)
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_RECEIPT = "notification_receipt";
    /**
     * 踢掉其他客户端
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_KICKOFF = "notification_kickoff";
    /**
     * webrtc通知初始化localStream
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_INVITE = "notification_webrtc_invite";
    /**
     * webrtc取消邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANCEL = "notification_webrtc_cancel";
    /**
     * webrtc邀请视频会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_VIDEO = "notification_webrtc_offer_video";
    /**
     * webrtc邀请音频会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_OFFER_AUDIO = "notification_webrtc_offer_audio";
    /**
     * 接受webrtc邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_ANSWER = "notification_webrtc_answer";
    /**
     * webrtc candidate信息
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_CANDIDATE = "notification_webrtc_candidate";
    /**
     * 接受webrtc邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_ACCEPT = "notification_webrtc_accept";
    /**
     * 拒绝webrtc邀请
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_REJECT = "notification_webrtc_reject";
    /**
     * 被邀请方视频设备 + peeConnection已经就绪
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_READY = "notification_webrtc_ready";
    /**
     * webrtc忙线
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_BUSY = "notification_webrtc_busy";
    /**
     * 结束webrtc会话
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_WEBRTC_CLOSE = "notification_webrtc_close";
    /**
     * 用户进入页面，来源于小程序
     */
    public static final String MESSAGE_TYPE_USER_ENTER_TEMPSESSION = "user_enter_tempsession";
    /**
     * 创建群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_CREATE = "notification_group_create";
    /**
     * 更新群名称、简介等
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_UPDATE = "notification_group_update";
    /**
     * 群公告
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_ANNOUNCEMENT = "notification_group_announcement";
    /**
     * 邀请多人加入群
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_INVITE = "notification_group_invite";
    /**
     * 受邀请：同意
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_INVITE_ACCEPT = "notification_group_invite_accept";
    /**
     * 受邀请：拒绝
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_INVITE_REJECT = "notification_group_invite_reject";
    /**
     * 不需要审核加入群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_JOIN = "notification_group_join";
    /**
     * 主动申请加入群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY = "notification_group_apply";
    /**
     * 同意：主动申请加群
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY_APPROVE = "notification_group_apply_approve";
    /**
     * 拒绝：主动申请加群
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_APPLY_DENY = "notification_group_apply_deny";
    /**
     * 踢人
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_KICK = "notification_group_kick";
    /**
     * 禁言
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_MUTE = "notification_group_mute";
    /**
     * 移交群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER = "notification_group_transfer";
    /**
     * 移交群组：同意、接受
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER_ACCEPT = "notification_group_transfer_accept";
    /**
     * 移交群组：拒绝
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_TRANSFER_REJECT = "notification_group_transfer_reject";
    /**
     * 退出群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_WITHDRAW = "notification_group_withdraw";
    /**
     * 解散群组
     */
    public static final String MESSAGE_TYPE_NOTIFICATION_GROUP_DISMISS = "notification_group_dismiss";


    /**
     * app类型：
     *
     * 网站
     */
    public static final String APP_TYPE_WEB = "web";
    /**
     * app
     */
    public static final String APP_TYPE_APP = "app";
    /**
     * 安卓平台
     */
    public static final String APP_PLATFORM_ANDROID = "android";
    /**
     * 苹果
     */
    public static final String APP_PLATFORM_IOS = "ios";
    /**
     * 安卓 + 苹果
     */
    public static final String APP_PLATFORM_BOTH = "both";

    /**
     * 角色类型:
     * 平台级角色
     */
    public static final String ROLE_TYPE_PLATFORM = "platform";
    /**
     * 工作组级角色
     */
    public static final String ROLE_TYPE_WORKGROUP = "workgroup";

    /**
     * 常用语: 平台类型
     */
    public static final String CUW_TYPE_PLATFORM = "platform";
    /**
     * 常用语: 公司类型
     */
    public static final String CUW_TYPE_COMPANY = "company";
    /**
     * 常用语: 自己创建类型
     */
    public static final String CUW_TYPE_MINE = "mine";


    /**
     * 类别类型：智能客服robot
     */
    public static final String CATEGORY_TYPE_ROBOT = "robot";
    /**
     * 帮助文档support
     */
    public static final String CATEGORY_TYPE_SUPPORT = "support";
    /**
     * 常用语分类：
     */
    public static final String CATEGORY_TYPE_CUW_MINE = "cuw_mine";
    public static final String CATEGORY_TYPE_CUW_COMPANY = "cuw_company";
    public static final String CATEGORY_TYPE_CUW_PLATFORM = "cuw_platform";
    /**
     * 意见反馈
     */
    public static final String CATEGORY_TYPE_FEEDBACK = "feedback";

    /**
     * 搜索过滤条件
     */
    public static final String SPECIFICATION_TYPE_THREAD = "thread";
    public static final String SPECIFICATION_TYPE_RATE = "rate";
    public static final String SPECIFICATION_TYPE_MESSAGE = "message";
    public static final String SPECIFICATION_TYPE_LEAVE_MESSAGE = "leave_message";
    public static final String SPECIFICATION_TYPE_STATUS = "status";
    public static final String SPECIFICATION_TYPE_QUEUE = "queue";
    public static final String SPECIFICATION_TYPE_BROWSE = "browse";

    /**
     * 统计维度
     */
    public static final String STATISTIC_TIME_TYPE_DAY = "day";
    public static final String STATISTIC_TIME_TYPE_HOUR = "hour";
    public static final String STATISTIC_DIMENSION_TYPE_AGENT = "agent";
    public static final String STATISTIC_DIMENSION_TYPE_WORKGROUP = "workGroup";
    public static final String STATISTIC_DIMENSION_TYPE_TOTAL = "total";

    /**
     * 浏览类型
     */
    public static final String BROWSE_TYPE_LEAVED = "leaved";
    public static final String BROWSE_TYPE_ACCEPTED = "accepted";

    /**
     * 工作组请求会话
     */
    public static final String THREAD_REQUEST_TYPE_WORK_GROUP = "workGroup";
    /**
     * 指定客服会话: 用appointed替代visitor
     */
    public static final String THREAD_REQUEST_TYPE_APPOINTED = "appointed";

    /**
     * 会话类型: 工作组会话、访客跟客服一对一
     *
     * TODO: THREAD_TYPE_WORK_GROUP 修改为 workGroup，并同步修改 安卓+iOS+web
     */
    public static final String THREAD_TYPE_WORK_GROUP = "thread";
    public static final String THREAD_TYPE_APPOINTED = "appointed";
    public static final String THREAD_TYPE_CONTACT = "contact";
    public static final String THREAD_TYPE_GROUP = "group";

    /**
     * 会话关闭类型：客服关闭、访客关闭、超时自动关闭、非工作时间关闭、客服离线无效会话关闭
     */
    public static final String THREAD_CLOSE_TYPE_AGENT = "agent";
    public static final String THREAD_CLOSE_TYPE_VISITOR = "visitor";
    public static final String THREAD_CLOSE_TYPE_TIMEOUT = "timeout";
    public static final String THREAD_CLOSE_TYPE_NON_WORKING_TIME = "non_working_time";
    public static final String THREAD_CLOSE_TYPE_OFFLINE = "offline";

    /**
     *  访客会话、同事一对一、群组会话
     *
     *  TODO: MESSAGE_SESSION_TYPE_WORK_GROUP 修改为 workGroup, 并增加 appointed 类型，并同步修改 安卓+iOS+web
     */
    public static final String MESSAGE_SESSION_TYPE_WORK_GROUP = THREAD_TYPE_WORK_GROUP;
    public static final String MESSAGE_SESSION_TYPE_APPOINTED= THREAD_TYPE_APPOINTED;
    public static final String MESSAGE_SESSION_TYPE_CONTACT = THREAD_TYPE_CONTACT;
    public static final String MESSAGE_SESSION_TYPE_GROUP = THREAD_TYPE_GROUP;

    /**
     * 问卷题目类型：单选、多选，单行输入input、多行输入textarea
     */
    public static final String QUESTIONNAIRE_ITEM_TYPE_RADIO = "radio";
    public static final String QUESTIONNAIRE_ITEM_TYPE_CHECKBOX = "checkbox";
    public static final String QUESTIONNAIRE_ITEM_TYPE_INPUT = "input";
    public static final String QUESTIONNAIRE_ITEM_TYPE_TEXTAREA = "textarea";

    /**
     * region类型, 代码长度分别为：省 2、市 4、区/县 6、镇 9
     */
    public static final String REGION_TYPE_PROVINCE = "province";
    public static final String REGION_TYPE_CITY = "city";
    public static final String REGION_TYPE_COUNTY = "county";
    public static final String REGION_TYPE_TOWN = "town";

    /**
     * 群组相关: 普通群组、讨论组
     */
    public static final String GROUP_TYPE_GROUP = "group";
    public static final String GROUP_TYPE_DISCUSS = "discuss";

    /**
     * 邀请相关：邀请会话、群组会话
     */
    public static final String INVITE_TYPE_THREAD = "thread";
    public static final String INVITE_TYPE_GROUP = "group";

    /**
     * 通知类型：群组通知
     */
    public static final String NOTICE_TYPE_GROUP = "group";





}






