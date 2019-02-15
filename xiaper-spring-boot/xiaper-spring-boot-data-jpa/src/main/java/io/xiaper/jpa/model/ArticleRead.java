package io.xiaper.jpa.model;

import javax.persistence.*;

/**
 * 帮助中心support: 文章阅读记录
 *
 * 一篇文档一个用户可以有多条阅读记录
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "article_read")
public class ArticleRead extends AuditModel {

    private static final long serialVersionUID = 6572509028552725945L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 文章
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private Article article;

    /**
     * 阅读者
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


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
