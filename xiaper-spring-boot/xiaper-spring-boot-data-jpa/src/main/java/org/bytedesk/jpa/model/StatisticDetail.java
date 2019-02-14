package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


/**
 TODO: 实时的写到redis，定时同步到mysql

 11. 最大同时接待
 12. 慢响应会话数（？？）
 13. 长接待会话数
 14. 总消息量：客服和访客发出的总消息数
 15. 访客消息数
 16. 客服消息数
 17. 客服字数
 18. 答问比： 客服发出消息与访客发出消息数量的比值
 19. 30s应答率：响应访客消息时间在30s内的占比
 20. 1分钟接入率
 21. 3分钟接入率
 22. 5分钟接入率
 23. 超时接入率：超过5分钟接入
 24. 平均会话时长：从访客进入到会话结束时间间隔的平均值
 25. 平均首次响应时长：从访客首次询问和客服回复时间间隔的平均值
 26. 平均响应时长：所有访客询问和客服回复时间间隔的平均值
 27. 一次性解决率：统计周期内，访客未二次来访的会话与总会话数的比值，此数据有一定延迟
 28. 排队量：所选时间内，发生排队的总次数
 29. 平均排队时长：开始排队到进入咨询的平均时长
 30. 最长排队时长：开始排队到进入咨询的最长时间
 31. 平均放弃时长：开始排队到放弃咨询的平均时长
 */

/**
 * 其他：
 * http://www.maijia.com/software/sw/1015151
 *
 * 1. 未回复人数
 * 2. 上班天数
 * 3. 平均上线时间
 * 4. 平均最后在线时间
 * 5. 最早上线
 * 6. 最晚在线
 * 7. 总登录次数
 *
 * 满意度（数量）:
 * 1. 收到评价
 * 2. 非常满意
 * 3. 满意
 * 4. 一般
 * 5. 不满意
 * 6. 非常不满意
 * 7. 评价返回率
 * 8. 客户满意比
 * 9. 客户服务满意度
 *
 * 支持自定义报表字段
 */


