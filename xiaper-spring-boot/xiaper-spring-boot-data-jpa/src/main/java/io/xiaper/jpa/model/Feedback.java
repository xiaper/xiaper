package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


/**
 * 意见反馈：反馈记录
 * 模板在 Template 类
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "feedback")
public class Feedback extends AuditModel {

    private static final long serialVersionUID = -5744019214327774557L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "fid", unique = true, nullable = false)
    private String fid;

    /**
     * 网页反馈url
     */
    @Column(name = "url")
    private String url;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 截图
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 回复内容
     */
    @Column(name = "reply_content")
    private String replyContent;

    /**
     * 是否已经回复
     */
    @Column(name = "is_replied")
    private boolean replied = false;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Category category;

    /**
     * 访客
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User visitor;

    /**
     * 管理员
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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
