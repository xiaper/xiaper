package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 数据总览:

 TODO: 实时的写到redis，定时同步到mysql

 1. 正在咨询人数： 当前正在咨询的访客数量
 2. 当前在线客服数：当前处于可接待状态的客服数量
 3. 当前排队人数：当前处于排队状态的访客数量
 5. 会话量：本日累计的会话数量，包含了访客来访和客服主动发起会话两种
 6. 未接入会话量：放弃排队的数量
 7. 接入率：今日累计已已接入会话与总会话数的比值
 8. 满意率：满意数量与评价总量的比值
 9. 参评率： 今日参加评价的数量与接入会话数量的比值

 TODO: 民生银行增加2项：
 1. 坐席当前接待数量
 2. 当前渠道接待人数

 // 后备指标：
 转接量：转出会话的数量，转出的会话不计入接入会话数中
 客服邀请量：客服邀请其他客服，并成功加入会话的数量
 访客邀请量：客服主动邀请访客，并成功加入会话的数量
 接待人数：区别于会话量，1个访客可以对应多个会话

 */

/**
 * 统计数据：总览
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "statistic")
public class Statistic extends AuditModel {

    private static final long serialVersionUID = -2011651916602232088L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 正在咨询人数： 当前正在咨询的访客数量
     */
    @Column(name = "current_thread_count")
    private int currentThreadCount;

    /**
     * 会话量：本日累计的会话数量，包含了访客来访和客服主动发起会话两种
     */
    @Column(name = "total_thread_count")
    private int totalThreadCount;

    /**
     * 当前在线客服数：当前处于可接待状态的客服数量
     */
    @Column(name = "online_agent_count")
    private int onlineAgentCount;

    /**
     * 参评率： 今日参加评价的数量与接入会话数量的比值
     */
    @Column(name = "rate_rate")
    private double rateRate;

    @Column(name = "rate_count")
    private int rateCount;

    /**
     * 会话满意率：满意数量与评价总量的比值
     * >= 4 分
     *
     * 其中：非常满意 5、满意 4、一般 3、失望 2、极差 1
     */
    @Column(name = "satisfy_rate")
    private double satisfyRate;

    @Column(name = "satisfy_count")
    private int satisfyCount;

    /**
     * 正在排队量, 当前排队人数：所选时间内，发生排队的总次数
     */
    @Column(name = "queuing_count")
    private int queuingCount;

    /**
     * 所有排队量
     */
    @Column(name = "total_queue_count")
    private int totalQueueCount;

    /**
     * 排队接入率：今日累计已已接入会话与总会话数的比值
     */
    @Column(name = "accept_queue_rate")
    private double acceptQueueRate;

    @Column(name = "accept_queue_count")
    private double acceptQueueCount;

    /**
     * 未接入排队量：放弃排队的数量
     */
    @Column(name = "leave_queue_rate")
    private int leaveQueueRate;

    @Column(name = "leave_queue_count")
    private int leaveQueueCount;

    /**
     * 平均排队时长：开始排队到进入咨询的平均时长
     */
    @Column(name = "average_queue_time_length")
    private double averageQueueTimeLength;

    /**
     * 最长排队时长：开始排队到进入咨询的最长时间
     */
    @Column(name = "max_queue_time_length")
    private long maxQueueTimeLength;

    /**
     * 平均放弃时长：开始排队到放弃咨询的平均时长
     */
    @Column(name = "average_leave_queue_length")
    private double averageLeaveQueueTimeLength;

    /**
     * 1分钟内接入率
     */
    @Column(name = "accept_rate_lt1m")
    private double acceptRateLt1m;

    @Column(name = "accept_count_lt1m")
    private double acceptCountLt1m;

    /**
     * 3分钟内接入率
     */
    @Column(name = "accept_rate_lt3m")
    private double acceptRateLt3m;

    @Column(name = "accept_count_lt3m")
    private double acceptCountLt3m;

    /**
     * 5分钟内接入率
     */
    @Column(name = "accept_rate_lt5m")
    private double acceptRateLt5m;

    @Column(name = "accept_count_lt5m")
    private double acceptCountLt5m;

    /**
     * 超过5分钟接入率
     */
    @Column(name = "accept_rate_gt5m")
    private double acceptRateGt5m;

    @Column(name = "accept_count_gt5m")
    private double acceptCountGt5m;

    /**
     * 统计日期
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
     *
     * FIXME: type为Oracle保留字，修改为by_type
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 管理员
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrentThreadCount() {
        return currentThreadCount;
    }

    public void setCurrentThreadCount(int currentThreadCount) {
        this.currentThreadCount = currentThreadCount;
    }

    public int getTotalThreadCount() {
        return totalThreadCount;
    }

    public void setTotalThreadCount(int totalThreadCount) {
        this.totalThreadCount = totalThreadCount;
    }

    public int getOnlineAgentCount() {
        return onlineAgentCount;
    }

    public void setOnlineAgentCount(int onlineAgentCount) {
        this.onlineAgentCount = onlineAgentCount;
    }

    public int getLeaveQueueCount() {
        return leaveQueueCount;
    }

    public void setLeaveQueueCount(int leaveQueueCount) {
        this.leaveQueueCount = leaveQueueCount;
    }

    public double getAcceptQueueRate() {
        return acceptQueueRate;
    }

    public void setAcceptQueueRate(double acceptQueueRate) {
        this.acceptQueueRate = acceptQueueRate;
    }

    public double getSatisfyRate() {
        return satisfyRate;
    }

    public void setSatisfyRate(double satisfyRate) {
        this.satisfyRate = satisfyRate;
    }

    public double getRateRate() {
        return rateRate;
    }

    public void setRateRate(double rateRate) {
        this.rateRate = rateRate;
    }

    public int getQueuingCount() {
        return queuingCount;
    }

    public void setQueuingCount(int queuingCount) {
        this.queuingCount = queuingCount;
    }

    public double getAverageQueueTimeLength() {
        return averageQueueTimeLength;
    }

    public void setAverageQueueTimeLength(double averageQueueTimeLength) {
        this.averageQueueTimeLength = averageQueueTimeLength;
    }

    public long getMaxQueueTimeLength() {
        return maxQueueTimeLength;
    }

    public void setMaxQueueTimeLength(long maxQueueTimeLength) {
        this.maxQueueTimeLength = maxQueueTimeLength;
    }

    public double getAverageLeaveQueueTimeLength() {
        return averageLeaveQueueTimeLength;
    }

    public void setAverageLeaveQueueTimeLength(double averageLeaveQueueTimeLength) {
        this.averageLeaveQueueTimeLength = averageLeaveQueueTimeLength;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getTotalQueueCount() {
        return totalQueueCount;
    }

    public void setTotalQueueCount(int totalQueueCount) {
        this.totalQueueCount = totalQueueCount;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public int getSatisfyCount() {
        return satisfyCount;
    }

    public void setSatisfyCount(int satisfyCount) {
        this.satisfyCount = satisfyCount;
    }

    public double getAcceptCountLt1m() {
        return acceptCountLt1m;
    }

    public void setAcceptCountLt1m(double acceptCountLt1m) {
        this.acceptCountLt1m = acceptCountLt1m;
    }

    public double getAcceptCountLt3m() {
        return acceptCountLt3m;
    }

    public void setAcceptCountLt3m(double acceptCountLt3m) {
        this.acceptCountLt3m = acceptCountLt3m;
    }

    public double getAcceptCountLt5m() {
        return acceptCountLt5m;
    }

    public void setAcceptCountLt5m(double acceptCountLt5m) {
        this.acceptCountLt5m = acceptCountLt5m;
    }

    public double getAcceptCountGt5m() {
        return acceptCountGt5m;
    }

    public void setAcceptCountGt5m(double acceptCountGt5m) {
        this.acceptCountGt5m = acceptCountGt5m;
    }

    public double getAcceptQueueCount() {
        return acceptQueueCount;
    }

    public void setAcceptQueueCount(double acceptQueueCount) {
        this.acceptQueueCount = acceptQueueCount;
    }

    public int getLeaveQueueRate() {
        return leaveQueueRate;
    }

    public void setLeaveQueueRate(int leaveQueueRate) {
        this.leaveQueueRate = leaveQueueRate;
    }
}


