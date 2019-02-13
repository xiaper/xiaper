package org.bytedesk.jpa.model;

import org.bytedesk.jpa.constant.AvatarConsts;
import org.bytedesk.jpa.constant.BdConstants;
import org.bytedesk.jpa.constant.RouteConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * 客服工作组: 技能组
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "workgroup")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class WorkGroup extends AuditModel {

    private static final long serialVersionUID = 733054358991055193L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "wid", unique = true, nullable = false)
    private String wid;

    /**
     * 工作组名称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 默认头像
     */
    @Column(name = "avatar")
    private String avatar = AvatarConsts.DEFAULT_WORK_GROUP_AVATAR_URL;

    /**
     * 是否默认机器人接待
     */
    @Column(name = "is_default_robot")
    private boolean defaultRobot = false;

    /**
     * 是否无客服在线时，启用机器人接待
     */
    @Column(name = "is_offline_robot")
    private boolean offlineRobot = false;

    /**
     * 宣传语，对话框顶部，
     * 活动、宣传语
     */
    @Lob
    @Column(name = "slogan")
    private String slogan  = BdConstants.DEFAULT_WORK_GROUP_SLOGAN;

    /**
     * 进入页面欢迎语
     */
    @Column(name = "welcome_tip")
    private String welcomeTip = BdConstants.DEFAULT_WORK_GROUP_WELCOME_TIP;

    /**
     * 接入客服欢迎语，
     * 如果客服账号没有设置，则启用工作组欢迎语
     */
    @Column(name = "accept_tip")
    private String acceptTip = BdConstants.DEFAULT_WORK_GROUP_ACCEPT_TIP;

    /**
     * 非工作时间提示
     */
    @Column(name = "non_working_time_tip")
    private String nonWorkingTimeTip = BdConstants.DEFAULT_WORK_GROUP_NON_WORKING_TIME_TIP;

    /**
     * 离线提示
     */
    @Column(name = "offline_tip")
    private String offlineTip = BdConstants.DEFAULT_WORK_GROUP_OFFLINE_TIP;

    /**
     * 客服关闭会话提示语
     */
    @Column(name = "close_tip")
    private String closeTip = BdConstants.DEFAULT_WORK_GROUP_AGENT_CLOSE_TIP;

    /**
     * 会话自动关闭会话提示语
     */
    @Column(name = "auto_close_tip")
    private String autoCloseTip = BdConstants.DEFAULT_WORK_GROUP_AUTO_CLOSE_TIP;

    /**
     * 是否强制评价
     */
    @Column(name = "is_force_rate")
    private boolean forceRate = false;

    /**
     * 路由类型：
     *
     * 广播pubsub、
     * 轮询robin、
     * 熟客优先recent、
     * 最少接待least、
     * 智能分配smart
     */
    @Column(name = "route_type")
    private String routeType = RouteConsts.ROUTE_TYPE_ROBIN;

    /**
     * 是否是系统分配的默认工作组，不允许删除
     */
    @Column(name = "is_default")
    private boolean defaulted = false;

    /**
     * 判断是否为普通部门，true：普通部门，false：客服部门
     */
    @Column(name = "is_department")
    private boolean department = false;

    /**
     * TODO: 弹出时长
     * 访客到达网站多久之后，自动弹客服窗口
     */
    @Column(name = "is_auto_pop")
    private boolean autoPop = false;

    /**
     * 默认打开网页10s后，弹窗
     */
    @Column(name = "pop_after_time_length", columnDefinition = "int default 10")
    private int popAfterTimeLength  = 10;

    /**
     * 描述、介绍, 对话框 右侧 "关于我们"
     */
    @Column(name = "about")
    private String about = "关于我们";

    /**
     * 左上角，昵称下面描述语
     */
    @Column(name = "description")
    private String description = BdConstants.DEFAULT_WORK_GROUP_DESCRIPTION;

    /**
     * 咨询前问卷
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    /**
     * 值班客服组
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "on_duty_workgroup_id")
    private WorkGroup onDutyWorkGroup;

    /**
     * 金吉列大学长：一个分公司有多个工作组
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * 金吉列大学长：一个国家对应多个工作组，一个工作组可以接待多个国家
     */
