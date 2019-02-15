package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 帮助中心support: 文章
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "article")
public class Article extends AuditModel {

    private static final long serialVersionUID = 8654550640233552864L;

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
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 摘要
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 阅读数量
     */
    @Column(name = "read_count")
    private int readCount;

    /**
     * 是否推荐到首页
     */
    @Column(name = "is_recommend")
    private boolean recommend = false;

    /**
     * 是否置顶
     */
    @Column(name = "is_top")
    private boolean top = false;

    /**
     * 是否转载，否为原创
     *
     * FIXME: columnDefinition 不能用在Oracle，需要删除
     */
    @Column(name = "is_reship")
    private boolean reship = false;

    /**
     * 转载网址
     */
    @Column(name = "reship_url")
    private String reshipUrl;

    /**
     * 是否发布
     * 默认为否0，前端看不到，只有发布之后，访客端才能够看到
     *
     * FIXME: columnDefinition 不能用在Oracle，需要删除
     */
    @Column(name = "is_published")
    private boolean published = false;

    /**
     * 是否markdown编辑器
     *
     * FIXME: columnDefinition 不能用在Oracle，需要删除
     */
    @Column(name = "is_markdown")
    private boolean markdown = false;

    /**
     * 有帮助
     */
    @Column(name = "rate_helpful")
    private int rateHelpful;

    /**
     * 无帮助
     */
    @Column(name = "rate_useless")
    private int rateUseless;

    /**
     * 文章内容
     */
    @Lob
    @Column(name = "content")
    private String content;

    /**
     * 文档对应的评价记录
     */
    @JsonIgnore
    @OneToMany(mappedBy = "article")
    private Set<ArticleRate> rates = new HashSet<>();

    /**
     * 文档对应的阅读记录
     */
    @JsonIgnore
    @OneToMany(mappedBy = "article")
    private Set<ArticleRead> reads = new HashSet<>();


    /**
     * 一片文章对应多个评论
     */
    @OneToMany(mappedBy = "article")
    private Set<Comment> comments = new HashSet<>();

    /**
     * 相关关键词
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "article_keyword",
            joinColumns = @JoinColumn(name = "article_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "tag_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Tag> keywords = new HashSet<>();

    /**
     * 所属分类
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "article_category",
            joinColumns = @JoinColumn(name = "article_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "category_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Category> categories = new HashSet<>();

    /**
     * 创建者
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRateHelpful() {
        return rateHelpful;
    }

    public void setRateHelpful(int rateHelpful) {
        this.rateHelpful = rateHelpful;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public int getRateUseless() {
        return rateUseless;
    }

    public void setRateUseless(int rateUseless) {
        this.rateUseless = rateUseless;
    }

    public Set<ArticleRate> getRates() {
        return rates;
    }

    public void setRates(Set<ArticleRate> rates) {
        this.rates = rates;
    }

    public Set<ArticleRead> getReads() {
        return reads;
    }

    public void setReads(Set<ArticleRead> reads) {
        this.reads = reads;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Set<Tag> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Tag> keywords) {
        this.keywords = keywords;
    }

    public boolean isReship() {
        return reship;
    }

    public void setReship(boolean reship) {
        this.reship = reship;
    }

    public String getReshipUrl() {
        return reshipUrl;
    }

    public void setReshipUrl(String reshipUrl) {
        this.reshipUrl = reshipUrl;
    }

    public boolean isMarkdown() {
        return markdown;
    }

    public void setMarkdown(boolean markdown) {
        this.markdown = markdown;
    }

    public void updateReadCount() {
        this.readCount +=1;
    }

    public void rate(boolean rate) {
        if (rate) {
            rateHelpful();
        } else {
            rateUseless();
        }
    }

    public void rateHelpful() {
        this.rateHelpful += 1;
    }

    public void rateUseless() {
        this.rateUseless += 1;
    }





}
