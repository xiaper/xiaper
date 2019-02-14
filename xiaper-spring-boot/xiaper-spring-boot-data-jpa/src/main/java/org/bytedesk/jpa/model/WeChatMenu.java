package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 公众号自定义菜单
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "wechat_menu")
public class WeChatMenu extends AuditModel {

    private static final long serialVersionUID = 2045947357759705593L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 父菜单id， sub_button 二级菜单数组，个数应为1~5个
     */
    @Column(name = "parent_id")
    private int parentId;

    /**
     * 是否已经发布
     */
    @Column(name = "status")
    private int status;

    /**
     * 菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 菜单标题，不超过16个字节，子菜单不超过60个字节
     */
    @Column(name = "name")
    private String name;

    /**
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    @Column(name = "my_key")
    private String key;

    /**
     * 其他，自定义字段
     */
    @Column(name = "content")
    private String content;

    /**
     * 网页链接，用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url
     */
    @Column(name = "url")
    private String url;

    /**
     * 调用新增永久素材接口返回的合法media_id
     */
    @Column(name = "media_id")
    private String mediaId;

    /**
     * 小程序的appid（仅认证公众号可配置）
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * 小程序的页面路径
     */
    @Column(name = "page_path")
    private String pagePath;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
