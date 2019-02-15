package io.xiaper.jpa.model;

import io.xiaper.jpa.constant.TypeConsts;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 浏览网页中的访客
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "browse")
public class Browse extends AuditModel {

    private static final long serialVersionUID = -5686535153522958228L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "bid", unique = true, nullable = false)
    private String bid;

    /**
     * stomp session id
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 来源网页
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "referrer_id")
    private Url referrer;

    /**
     * 当前网页
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "url_id")
    private Url url;


    /**
     * 离开、接入
     */
    @Column(name = "actioned")
    private String actioned;

    /**
     * 离开、接入 网页时间
     */
    @Column(name = "actioned_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date actionedAt;

    /**
     * 访客
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User visitor;

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

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Url getReferrer() {
        return referrer;
    }

    public void setReferrer(Url referrer) {
        this.referrer = referrer;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getActioned() {
        return actioned;
    }

    public void setActioned(String actioned) {
        this.actioned = actioned;
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

    public void setWorkGroup(WorkGroup workgroup) {
        this.workGroup = workgroup;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    /**
     * 离开
     */
    public void leave() {
        this.actioned = TypeConsts.BROWSE_TYPE_LEAVED;
        this.actionedAt = new Date();
    }

    /**
     * 接入
     */
    public void accept() {
        this.actioned = TypeConsts.BROWSE_TYPE_ACCEPTED;
        this.actionedAt = new Date();
    }



}
