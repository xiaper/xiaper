package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 调查问卷/咨询前问卷
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "questionnaire")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Questionnaire extends AuditModel {

    private static final long serialVersionUID = 2422315195190552703L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "qid", unique = true, nullable = false)
    private String qid;

    /**
     * 问卷名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 所包含题目
     */
    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.EAGER)
    private Set<QuestionnaireItem> questionnaireItems = new HashSet<>(16);

    /**
     * 创建者
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<QuestionnaireItem> getQuestionnaireItems() {
        return questionnaireItems;
    }

    public void setQuestionnaireItems(Set<QuestionnaireItem> questionnaireItems) {
        this.questionnaireItems = questionnaireItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}



