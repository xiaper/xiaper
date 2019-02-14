package org.bytedesk.jpa.constant;

/**
 * 角色类型常量
 *
 * @author bytedesk.com
 */
public class RoleConsts {


    /**
     * 超级管理员
     */
    public static final String ROLE_SUPER = "ROLE_SUPER";
    /**
     * 注册管理员：注册用户默认角色
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    /**
     * 付费注册用户
     */
    public static final String ROLE_VIP = "ROLE_VIP";
    /**
     * 试用会员
     */
    public static final String ROLE_TRY = "ROLE_TRY";
    /**
     * 第三方代理公司
     */
    public static final String ROLE_PROXY = "ROLE_PROXY";
    /**
     * 免费注册用户
     */
    public static final String ROLE_FREE = "ROLE_FREE";
    /**
     * 普通员工：非客服人员
     */
    public static final String ROLE_MEMBER = "ROLE_MEMBER";
    /**
     * 智能客服：客服机器人
     */
    public static final String ROLE_ROBOT = "ROLE_ROBOT";
    /**
     * 访客
     */
    public static final String ROLE_VISITOR = "ROLE_VISITOR";
    /**
     * IM注册用户
     */
    public static final String ROLE_USER = "ROLE_USER";


    /**
     * 工作组：
     * 客服组长
     *
     */
    public static final String ROLE_WORKGROUP_ADMIN = "ROLE_WORKGROUP_ADMIN";
    /**
     * 客服账号：
     * 接待访客角色，如果要接待访客，必须赋予此角色
     */
    public static final String ROLE_WORKGROUP_AGENT = "ROLE_WORKGROUP_AGENT";
    /**
     * 质检账号
     */
    public static final String ROLE_WORKGROUP_CHECKER = "ROLE_WORKGROUP_CHECKER";



}






