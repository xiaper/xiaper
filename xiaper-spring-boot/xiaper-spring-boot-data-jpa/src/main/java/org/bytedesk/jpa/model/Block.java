package org.bytedesk.jpa.model;

import javax.persistence.*;

/**
 * 黑名单
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "block")
public class Block extends AuditModel {

    private static final long serialVersionUID = -348080117835035992L;

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
     * 拉黑类型，理由
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 备注
     */
    @Column(name = "note")
    private String note;

    /**
     * 被拉黑者
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blocked_user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User blockedUser;

    /**
     * 客服人员, 或者普通IM用户
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

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(User blockedUser) {
        this.blockedUser = blockedUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
