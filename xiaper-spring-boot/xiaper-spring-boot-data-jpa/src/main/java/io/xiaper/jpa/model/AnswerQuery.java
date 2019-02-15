package io.xiaper.jpa.model;

import javax.persistence.*;

/**
 * 自动提供答案: 用户查询记录
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "answer_query")
public class AnswerQuery extends AuditModel {

    private static final long serialVersionUID = 8121093984754195014L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 文章
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Answer answer;

    /**
     * 查询者
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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
