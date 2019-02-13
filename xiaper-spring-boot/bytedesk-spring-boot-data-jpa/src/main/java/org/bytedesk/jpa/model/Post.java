package org.bytedesk.jpa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bytedesk.com on 2019/1/23
 */
@Entity
@Table(name = "post")
public class Post extends AuditModel {

    private static final long serialVersionUID = -1208528873114845315L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "pid", unique = true, nullable = false)
    private String pid;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 文章内容
     */
    @Lob
    @Column(name = "content")
    private String content;

    /**
     * 阅读数量
     */
    @Column(name = "read_count")
    private int readCount;

    /**
     * 是否置顶
     */
    @Column(name = "is_top")
    private boolean top = false;

    /**
     * 一片文章对应多个评论
     */
    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();

    /**
     * 相关关键词
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "post_keyword",
            joinColumns = @JoinColumn(name = "post_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "tag_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Tag> keywords = new HashSet<>();

    /**
     * 所属分类
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "post_category",
            joinColumns = @JoinColumn(name = "post_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "category_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Category> categories = new HashSet<>();

    /**
     * 创建者
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Tag> keywords) {
        this.keywords = keywords;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
