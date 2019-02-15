package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 帮助中心support: 文章分类
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "category")
public class Category extends AuditModel {

    private static final long serialVersionUID = 581974842773067442L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "cid", unique = true, nullable = false)
    private String cid;

    /**
     * 类别名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 类型：智能客服robot、帮助文档support、常用语分类cuw、意见反馈feedback
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 父类别
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category parent;

    /**
     * 智能问答分类，相关问题
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<Answer> answers = new HashSet<>();

    /**
     * 所包含文章
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<Article> articles = new HashSet<>();

    /**
     * 创建者
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    /**
     * 非数据库字段属性
     */
    @Transient
    private Set<Category> children = new HashSet<>();

    @Transient
    private Set<Cuw> cuwChildren = new HashSet<>();


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

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<Cuw> getCuwChildren() {
        return cuwChildren;
    }

    public void setCuwChildren(Set<Cuw> cuwChildren) {
        this.cuwChildren = cuwChildren;
    }
}
