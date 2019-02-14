package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 会话
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "thread")
public class Thread extends AuditModel {

    private static final long serialVersionUID = 1194607482984119161L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "tid", unique = true, nullable = false)
    private String tid;

    /**
     * 公众号、小程序 token
     */
    @Column(name = "token")
    private String token;

    /**
     * 来自stomp的session id
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 最新消息内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 最新消息时间戳
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date timestamp;

    /**
     * 未读消息数
     */
    @Column(name = "unread_count")
    private int unreadCount;

    /**
     * 是否是当前会话，客户端点击会话之后，通知服务器并保存，同时通知其他所有端
     */
    @Column(name = "is_current")
    private boolean current = false;

    /**
     * 会话类型: 工作组会话、指定坐席、同事一对一、群组会话
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 非匿名用户，会话请求客户端
     *
     * TODO: 待完善逻辑实现
     */
    @Column(name = "client")
    private String client;

    /**
     * 会话关闭类型：客服关闭、访客关闭、超时自动关闭
     */
    @Column(name = "close_type")
    private String closeType;

    /**
     * 对应队列
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Queue queue;

    /**
     * 访客，只能由一个访客
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User visitor;

    /**
     * 一对一会话：Contact
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User contact;

    /**
     * 群组会话：Group
     * 注：group为Oracle保留字
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Group group;

    /**
     * 主客服，accept会话的客服账号，或被转接客服
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User agent;

    /**
     * 客服，可以有多个客服加入一个会话，
     * 包括：主客服，被邀请客服，监控客服
     *
     * 工作组会话：所有客服
     * 访客跟客服一对一：所有客服
     * 同事一对一：所有客服
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "thread_agent",
            joinColumns = @JoinColumn(name = "thread_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> agents = new HashSet<>();

    /**
     * 一对多
     */
    @JsonIgnore
    @OneToMany(mappedBy = "thread")
    private Set<Message> messages = new HashSet<>();

    /**
     * 所属工作组
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workgroup_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WorkGroup workGroup;

    /**
     * 会话置顶
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "thread_top",
            joinColumns = @JoinColumn(name = "thread_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> topSet = new HashSet<>();

    /**
     * 免打扰
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "thread_disturb",
            joinColumns = @JoinColumn(name = "thread_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> disturbSet = new HashSet<>();

    /**
     * 标记会话未读
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "thread_unread",
            joinColumns = @JoinColumn(name = "thread_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> unreadSet = new HashSet<>();


    /**
     * 标记会话删除：不再出现在该用户的会话列表中，而非从数据库中删除
     * TODO: 直接不从数据库中查出
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "thread_deleted",
            joinColumns = @JoinColumn(name = "thread_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> deletedSet = new HashSet<>();


    /**
     * 会话开始时间
     */
    @Column(name = "started_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startedAt;

    /**
     * 是否指定坐席
     */
    @Column(name = "is_appointed")
    private boolean appointed = false;

    /**
     * 是否评价过
     */
    @Column(name = "is_rated")
    private boolean rated;

    /**
     * 会话是否结束
     */
    @Column(name = "is_closed")
    private boolean closed;

    /**
     * 是否为系统自动关闭
     */
    @Column(name = "is_auto_close")
    private boolean autoClose;

    /**
     * 会话结束时间
     */
    @Column(name = "closed_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date closedAt;


    public Thread() {
    }

    public Thread(String tid) {
        this.tid = tid;
    }

    /**
     * 关闭会话
     */
    public void close() {
        closedAt = new Date();
        closed = true;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public void increaseUnreadCount () {
        this.unreadCount += 1;
    }

    public void clearUnreadCount () {
        this.unreadCount = 0;
    }

    public WorkGroup getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup) {
        this.workGroup = workGroup;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<User> getAgents() {
        return agents;
    }

    public void setAgents(Set<User> agents) {
        this.agents = agents;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public boolean isAutoClose() {
        return autoClose;
    }

    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAppointed() {
        return appointed;
    }

    public void setAppointed(boolean appointed) {
        this.appointed = appointed;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Set<User> getTopSet() {
        return topSet;
    }

    public void setTopSet(Set<User> topSet) {
        this.topSet = topSet;
    }

    public Set<User> getDisturbSet() {
        return disturbSet;
    }

    public void setDisturbSet(Set<User> disturbSet) {
        this.disturbSet = disturbSet;
    }

    public Set<User> getUnreadSet() {
        return unreadSet;
    }

    public void setUnreadSet(Set<User> unreadSet) {
        this.unreadSet = unreadSet;
    }

    public Set<User> getDeletedSet() {
        return deletedSet;
    }

    public void setDeletedSet(Set<User> deletedSet) {
        this.deletedSet = deletedSet;
    }

    public boolean isTop(User user) {


        Iterator iterator = this.topSet.iterator();
        while (iterator.hasNext()) {
            User user1 = (User) iterator.next();
            if (user.getUid().equals(user1.getUid())) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnread(User user) {
        Iterator iterator = this.unreadSet.iterator();
        while (iterator.hasNext()) {
            User user1 = (User) iterator.next();
            if (user.getUid().equals(user1.getUid())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDeleted(User user) {
        Iterator iterator = this.deletedSet.iterator();
        while (iterator.hasNext()) {
            User user1 = (User) iterator.next();
            if (user.getUid().equals(user1.getUid())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", tid='" + tid + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", queue=" + queue +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", unreadCount='" + unreadCount + '\'' +
                ", closed=" + closed +
                ", visitor=" + visitor +
                ", workGroup=" + workGroup +
                ", startedAt=" + startedAt +
                ", closedAt=" + closedAt +
                '}';
    }
}
