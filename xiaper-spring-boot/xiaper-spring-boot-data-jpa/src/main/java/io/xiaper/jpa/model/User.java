package io.xiaper.jpa.model;

import io.xiaper.jpa.constant.BdConstants;
import io.xiaper.jpa.constant.ClientConsts;
import io.xiaper.jpa.constant.RoleConsts;
import io.xiaper.jpa.constant.StatusConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * 注册用户、分配客服账号、访客账号、系统账号、机器人账号
 *
 * FIXME: Oracle不允许创建user表名（user为Oracle保留字），故用users替代
 *
 * @author bytedesk.com
 */
@Entity
// 软删除
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "users")
// https://blog.csdn.net/qq_30581017/article/details/79747516
// No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no pro
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class User extends AuditModel implements UserDetails {

	private static final long serialVersionUID = -7810168718373868640L;

	/**
	 * 用户信息表:
	 *
	 * 限制：用户名、昵称、邮箱、手机 均唯一
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 唯一数字id，保证唯一性
	 * 替代id
	 *
	 * FIXME: uid为Oracle关键字，故修改为uuid
	 */
	@Column(name = "uuid", unique = true)
	private String uid;

	/**
	 * 全平台唯一，公司子账号通过添加@subDomain来区分唯一性
	 */
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;

	/**
	 * FIXME: password为Oracle保留字
	 */
	@JsonIgnore
	@Column(name = "passwords")
	private String password;

	/**
	 * 手机号
	 */
	@Column(name = "mobile")
	private String mobile;

	/**
	 * 对话页面显示
	 */
	@Column(name = "nickname")
	private String nickname;

	/**
	 * 后台统计显示
	 */
	@Column(name = "real_name")
	private String realName;

	/**
	 * 头像
	 */
	@Column(name = "avatar")
	private String avatar;

	/**
	 * 公司名
	 */
	@Column(name = "company")
	private String company;

	/**
	 * TODO: 有待完善，对应im用户，注册之后可创建多个团队
	 */


	/**
	 * 企业号
	 */
	@Column(name = "sub_domain")
	private String subDomain;

	/**
	 * 所有客服端同步在线状态，
	 * TODO: 通过另外的表来存储各个端状态变化
	 * FIXME: 此字段仅记录接待状态：在线、忙线等，不记载登录状态
	 *
	 * @OneToOne
	 * @JoinColumn(name = "status_id")
	 * private Status status
	 */

	/**
	 * 长连接状态
	 */
	@Column(name = "connection_status")
	private String connectionStatus;

	/**
	 * 客服设置接待状态
	 */
	@Column(name = "accept_status")
	private String acceptStatus;

	/**
	 * IM用户在线状态
	 */
	@Column(name = "im_status")
	private String imStatus;

	/**
	 * 标记匿名访客，来源端
	 */
	@Column(name = "client")
	private String client;

	/**
	 * false 为离职禁止登录，true 为在职
	 */
	@Column(name = "is_enabled")
	private boolean enabled = true;

	/**
	 * 是否是机器人, 默认非机器人
	 */
	@Column(name = "is_robot")
	private boolean robot = false;

	/**
	 * 进入页面欢迎语
	 * 设置默认值
	 */
	@Column(name = "welcome_tip")
	private String welcomeTip = BdConstants.DEFAULT_AGENT_WELCOME_TIP;

	/**
	 * ios离线消息推送token
	 */
	@Column(name = "device_token")
	private String deviceToken;

	/**
	 * 是否自动回复
	 */
	@Column(name = "is_auto_reply")
	private boolean autoReply = false;

	/**
	 * 自动回复内容
	 * TODO: 添加默认值
	 */
	@Column(name = "auto_reply_content")
	private String autoReplyContent = BdConstants.DEFAULT_AGENT_AUTO_REPLY_CONTENT;

	/**
	 * 个性签名
	 */
	@Column(name = "description")
	private String description = BdConstants.DEFAULT_AGENT_DESCRIPTION;

	/**
	 * 同时最大接待会话数目, 默认10个
	 */
	@Column(name = "max_thread_count")
	private int maxThreadCount = 10;

	/**
	 * 用于软删除soft delete, 也即：
	 * 在数据库中设置此字段为1标记删除，而非直接从数据库中删除
	 */
	@Column(name = "is_deleted")
	private boolean deleted = false;

	/**
	 * 访客所有的会话thread
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "visitor")
	private Set<Thread> threads = new HashSet<>();

	/**
	 * 访客所有的fingerprint
	 * TODO: 过滤掉多余字段
	 */
	@OneToMany(mappedBy = "visitor", fetch = FetchType.EAGER)
	@Where(clause = " my_key = 'os-name' or my_key = 'browser-name' ")
	private Set<FingerPrint2> fingerPrint2s = new HashSet<>();

	/**
	 * 用户所属工作组
	 */
	@JsonIgnore
//	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	@JoinTable(name = "users_workgroup",
//			joinColumns = @JoinColumn(name = "users_id",
//					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
//			inverseJoinColumns = @JoinColumn(name = "workgroup_id",
//					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Set<WorkGroup> workGroups = new HashSet<>();

	/**
	 * 用户标签
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_tag",
			joinColumns = @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	private Set<Tag> tags = new HashSet<>();

	/**
	 * 粉丝，关注我的
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_follow",
			joinColumns = @JoinColumn(name = "follow_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	private Set<User> fans = new HashSet<>();

	/**
	 * 我关注的
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_follow",
			joinColumns = @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(name = "follow_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	private Set<User> follows = new HashSet<>();

	/**
	 * robot, 机器人账号
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_robot",
			joinColumns = @JoinColumn(name = "users_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(name = "robot_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	private Set<User> robots = new HashSet<>();

	/**
	 * 多对多关系
	 * targetEntity = Role.class,
	 */
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "users_role",
			joinColumns = @JoinColumn(name = "users_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(name = "role_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	@BatchSize(size = 20)
	private Set<Role> roles = new HashSet<>();

	/**
	 * 非基于角色的权限, 单独赋予的权限
	 */
	@ManyToMany(targetEntity = Authority.class,fetch = FetchType.EAGER)
	@JoinTable(name = "users_authority",
			joinColumns = @JoinColumn(name = "users_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(name = "authority_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	private Set<Authority> userAuthorities = new HashSet<>();

	/**
	 * 客服账号创建者
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id",
			foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
	private User user;

	/**
	 * TODO: 还没有分清楚，不太理解！
	 * Java's transient keyword is used to denote that a field is not to be serialized,
	 * whereas JPA's @Transient annotation is used to indicate that a field is not to be persisted in the database
	 */
	@JsonIgnore
	@Transient
	private Set<GrantedAuthority> authorities = new HashSet<>();


	public User() {
	}

	public User(String uid) {
		this.uid = uid;
	}

	public User(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.nickname = user.getNickname();
		this.email = user.getEmail();
		this.mobile = user.getMobile();
		this.avatar = user.getAvatar();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<String> authorities = new ArrayList<>();
		List<Authority> collection = new ArrayList<>();

		for (Role role : roles) {
			collection.addAll(role.getAuthorities());
		}
		for (Authority item : collection) {
			authorities.add(item.getName());
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (String authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority));
		}

		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<User> getFans() {
		return fans;
	}

	public void setFans(Set<User> fans) {
		this.fans = fans;
	}

	public Set<User> getFollows() {
		return follows;
	}

	public void setFollows(Set<User> follows) {
		this.follows = follows;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Set<WorkGroup> getWorkGroups() {
		return workGroups;
	}

	public void setWorkGroups(Set<WorkGroup> workGroups) {
		this.workGroups = workGroups;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Thread> getThreads() {
		return threads;
	}

	public void setThreads(Set<Thread> threads) {
		this.threads = threads;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Authority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<Authority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public Set<FingerPrint2> getFingerPrint2s() {
		return fingerPrint2s;
	}

	public void setFingerPrint2s(Set<FingerPrint2> fingerPrint2s) {
		this.fingerPrint2s = fingerPrint2s;
	}

	public Set<User> getRobots() {
		return robots;
	}

	public void setRobots(Set<User> robots) {
		this.robots = robots;
	}

	public boolean isRobot() {
		return robot;
	}

	public void setRobot(boolean robot) {
		this.robot = robot;
	}

	public String getWelcomeTip() {
		return welcomeTip;
	}

	public void setWelcomeTip(String welcomeTip) {
		this.welcomeTip = welcomeTip;
	}

	public boolean isAutoReply() {
		return autoReply;
	}

	public void setAutoReply(boolean autoReply) {
		this.autoReply = autoReply;
	}

	public String getAutoReplyContent() {
		return autoReplyContent;
	}

	public void setAutoReplyContent(String autoReplyContent) {
		this.autoReplyContent = autoReplyContent;
	}

	public String getImStatus() {
		return imStatus;
	}

	public void setImStatus(String imStatus) {
		this.imStatus = imStatus;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}

		if (this.uid == null || o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;
		return this.uid.equals(user.uid);
	}

	@Override
	public int hashCode() {

		return Objects.hash(uid);
	}

	/**
	 * 返回默认工作组
	 *
	 * @return
	 */
	public WorkGroup defaultWorkGroup() {
		Iterator iterator = workGroups.iterator();
		while (iterator.hasNext()) {
			WorkGroup workGroup = (WorkGroup) iterator.next();
			if (workGroup.isDefaulted()) {
				return workGroup;
			}
		}
		return null;
	}


	public boolean isSuper() {

		if (roles == null) {
			return false;
		}

		Iterator iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = (Role) iterator.next();
			if (role.getValue().equals(RoleConsts.ROLE_SUPER)) {
				return true;
			}
		}
		return false;
	}


	public boolean isAdmin() {

		if (roles == null) {
			return false;
		}

		Iterator iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = (Role) iterator.next();
			if (role.getValue().equals(RoleConsts.ROLE_ADMIN)
					|| role.getValue().equals(RoleConsts.ROLE_SUPER)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 客服组长
	 * @return
	 */
	public boolean isWorkGroupAdmin() {

//		Iterator iterator = roles.iterator();
//		while (iterator.hasNext()) {
//			Role role = (Role) iterator.next();
//			if (role.getValue().equals(RoleConsts.ROLE_WORKGROUP_ADMIN)) {
//				return true;
//			}
//		}
//		return false;

		if (workGroups == null) {
			return false;
		}

		Iterator iterator = workGroups.iterator();
		while (iterator.hasNext()) {
			WorkGroup workGroup = (WorkGroup) iterator.next();
			if (workGroup.getAdmin() != null && workGroup.getAdmin().getUid().equals(this.getUid())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 客服人员
	 * @return
	 */
	public boolean isAgent() {

		if (roles == null) {
			return false;
		}

		Iterator iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = (Role) iterator.next();
			if (role.getValue().equals(RoleConsts.ROLE_WORKGROUP_AGENT)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断用户是否是访客
	 *
	 * @return
	 */
	public boolean isVisitor() {

		if (roles == null) {
			return false;
		}

		Iterator iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = (Role) iterator.next();
			if (role.getValue().equals(RoleConsts.ROLE_VISITOR)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 访客是否来自公众号
	 * @return
	 */
	public boolean isWeChatMp() {
		return this.client != null && this.client.equals(ClientConsts.CLIENT_WECHAT_MP);
	}

	/**
	 * 访客来自小程序
	 * @return
	 */
	public boolean isWeChatMini() {
		return this.client != null && this.client.equals(ClientConsts.CLIENT_WECHAT_MINI);
	}


	/**
	 * 是否可以接待访客
	 *
	 * @return
	 */
	public boolean isAvailable() {

		if (this.connectionStatus == null || this.acceptStatus == null) {
			return false;
		}

		return this.connectionStatus.equals(StatusConsts.USER_STATUS_CONNECTED)
				&& this.acceptStatus.equals(StatusConsts.USER_STATUS_ONLINE);
	}


	/**
	 * 获取客服账号的管理员
	 *
	 * @return
	 */
	@JsonIgnore
	public User getAdmin() {

		if (user != null) {
			return user;
		} else {
			return this;
		}
	}


	@JsonIgnore
	public User getRoBotUser() {

		if (robots.size() > 0) {
			return (User) robots.toArray()[0];
		}

		return null;
	}





}



