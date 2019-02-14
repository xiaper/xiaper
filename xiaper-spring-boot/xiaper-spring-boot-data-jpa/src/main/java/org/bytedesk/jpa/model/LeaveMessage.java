package org.bytedesk.jpa.model;

import javax.persistence.*;

/**
 * 留言
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "leave_message")
public class LeaveMessage extends AuditModel {

    private static final long serialVersionUID = 5541007700000492374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "lid", unique = true, nullable = false)
    private String lid;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "content")
    private String content;

    @Column(name = "is_replied")
    private boolean replied;

    @Column(name = "reply")
    private String reply;

    /**
     * 留言类型：工作组留言、一对一留言
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 访客, 留言者
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User visitor;

    /**
     * 留言对应工作组
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workGroup_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WorkGroup workGroup;

    /**
     * 一对一被留言人
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User agent;

    /**
     * 管理员:
     * FIXME: 为方便查询添加此字段，有待优化
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WorkGroup getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup) {
        this.workGroup = workGroup;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }
}
