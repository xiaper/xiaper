package org.bytedesk.jpa.model;

import javax.persistence.*;

/**
 * 用户状态变化记录
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "status")
public class Status extends AuditModel {

    private static final long serialVersionUID = 6402595251870248098L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 状态，具体请查看
     * StatusConsts
     */
    @Column(name = "status")
    private String status;

    /**
     * 状态类型：IM在线状态、客服接待状态、长连接状态
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 客户端
     */
    @Column(name = "client")
    private String client;

    /**
     * 来自stomp的session id
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
