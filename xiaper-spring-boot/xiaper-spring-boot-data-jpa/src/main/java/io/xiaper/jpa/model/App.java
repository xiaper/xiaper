package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "app")
public class App extends AuditModel {

    private static final long serialVersionUID = 1066028027481249309L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "aid", unique = true, nullable = false)
    private String aid;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 版本
     */
    @Column(name = "app_version")
    private String version;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 网站url，或者app下载url
     */
    @Column(name = "url")
    private String url;

    /**
     * app key,
     * key为关键字
     */
    @Column(name = "app_key")
    private String key;

    /**
     * 类型：网站、App
     * TODO：枚举类型
     */
    @Column(name = "by_type")
    private String type;

    /**
     * android、ios 或者 both
     */
    @Column(name = "platform")
    private String platform;

    /**
     * 上线、测试、新版本
     * TODO：枚举
     */
    @Column(name = "status")
    private String status;

    /**
     * 升级新版tip
     */
    @Column(name = "tip")
    private String tip;

    /**
     * pem上传到服务器的路径
     */
    @Column(name = "pem_path")
    private String pemPath;

    /**
     * pem密码
     */
    @Column(name = "pem_password")
    private String pemPassword;


    /**
     * 描述，介绍
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否是系统分配的默认网站，不允许删除
     * FIXME: columnDefinition 不能用在Oracle，需要删除
     */
    @Column(name = "is_default")
    private boolean defaulted = false;

    /**
     * 一个工作组 可以属于多个app，一个app可以包含多个工作组
     */
    @ManyToMany(mappedBy = "apps")
    private Set<WorkGroup> workGroups = new HashSet<>();

    /**
     * 创建者
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WorkGroup> getWorkGroups() {
        return workGroups;
    }

    public void setWorkGroups(Set<WorkGroup> workGroups) {
        this.workGroups = workGroups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    public void setDefaulted(boolean defaulted) {
        this.defaulted = defaulted;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getPemPath() {
        return pemPath;
    }

    public void setPemPath(String pemPath) {
        this.pemPath = pemPath;
    }

    public String getPemPassword() {
        return pemPassword;
    }

    public void setPemPassword(String pemPassword) {
        this.pemPassword = pemPassword;
    }
}
