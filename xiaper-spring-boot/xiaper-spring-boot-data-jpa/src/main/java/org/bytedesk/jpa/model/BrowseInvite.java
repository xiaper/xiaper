package org.bytedesk.jpa.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 邀请访客会话记录
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "browse_invite")
public class BrowseInvite extends AuditModel {

    private static final long serialVersionUID = 2258583307204931978L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "b_iid", unique = true, nullable = false)
    private String bIid;

    /**
     * 访客浏览记录
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "browse_id")
    private Browse browse;

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
     * 被邀请人
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    /**
     * 被邀请访客端
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
     * 所属工作组
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workgroup_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WorkGroup workGroup;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getbIid() {
        return bIid;
    }

    public void setbIid(String bIid) {
        this.bIid = bIid;
    }

    public Browse getBrowse() {
        return browse;
    }

    public void setBrowse(Browse browse) {
        this.browse = browse;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromClient() {
        return fromClient;
    }

    public void setFromClient(String fromClient) {
        this.fromClient = fromClient;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getToClient() {
        return toClient;
    }

    public void setToClient(String toClient) {
        this.toClient = toClient;
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

    public WorkGroup getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup) {
        this.workGroup = workGroup;
    }
}

