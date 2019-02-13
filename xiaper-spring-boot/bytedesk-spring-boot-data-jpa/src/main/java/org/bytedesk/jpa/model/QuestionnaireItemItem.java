package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 调查问卷/咨询前问卷: 题目选项
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "questionnaire_item_item")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class QuestionnaireItemItem extends AuditModel {

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
     * 选项内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 对应题目
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_item_id", nullable = false)
    private QuestionnaireItem questionnaireItem;

    /**
     * 多选答案
     */
    @ManyToMany(mappedBy = "questionnaireAnswerItems", fetch = FetchType.EAGER)
    private Set<QuestionnaireAnswer> questionnaireAnswers = new HashSet<>();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionnaireItem getQuestionnaireItem() {
        return questionnaireItem;
    }

    public void setQuestionnaireItem(QuestionnaireItem questionnaireItem) {
        this.questionnaireItem = questionnaireItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<QuestionnaireAnswer> getQuestionnaireAnswers() {
        return questionnaireAnswers;
    }

    public void setQuestionnaireAnswers(Set<QuestionnaireAnswer> questionnaireAnswers) {
        this.questionnaireAnswers = questionnaireAnswers;
    }
}
