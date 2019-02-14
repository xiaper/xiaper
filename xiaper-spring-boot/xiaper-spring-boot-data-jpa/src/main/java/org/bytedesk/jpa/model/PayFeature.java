package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 购买的功能
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "pay_feature")
public class PayFeature extends AuditModel {

    private static final long serialVersionUID = 4732637095097569217L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    /**
     * 购买坐席数，默认一个
     * 可以同时登录的客服账号数量
     */
    @Column(name = "current_agent_count", columnDefinition = "int default 1")
    private int currentAgentCount = 1;

    /**
     * 创建者
     */
    @JsonIgnore
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

    public int getCurrentAgentCount() {
        return currentAgentCount;
    }

    public void setCurrentAgentCount(int currentAgentCount) {
        this.currentAgentCount = currentAgentCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
