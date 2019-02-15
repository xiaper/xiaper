package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 机器人问答
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "answer")
public class Answer extends AuditModel {

    private static final long serialVersionUID = -5490961831704509253L;

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
     * 问题
     */
    @Column(name = "question")
    private String question;

    /**
     * 答案
     */
    @Column(name = "answer")
    private String answer;

    /**
     * 查询次数：
     * 作为查询热度决定问答是否被推荐到网页对话框右侧
     *
     * 默认显示10条，is_recommend = true的问题置顶，其余按照查询热度排序
     */
    @Column(name = "query_count")
    private int queryCount;

    /**
     * 是否是机器人问答 初始欢迎语
     *
     * FIXME: columnDefinition 不能用在Oracle，需要删除
     */
    @Column(name = "is_welcome")
    private boolean welcome = false;

    /**
     * 是否被推荐到网页对话框右侧
     * 用户可以自定义，也可以根据查询热度推荐到首页
     *
     * FIXME: columnDefinition 不能用在Oracle，需要删除
     */
    @Column(name = "is_recommend")
    private boolean recommend = false;

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
     * 图片
     */
    @Column(name = "url")
    private String url;

    /**
     * 微信资源id
     */
    @Column(name = "media_id")
    private String mediaId;

    /**
     * 微信图片资源url
     */
    @Column(name = "weixin_url")
    private String weiXinUrl;

    /**
     * 是否：相关问题
     */
    @Column(name = "is_related")
    private boolean related;

    /**
     * 微信使用：公众号
     */
    @Column(name = "code")
    private String code;

    /**
     * 智能问答，相关问题
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "answers", fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();

    /**
     * 所属分类
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "answer_category",
            joinColumns = @JoinColumn(name = "answer_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "category_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Category> categories = new HashSet<>();

    /**
     * 相关问题
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "answer_related",
            joinColumns = @JoinColumn(name = "answer_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "related_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Answer> relateds = new HashSet<>();

    /**
     * 所属工作组
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workgroup_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WorkGroup workGroup;

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

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public String getWeiXinUrl() {
        return weiXinUrl;
    }

    public void setWeiXinUrl(String weiXinUrl) {
        this.weiXinUrl = weiXinUrl;
    }

    public WorkGroup getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup) {
        this.workGroup = workGroup;
    }

    public Set<Answer> getRelateds() {
        return relateds;
    }

    public void setRelateds(Set<Answer> relateds) {
        this.relateds = relateds;
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

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public boolean isRelated() {
        return related;
    }

    public void setRelated(boolean related) {
        this.related = related;
    }

    public int getRateHelpful() {
        return rateHelpful;
    }

    public void setRateHelpful(int rateHelpful) {
        this.rateHelpful = rateHelpful;
    }

    public int getRateUseless() {
        return rateUseless;
    }

    public void setRateUseless(int rateUseless) {
        this.rateUseless = rateUseless;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public boolean isWelcome() {
        return welcome;
    }

    public void setWelcome(boolean welcome) {
        this.welcome = welcome;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public void updateQueryCount() {
        this.queryCount += 1;
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
