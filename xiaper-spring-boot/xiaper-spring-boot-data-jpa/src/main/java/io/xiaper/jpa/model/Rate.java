package io.xiaper.jpa.model;

import javax.persistence.*;

/**
 * 满意度评价
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "rate")
public class Rate extends AuditModel {

    private static final long serialVersionUID = -5655210890627449194L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 得分：非常满意 5、满意 4、一般 3、失望 2、极差 1
     */
    @Column(name = "score")
    private int score;

    /**
     * 附言
     */
    @Column(name = "note")
    private String note;

    /**
     * 评分满分值
     */
    @Column(name = "total")
    private int total;

    /**
     * 是否自动评分
     */
    @Column(name = "is_auto")
    private boolean auto;

    /**
     * 是否客服发起评价邀请
     */
    @Column(name = "is_invite")
    private boolean invite;

    /**
     * 来源客户端
     */
    @Column(name = "client")
    private String client;

    /**
     * 会话，严格来说，一次会话只能够评价一次
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thread_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Thread thread;

    /**
     * 访客
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User visitor;

    /**
     * 被评价客服：方便统计
     *
     *  FIXME: Error executing DDL "alter table rate add constraint FKtjkx92oi21nn2uq7oj39ayrjy foreign key (users_id) references users (id)"
     *  FIXME: alter table rate add constraint FKt1dgdlh0fdl0tlr1s4op3qwq0 foreign key (agent_id) references users (id)"
     *
     *  FIXME: java.sql.SQLException: Cannot add foreign key constraint
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User agent;

    /**
     * 管理员:
     * FIXME: 为方便查询添加此字段，有待优化
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isInvite() {
        return invite;
    }

    public void setInvite(boolean invite) {
        this.invite = invite;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
