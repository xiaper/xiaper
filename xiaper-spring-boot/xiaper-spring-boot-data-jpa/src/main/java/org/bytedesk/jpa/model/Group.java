package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 群
 * 注：group为mysql保留关键字，所有表名修改为groups
 *
 * TODO: 群组角色Role/权限Authority
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "groups")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Group extends AuditModel {

    private static final long serialVersionUID = 7195675282154179891L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "gid", unique = true, nullable = false)
    private String gid;

    /**
     * 群昵称: 群组管理员设置群组名称，其他人修改昵称写到groups_details表中
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 类型：群、讨论组
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 最大成员数
     */
    @Column(name = "max_count")
    private int maxCount = 10000;

    /**
     * 群简介：只有管理员可以修改
     */
    @Column(name = "description")
    private String description;

    /**
     * 群公告：只有管理员可以发布
     */
    @Column(name = "announcement")
    private String announcement;

    /**
     * 群组是否已经解散：只有管理员可以解散
     * 让人数小于3的时候，自动解散
     */
    @Column(name = "is_dismissed")
    private boolean dismissed;

    /**
     * 申请加群记录
     */


    /**
     * 禁言成员列表
     *
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "groups_muted",
            joinColumns = @JoinColumn(name = "groups_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> muted = new HashSet<>();

    /**
     * 群成员
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "groups_member",
            joinColumns = @JoinColumn(name = "groups_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> members = new HashSet<>();

    /**
     * 副管理员
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "groups_admin",
            joinColumns = @JoinColumn(name = "groups_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> admins = new HashSet<>();

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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public Set<User> getMuted() {
        return muted;
    }

    public void setMuted(Set<User> muted) {
        this.muted = muted;
    }
}