//    @JsonIgnore
//    @ManyToMany(mappedBy = "workGroups", fetch = FetchType.LAZY)
//    private Set<Country> countries = new HashSet<>();

    /**
     * 工作组所属app
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "workgroup_app",
            joinColumns = @JoinColumn(name = "workgroup_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "app_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<App> apps = new HashSet<>();

    /**
     * 是否根据工作时间进行路由，
     * 具体参见worktimes表
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "workgroup_worktime",
            joinColumns = @JoinColumn(name = "workgroup_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "worktime_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<WorkTime> workTimes = new HashSet<>();

    /**
     * 工作组所包含用户
     * TODO: 每个组内成员所对应的角色，每个角色所对应的权限
     */
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "workgroup_users",
            joinColumns = @JoinColumn(name = "workgroup_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> users = new HashSet<>();

    /**
     * 管理员，FIXME: 暂时一个工作组仅允许一个管理员
     */
//    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//    @JoinTable(name = "workgroup_admins", joinColumns = @JoinColumn(name = "workgroup_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
//    private Set<User> admins = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User admin;


    /**
     * TODO: 按照工作组给客服赋予角色：一个客服账号在工作组A是客服组长，在工作组B是客服组员
     */


    /**
     * 创建者
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<WorkTime> getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(Set<WorkTime> workTimes) {
        this.workTimes = workTimes;
    }

    public boolean isDefaultRobot() {
        return defaultRobot;
    }

    public void setDefaultRobot(boolean defaultRobot) {
        this.defaultRobot = defaultRobot;
    }

    public boolean isOfflineRobot() {
        return offlineRobot;
    }

    public void setOfflineRobot(boolean offlineRobot) {
        this.offlineRobot = offlineRobot;
    }

    public String getWelcomeTip() {
        return welcomeTip;
    }

    public void setWelcomeTip(String welcomeTip) {
        this.welcomeTip = welcomeTip;
    }

    public String getAcceptTip() {
        return acceptTip;
    }

    public void setAcceptTip(String acceptTip) {
        this.acceptTip = acceptTip;
    }

    public String getOfflineTip() {
        return offlineTip;
    }

    public void setOfflineTip(String offlineTip) {
        this.offlineTip = offlineTip;
    }

    public boolean isForceRate() {
        return forceRate;
    }

    public void setForceRate(boolean forceRate) {
        this.forceRate = forceRate;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    public void setDefaulted(boolean defaulted) {
        this.defaulted = defaulted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getNonWorkingTimeTip() {
        return nonWorkingTimeTip;
    }

    public void setNonWorkingTimeTip(String nonWorkingTimeTip) {
        this.nonWorkingTimeTip = nonWorkingTimeTip;
    }

    public boolean isDepartment() {
        return department;
    }

    public void setDepartment(boolean department) {
        this.department = department;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public WorkGroup getOnDutyWorkGroup() {
        return onDutyWorkGroup;
    }

    public void setOnDutyWorkGroup(WorkGroup onDutyWorkGroup) {
        this.onDutyWorkGroup = onDutyWorkGroup;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public boolean isAutoPop() {
        return autoPop;
    }

    public void setAutoPop(boolean autoPop) {
        this.autoPop = autoPop;
    }

    public int getPopAfterTimeLength() {
        return popAfterTimeLength;
    }

    public void setPopAfterTimeLength(int popAfterTimeLength) {
        this.popAfterTimeLength = popAfterTimeLength;
    }

    public String getCloseTip() {
        return closeTip;
    }

    public void setCloseTip(String closeTip) {
        this.closeTip = closeTip;
    }

    public String getAutoCloseTip() {
        return autoCloseTip;
    }

    public void setAutoCloseTip(String autoCloseTip) {
        this.autoCloseTip = autoCloseTip;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    /**
     * 是否启用咨询前调查问卷
     *
     * @return
     */
    public boolean isQuestionnaire() {
        return questionnaire != null;
    }

    /**
     * 是否工作时间
     *
     * @return
     */
    public boolean isWorkTime() {
        Iterator iterator = workTimes.iterator();
        while (iterator.hasNext()) {
            WorkTime workTime = (WorkTime) iterator.next();
            if (workTime.isWorkTime()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (this.wid == null || o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkGroup workGroup = (WorkGroup) o;

        return this.wid.equals(workGroup.getWid());
    }

    @Override
    public int hashCode() {

        return Objects.hash(wid);
    }
}
