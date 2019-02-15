package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 邀请会话记录
 * 邀请加入群组记录
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "invite")
public class Invite extends AuditModel {

    private static final long serialVersionUID = -3712250254849335047L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "t_iid", unique = true, nullable = false)
    private String tIid;

    /**
     * 邀请类型：会话邀请、群组邀请
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 会话
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Thread thread;

    /**
     * 群组邀请
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Group group;

    /**
     * 发起人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User fromUser;

    /**
     * 发起请求客户端
     */
    @Column(name = "from_client")
    private String fromClient;

    /**
     * 被邀请人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User toUser;

    /**
     * 被邀请客服 响应客服端
     */
    @Column(name = "to_client")
    private String toClient;

    /**
     * 是否接受邀请
     */
    @Column(name = "is_accepted")
    private boolean accepted = false;

    /**
     * 被转接人接受或拒绝时间
     */
    @Column(name = "actioned_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date actionedAt;

    /**
     * 被邀请加入的会话，可以随时退出，此字段记录退出会话时间戳
     */
    @Column(name = "exit_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date exitAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gettIid() {
        return tIid;
    }

    public void settIid(String tIid) {
        this.tIid = tIid;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Date getActionedAt() {
        return actionedAt;
    }

    public void setActionedAt(Date actionedAt) {
        this.actionedAt = actionedAt;
    }

    public String getFromClient() {
        return fromClient;
    }

    public void setFromClient(String fromClient) {
        this.fromClient = fromClient;
    }

    public String getToClient() {
        return toClient;
    }

    public void setToClient(String toClient) {
        this.toClient = toClient;
    }

    public Date getExitAt() {
        return exitAt;
    }

    public void setExitAt(Date exitAt) {
        this.exitAt = exitAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