/**
 * 统计数据：客服账号，通过计算可得 工作组、公司
 * 时间维度：小时，通过计算可得 天
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "statistic_detail")
public class StatisticDetail extends AuditModel {

    private static final long serialVersionUID = -8929439007201807416L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 访客消息数
     */
    @Column(name = "visitor_message_count")
    private int visitorMessageCount;

    /**
     * 客服消息数
     */
    @Column(name = "agent_message_count")
    private int agentMessageCount;

    /**
     * 总消息量：客服和访客发出的总消息数
     */
    @Column(name = "total_message_count")
    private int totalMessageCount;

    /**
     * 客服字数
     */
    @Column(name = "agent_word_count")
    private int agentWordCount;

    /**
     * 答问比： 客服发出消息与访客发出消息数量的比值
     */
    @Column(name = "answer_question_rate")
    private double answerQuestionRate;

    /**
     * 30s响应率：响应访客消息时间在30s内的占比
     * 收到访客消息之后，30s内回复消息
     */
    @Column(name = "response_rate_lt30s")
    private double responseRateLt30s;

    @Column(name = "response_count_lt30s")
    private int responseCountLt30s;

    /**
     * 1分钟接入率
     */
    @Column(name = "accept_rate_lt1m")
    private double acceptRateLt1m;

    @Column(name = "accept_count_lt1m")
    private int acceptCountLt1m;

    /**
     * 3分钟接入率
     */
    @Column(name = "accept_rate_lt3m")
    private double acceptRateLt3m;

    @Column(name = "accept_count_lt3m")
    private int acceptCountLt3m;

    /**
     * 5分钟接入率
     */
    @Column(name = "accept_rate_lt5m")
    private double acceptRateLt5m;

    @Column(name = "accept_count_lt5m")
    private int acceptCountLt5m;

    /**
     * 超时接入率：超过5分钟接入
     */
    @Column(name = "accept_rate_gt5m")
    private double acceptRateGt5m;

    @Column(name = "accept_count_gt5m")
    private int acceptCountGt5m;

    /**
     * 平均会话时长：从访客进入到会话结束时间间隔的平均值
     */
    @Column(name = "average_time_length")
    private double averageTimeLength;

    /**
     * 平均首次响应时长：从访客首次询问和客服回复时间间隔的平均值
     */
    @Column(name = "average_init_response_length")
    private int averageInitResponseTimeLength;

    /**
     * 平均响应时长：所有访客询问和客服回复时间间隔的平均值
     */
    @Column(name = "average_response_time_length")
    private int averageResponseTimeLength;

    /**
     * TODO: 一次性解决率：统计周期内，访客未二次来访的会话与总会话数的比值，此数据有一定延迟
     */
    @Column(name = "solve_once_rate")
    private double solveOnceRate;

    /**
     * 当前接待会话数
     */
    @Column(name = "agent_thread_count")
    private int agentThreadCount;

    /**
     * 最大同时接待数
     */
    @Column(name = "max_current_count")
    private int maxCurrentCount;

    /**
     * 慢响应人数, 慢响应会话数
     */
    @Column(name = "slow_response_count")
    private int slowResponseCount;

    /**
     * 长接待人数，长会话数
     *
     * TODO: 暂定超过30分钟？
     * TODO: 支持管理员自定义
     */
    @Column(name = "long_thread_count")
    private int longThreadCount;

    /**
     * 按照日期统计
     *
     * FIXME: date为Oracle保留字，修改为on_date
     */
    @Column(name = "on_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date date;

    /**
     * 按照小时统计
     *
     * FIXME: hour为Oracle保留字，修改为on_hour
     */
    @Column(name = "on_hour")
    private int hour;

    /**
     * 按照 小时 or 天 统计
     */
    @Column(name = "time_type")
    private String timeType;

    /**
     * 按照 客服账号agent or 工作组workGroup 统计
     */
    @Column(name = "dimension_type")
    private String dimensionType;

    /**
     * 客服账号
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVisitorMessageCount() {
        return visitorMessageCount;
    }

    public void setVisitorMessageCount(int visitorMessageCount) {
        this.visitorMessageCount = visitorMessageCount;
    }

    public int getAgentMessageCount() {
        return agentMessageCount;
    }

    public void setAgentMessageCount(int agentMessageCount) {
        this.agentMessageCount = agentMessageCount;
    }

    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    public void setTotalMessageCount(int totalMessageCount) {
        this.totalMessageCount = totalMessageCount;
    }

    public int getAgentWordCount() {
        return agentWordCount;
    }

    public void setAgentWordCount(int agentWordCount) {
        this.agentWordCount = agentWordCount;
    }

    public double getAnswerQuestionRate() {
        return answerQuestionRate;
    }

    public void setAnswerQuestionRate(double answerQuestionRate) {
        this.answerQuestionRate = answerQuestionRate;
    }

    public int getAgentThreadCount() {
        return agentThreadCount;
    }

    public void setAgentThreadCount(int agentThreadCount) {
        this.agentThreadCount = agentThreadCount;
    }

    public double getResponseRateLt30s() {
        return responseRateLt30s;
    }

    public void setResponseRateLt30s(double responseRateLt30s) {
        this.responseRateLt30s = responseRateLt30s;
    }

    public double getAcceptRateLt1m() {
        return acceptRateLt1m;
    }

    public void setAcceptRateLt1m(double acceptRateLt1m) {
        this.acceptRateLt1m = acceptRateLt1m;
    }

    public double getAcceptRateLt3m() {
        return acceptRateLt3m;
    }

    public void setAcceptRateLt3m(double acceptRateLt3m) {
        this.acceptRateLt3m = acceptRateLt3m;
    }

    public double getAcceptRateLt5m() {
        return acceptRateLt5m;
    }

    public void setAcceptRateLt5m(double acceptRateLt5m) {
        this.acceptRateLt5m = acceptRateLt5m;
    }

    public double getAcceptRateGt5m() {
        return acceptRateGt5m;
    }

    public void setAcceptRateGt5m(double acceptRateGt5m) {
        this.acceptRateGt5m = acceptRateGt5m;
    }

    public double getAverageTimeLength() {
        return averageTimeLength;
    }

    public void setAverageTimeLength(double averageTimeLength) {
        this.averageTimeLength = averageTimeLength;
    }

    public int getAverageInitResponseTimeLength() {
        return averageInitResponseTimeLength;
    }

    public void setAverageInitResponseTimeLength(int averageInitResponseTimeLength) {
        this.averageInitResponseTimeLength = averageInitResponseTimeLength;
    }

    public int getAverageResponseTimeLength() {
        return averageResponseTimeLength;
    }

    public void setAverageResponseTimeLength(int averageResponseTimeLength) {
        this.averageResponseTimeLength = averageResponseTimeLength;
    }

    public double getSolveOnceRate() {
        return solveOnceRate;
    }

    public void setSolveOnceRate(double solveOnceRate) {
        this.solveOnceRate = solveOnceRate;
    }

    public int getMaxCurrentCount() {
        return maxCurrentCount;
    }

    public void setMaxCurrentCount(int maxCurrentCount) {
        this.maxCurrentCount = maxCurrentCount;
    }

    public int getSlowResponseCount() {
        return slowResponseCount;
    }

    public void setSlowResponseCount(int slowResponseCount) {
        this.slowResponseCount = slowResponseCount;
    }

    public int getLongThreadCount() {
        return longThreadCount;
    }

    public void setLongThreadCount(int longThreadCount) {
        this.longThreadCount = longThreadCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(String dimensionType) {
        this.dimensionType = dimensionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getResponseCountLt30s() {
        return responseCountLt30s;
    }

    public void setResponseCountLt30s(int responseCountLt30s) {
        this.responseCountLt30s = responseCountLt30s;
    }

    public int getAcceptCountLt1m() {
        return acceptCountLt1m;
    }

    public void setAcceptCountLt1m(int acceptCountLt1m) {
        this.acceptCountLt1m = acceptCountLt1m;
    }

    public int getAcceptCountLt3m() {
        return acceptCountLt3m;
    }

    public void setAcceptCountLt3m(int acceptCountLt3m) {
        this.acceptCountLt3m = acceptCountLt3m;
    }

    public int getAcceptCountLt5m() {
        return acceptCountLt5m;
    }

    public void setAcceptCountLt5m(int acceptCountLt5m) {
        this.acceptCountLt5m = acceptCountLt5m;
    }

    public int getAcceptCountGt5m() {
        return acceptCountGt5m;
    }

    public void setAcceptCountGt5m(int acceptCountGt5m) {
        this.acceptCountGt5m = acceptCountGt5m;
    }
}
