package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 调查问卷/咨询前问卷: 用户选择、填写内容
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "questionnaire_answer")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class QuestionnaireAnswer extends AuditModel {

    private static final long serialVersionUID = -6432188286257064575L;

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
     * 对应题目
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_item_id", nullable = false)
    private QuestionnaireItem questionnaireItem;

    /**
     * 单选题答案
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_item_item_id", nullable = false)
    private QuestionnaireItemItem questionnaireItemItem;

    /**
     * 多选题答案
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "questionnaire_answer_item", joinColumns = @JoinColumn(name = "questionnaire_answer_id"), inverseJoinColumns = @JoinColumn(name = "questionnaire_item_item_id"))
    private Set<QuestionnaireItemItem> questionnaireAnswerItems = new HashSet<>();

    /**
     * 单行输入内容
     */
    @Column(name = "input_content")
    private String inputContent;

    /**
     * 多行输入内容
     */
    @Column(name = "textarea_content")
    private String textAreaContent;

    /**
     * 回答用户
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

    public QuestionnaireItem getQuestionnaireItem() {
        return questionnaireItem;
    }

    public void setQuestionnaireItem(QuestionnaireItem questionnaireItem) {
        this.questionnaireItem = questionnaireItem;
    }

    public QuestionnaireItemItem getQuestionnaireItemItem() {
        return questionnaireItemItem;
    }

    public void setQuestionnaireItemItem(QuestionnaireItemItem questionnaireItemItem) {
        this.questionnaireItemItem = questionnaireItemItem;
    }

    public Set<QuestionnaireItemItem> getQuestionnaireAnswerItems() {
        return questionnaireAnswerItems;
    }

    public void setQuestionnaireAnswerItems(Set<QuestionnaireItemItem> questionnaireAnswerItems) {
        this.questionnaireAnswerItems = questionnaireAnswerItems;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    public String getTextAreaContent() {
        return textAreaContent;
    }

    public void setTextAreaContent(String textAreaContent) {
        this.textAreaContent = textAreaContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
