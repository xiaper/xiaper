package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 调查问卷/咨询前问卷: 题目
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "questionnaire_item")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class QuestionnaireItem extends AuditModel {

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
     * 题目标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 问题类型: 单选、多选，单行输入input、多行输入textarea
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 问题选项
     */
    @OneToMany(mappedBy = "questionnaireItem", fetch = FetchType.EAGER)
    private Set<QuestionnaireItemItem> questionnaireItemItems = new HashSet<>(16);

    /**
     * 对应问卷
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Questionnaire questionnaire;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<QuestionnaireItemItem> getQuestionnaireItemItems() {
        return questionnaireItemItems;
    }

    public void setQuestionnaireItemItems(Set<QuestionnaireItemItem> questionnaireItemItems) {
        this.questionnaireItemItems = questionnaireItemItems;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
