package io.xiaper.jpa.model;

import javax.persistence.*;


/**
 * 个人设置 Preference
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "setting")
public class Setting extends AuditModel {

    private static final long serialVersionUID = 9184726391219783935L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 是否开启web通知
     */
    @Column(name = "is_web_notification")
    private boolean webNotification;

    /**
     * 个人积分
     */
    @Column(name = "score")
    private int score;


    /**
     * 所对应的客服账号
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isWebNotification() {
        return webNotification;
    }

    public void setWebNotification(boolean webNotification) {
        this.webNotification = webNotification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
