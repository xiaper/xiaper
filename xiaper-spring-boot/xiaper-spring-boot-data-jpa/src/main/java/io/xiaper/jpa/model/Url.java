package io.xiaper.jpa.model;

import javax.persistence.*;

/**
 * 网址
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "url")
public class Url extends AuditModel {

    private static final long serialVersionUID = -3619918999779625480L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一url
     */
    @Column(name = "url", unique = true, nullable = false)
    private String url;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "keywords")
    private String keywords;

    @Lob
    @Column(name = "description")
    private String description;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
