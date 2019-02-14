package org.bytedesk.jpa.model;

import org.bytedesk.jpa.constant.StatusConsts;

import javax.persistence.*;
import java.util.Date;

/**
 * 队列：
 * 访客首先直连客服，如果客服忙，则插入队
 *
 * 逻辑：
 * 访客进入->首先检查排在队列第一位的客服是否达到最大接待量->如果未达到饱和，则分配给此客服，
 * 否则检查排在第二位的客服是否达到饱和，如果未达到饱和，则分配此客服->否则检查下一位，以此类推
 *
 * 队列为公共队列，所有客服账号看到的均一样
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "queue")
public class Queue extends AuditModel {

    private static final long serialVersionUID = -5749949008949056993L;

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
     * 访客
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User visitor;

    /**
     * 客服
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User agent;

    /**
     * 客服接入端
     */
    @Column(name = "agent_client")
    private String agentClient;

    /**
     * 所属thread
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thread_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Thread thread;

    /**
     * 所属工作组
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workgroup_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WorkGroup workGroup;

    /**
     * 接入客服时间
     */
    @Column(name = "actioned_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionedAt;

    /**
     * 具体值参见 StatusConsts文件
     */
    @Column(name = "status")
    private String status;


    public boolean queuing() {
        return this.status.equals(StatusConsts.QUEUE_STATUS_QUEUING);
    }

    public void accept() {
        actionedAt = new Date();
        status = StatusConsts.QUEUE_STATUS_ACCEPTED;
    }

    public void leave() {
        actionedAt = new Date();
        status = StatusConsts.QUEUE_STATUS_LEAVED;
    }

    /**
     * 返回单位：秒
     *
     * @return
     */
    public Long waitLength() {
        return (actionedAt.getTime() - getCreatedAt().getTime())/1000;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public String getAgentClient() {
        return agentClient;
    }

    public void setAgentClient(String agentClient) {
        this.agentClient = agentClient;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public WorkGroup getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup) {
        this.workGroup = workGroup;
    }

    public Date getActionedAt() {
        return actionedAt;
    }

    public void setActionedAt(Date actionedAt) {
        this.actionedAt = actionedAt;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "id=" + id +
                ", qid='" + qid + '\'' +
                ", visitor=" + visitor +
                ", agent=" + agent +
                ", agentClient='" + agentClient + '\'' +
                ", workGroup=" + workGroup +
                '}';
    }
}
