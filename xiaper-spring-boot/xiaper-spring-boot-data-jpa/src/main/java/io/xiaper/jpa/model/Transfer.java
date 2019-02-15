package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * 会话转接记录
 * 转交群组记录
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "transfer")
public class Transfer extends AuditModel {

    private static final long serialVersionUID = 339497651874370824L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "t_tid", unique = true, nullable = false)
    private String tTid;

    /**
     * 会话
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thread_id")
    private Thread thread;

    /**
     * 发起人
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    /**
     * 发起请求客户端
     */
    @Column(name = "from_client")
    private String fromClient;

    /**
     * 被转接人
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User toUser;

    /**
     * 被转接客服 响应客服端
     */
    @Column(name = "to_client")
    private String toClient;

    /**
     * 是否接受转接
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gettTid() {
        return tTid;
    }

    public void settTid(String tTid) {
        this.tTid = tTid;
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
}
