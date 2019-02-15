package io.xiaper.schedule.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务：费用相关
 *
 * @author bytedesk.com
 */
@Component
public class PaymentTask {



//    @Autowired
//    CuratorFramework zkClient;

    /**
     * 每天凌晨更新试用期，并验证试用期是否结束，
     * 如果 结束，则更新用户角色
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void validateTry() {


    }

    /**
     * 每天检查付费是否到期，并提前一周，邮件或者短信提醒
     * 每天9:00检查注册用户的试用期，并发送邮件和短信提醒, 超期没有续费，自动降级为个人用户账号
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void notifyPayment() {


    }

    /**
     * 对于预付费用户，每天定时扣费
     * 检查当天是否扣费，防止重复扣费
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void doPayment() {


    }



}
