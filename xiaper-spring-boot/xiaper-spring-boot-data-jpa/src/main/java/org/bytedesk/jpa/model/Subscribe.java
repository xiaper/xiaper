package org.bytedesk.jpa.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * 订阅记录
 *
 * @author bytedesk.com
 */
@Entity
@SQLDelete(sql = "UPDATE subscribe SET leaved = true WHERE id = ?")
@Where(clause = "leaved = false")
@Table(name = "subscribe")
public class Subscribe extends AuditModel {

    private static final long serialVersionUID = -3524902548710727831L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 会话
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thread_id")
    private Thread thread;

    /**
     * 访客 or 客服
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    /**
     * 是否已经取消订阅主体，离开thread
     *
     * 用于软删除soft delete, 也即：
     * 在数据库中设置此字段为1标记删除，而非直接从数据库中删除
     */
    @Column(name = "leaved")
    private boolean leaved = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLeaved() {
        return leaved;
    }

    public void setLeaved(boolean leaved) {
        this.leaved = leaved;
    }
}
