package org.bytedesk.jpa.model;

import javax.persistence.*;

/**
 * 帮助中心support: 文章评价记录
 *
 * 注意：
 * 一个用户对一篇文章只能有一个评价
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "article_rate")
public class ArticleRate extends AuditModel {

    private static final long serialVersionUID = -7651956186619235430L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * true: 有用，false：无用
     */
    @Column(name = "helpful")
    private boolean helpful;

    /**
     * 文章
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private Article article;

    /**
     * 评价者
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

    public boolean isHelpful() {
        return helpful;
    }

    public void setHelpful(boolean helpful) {
        this.helpful = helpful;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

