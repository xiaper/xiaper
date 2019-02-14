package org.bytedesk.jpa.constant;

/**
 *
 * @author bytedesk.com
 */
public class StatusConsts {

    /**
     * app、网站已经上线
     */
    public static final String APP_STATUS_RELEASE = "release";
    /**
     * 开发对接中...
     */
    public static final String APP_STATUS_DEBUG = "debug";
    /**
     * 已上线，开发新版本中
     */
    public static final String APP_STATUS_VERSION = "version";


    /**
     * 消息发送状态:
     *
     * 1. 发送中
     */
    public static final String MESSAGE_STATUS_SENDING = "sending";
    /**
     * 2. 已经存储到服务器
     */
    public static final String MESSAGE_STATUS_STORED = "stored";
    /**
     * 3. 对方已收到
     */
    public static final String MESSAGE_STATUS_RECEIVED = "received";
    /**
     * 4. 对方已读
     */
    public static final String MESSAGE_STATUS_READ = "read";
    /**
     * 5. 发送错误
     */
    public static final String MESSAGE_STATUS_ERROR =  "error";


    /**
     * 用户在线状态：
     */
    /**
     * 跟服务器建立长连接
     */
    public static final String USER_STATUS_CONNECTED = "connected";
    /**
     * 断开长连接
     */
    public static final String USER_STATUS_DISCONNECTED = "disconnected";
    /**
     * 在线状态
     */
    public static final String USER_STATUS_ONLINE = "online";
    /**
     * 离线状态
     */
    public static final String USER_STATUS_OFFLINE = "offline";
    /**
     * 忙
     */
    public static final String USER_STATUS_BUSY = "busy";
    /**
     * 离开
     */
    public static final String USER_STATUS_AWAY = "away";
    /**
     * 登出
     */
    public static final String USER_STATUS_LOGOUT = "logout";
    /**
     * 登录
     */
    public static final String USER_STATUS_LOGIN = "login";
    /**
     * 离开
     */
    public static final String USER_STATUS_LEAVE = "leave";
    /**
     * 话后
     */
    public static final String USER_STATUS_AFTER  = "after";
    /**
     * 就餐
     */
    public static final String USER_STATUS_EAT = "eat";
    /**
     * 小休
     */
    public static final String USER_STATUS_REST = "rest";
    /**
     * 签入
     */
    public static final String USER_STATUS_SIGN_IN = "sign_in";
    /**
     * 签出
     */
    public static final String USER_STATUS_SIGN_OUT = "sign_out";


    /**
     * 排队状态: 排队中
     */
    public static final String QUEUE_STATUS_QUEUING = "queuing";
    /**
     * 已接入
     */
    public static final String QUEUE_STATUS_ACCEPTED = "accepted";
    /**
     * 已离开
     */
    public static final String QUEUE_STATUS_LEAVED = "leaved";




}
