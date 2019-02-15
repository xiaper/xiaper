package io.xiaper.jpa.model;

import javax.persistence.*;

/**
 * 消息发送状态
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "message_status")
public class MessageStatus extends AuditModel {

    private static final long serialVersionUID = -2497729805640281747L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /**
     * 状态，对应StatusConst里面的记录
     */
    @Column(name = "status")
    private String status;


    /**
     * 所属消息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Message message;


    /**
     * 相关用户
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


