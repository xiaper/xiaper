package io.xiaper.schedule.task;


import io.xiaper.jpa.constant.*;
import io.xiaper.jpa.model.*;
import io.xiaper.jpa.repository.*;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import io.xiaper.jpa.util.JpaUtil;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.redis.RedisService;
import org.bytedesk.mq.service.redis.RedisStatisticService;
import org.bytedesk.mq.service.redis.RedisThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 统计数据定时任务
 *
 * FIXME: 对于分布式集群部署的情况，需要保证同时只有一个实例执行定时任务
 * ZooKeeper 分布式锁
 *
 * @author bytedesk.com
 */
@Component
public class StatisticTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    StatisticDetailRepository statisticDetailRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    QueueRepository queueRepository;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RedisService redisService;

    @Autowired
    RedisThreadService redisThreadService;

    @Autowired
    RedisStatisticService redisStatisticService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CuratorFramework zooKeeperClient;

    @Autowired
    UserService userService;

    /**
     * 加锁路径
     */
    private String lockPath = BdConstants.IS_DEPLOY ? "/lock/statistic/dxz" : "/lock/statistic/";

    /**
     * 每2分钟检查5分钟之内没有新消息产生的会话, 发送提示消息并关闭
     *
     * TODO: 为方便测试效果，暂时修改为10秒关闭
     */
    @Scheduled(fixedRate = 1000 * 10)
    public void closeIdleThread() {
//        logger.info("closeIdleThread: " + new Date());

        // TODO: 在运行有多个实例的分布式情况下，需要首先检查事务已经处理过，不要重复处理，即使加分布式锁的情况下同样需要考虑

        String path = lockPath + "thread/close";
        InterProcessMutex lock = new InterProcessMutex(zooKeeperClient, path);

        try {
            // 等待1分钟
            if (lock.acquire(1, TimeUnit.MINUTES)) {

                try {

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE, -5);
//                    logger.info("closeIdleThread: " + new Date() + " time: "+ calendar.getTime());

                    // 会话时间戳为5分钟之前，且未关闭的会话
                    List<Thread> threadList = threadRepository.findByTimestampBeforeAndTypeAndClosedOrderByIdAsc(calendar.getTime(), TypeConsts.THREAD_TYPE_WORK_GROUP, false);
                    for (int i = 0; i < threadList.size(); i++) {
                        Thread thread = threadList.get(i);

                        logger.info("close thread {}, ", thread.getTid(), thread.getTimestamp());

                        // 更新可接待 数量 和当前接待 数量
                        Set<User> userSet = thread.getAgents();
                        Iterator iterator = userSet.iterator();
                        while (iterator.hasNext()) {
                            User user = (User) iterator.next();
                            //
                            redisService.increaseAgentIdleCount(user.getUid());
                            //
                            if (thread.getWorkGroup() != null) {
                                redisThreadService.decreaseWorkGroupAgentThreadCount(thread.getWorkGroup().getWid(), user.getUid());
                            } else {
                                redisThreadService.decreaseAgentThreadCount(user.getUid());
                            }
                        }

                        // 发送消息通知关闭会话
                        Message message = new Message();
                        message.setMid(JpaUtil.randomId());
                        if (thread.getWorkGroup() != null) {
                            message.setWid(thread.getWorkGroup().getWid());
                        } else {
                            message.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                        }
                        message.setThread(thread);
                        message.setType(TypeConsts.MESSAGE_TYPE_NOTIFICATION_AUTO_CLOSE);
                        message.setContent(BdConstants.DEFAULT_WORK_GROUP_AUTO_CLOSE_TIP);
                        message.setUser(userService.getNotificationUser());
                        messageRepository.save(message);

                        // 关闭会话thread
                        thread.setTimestamp(message.getCreatedAt());
                        thread.setContent(BdConstants.DEFAULT_WORK_GROUP_AUTO_CLOSE_TIP);
                        thread.setCloseType(TypeConsts.THREAD_CLOSE_TYPE_TIMEOUT);
                        thread.setAutoClose(true);
                        thread.close();
                        threadRepository.save(thread);

                        // 发送通知
                        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_THREAD_MESSAGE, message);

                        // 同步缓存：减少当前会话数
                        if (thread.getWorkGroup() != null) {
                            redisStatisticService.removeCurrentThread(thread.getWorkGroup().getUser().getUid(), thread.getTid());
                        }
                    }

                }
                finally {
                    lock.release();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Cron使用方法：
     * https://stackoverflow.com/questions/26147044/spring-cron-expression-for-every-day-101am
     * https://docs.spring.io/spring/docs/3.0.x/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
     */

    // "0 0 * * * *" = the top of every hour of every day.
    // "*/10 * * * * *" = every ten seconds.
    // "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
    // "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
    // "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
    // "0 0 0 25 12 ?" = every Christmas Day at midnight
    // (*) means match any
    // */X means "every X"
    // ? ("no specific value")

    /**
     * 每小时整点运行一次
     * 将redis中的统计数据 持久化到MySQL中
     *
     * TODO: 需要考虑多个实例同时运行，加分布式同步锁 ZooKeeper，防止数据重复
     */
    @Scheduled(cron = "0 0 * * * *")
    private void persistRedisHourly() {
        logger.info("persistRedisHourly");

        // 一. 数据总览

//        Set<User> agents = new HashSet<>();

        Optional<Role> adminRoleOptional = roleRepository.findByValue(RoleConsts.ROLE_ADMIN);
        List<User> adminUserList = userRepository.findByRolesContains(adminRoleOptional.get());
        for (int i = 0; i < adminUserList.size(); i++) {

            User admin = adminUserList.get(i);
            String adminUid = admin.getUid();

            // 1. 正在咨询人数： 当前正在咨询的访客数量
            Long currentThreadCount = redisStatisticService.currentThreadCount(adminUid);

            // 2. 会话量：本日累计的会话数量，包含了访客来访和客服主动发起会话两种
            Long totalThreadCount = redisStatisticService.totalThreadCount(adminUid);

            // 3. 当前在线客服数：当前处于可接待状态的客服数量
            Long onlineAgentCount = redisStatisticService.onlineAgentCount(adminUid);

            // 4. 参评数
            Long rateCount = redisStatisticService.rateCount(adminUid);

            // 5. 会话满意数
            Long satisfyCount = redisStatisticService.satisfyCount(adminUid);

            // 6. 正在排队量, 当前排队人数
            Long currentQueueCount = redisStatisticService.currentQueueCount(adminUid);

            // 7. 所有排队量
            Long totalQueueCount = redisStatisticService.totalQueueCount(adminUid);

            // 8. 排队接入量
            Long acceptQueueCount = redisStatisticService.acceptQueueCount(adminUid);

            // 9. 未接入排队量：放弃排队的数量
            Long leaveQueueCount = redisStatisticService.leaveQueueCount(adminUid);

            // 10. 1分钟内接入量
            Long acceptCountLt1m = redisStatisticService.acceptCountLt1m(adminUid);

            // 11. 3分钟内接入量
            Long acceptCountLt3m = redisStatisticService.acceptCountLt3m(adminUid);

            // 12. 5分钟内接入量
            Long acceptCountLt5m = redisStatisticService.acceptCountLt5m(adminUid);

            // 13. 超过5分钟接入量
            Long acceptCountGt5m = redisStatisticService.acceptCountGt5m(adminUid);

            // 查重并持久化
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(new Date());
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String type = TypeConsts.STATISTIC_TIME_TYPE_HOUR;
            Optional<Statistic> statisticOptional = statisticRepository.findByDateAndHourAndTypeAndUser(date, hour, type, admin);
            if (!statisticOptional.isPresent()) {

                Statistic statistic = new Statistic();

                statistic.setCurrentThreadCount(currentThreadCount.intValue());
                statistic.setTotalQueueCount(totalThreadCount.intValue());
                statistic.setOnlineAgentCount(onlineAgentCount.intValue());
                statistic.setRateCount(rateCount.intValue());
                statistic.setSatisfyCount(satisfyCount.intValue());
                statistic.setQueuingCount(currentQueueCount.intValue());
                statistic.setTotalQueueCount(totalQueueCount.intValue());
                statistic.setAcceptQueueCount(acceptQueueCount.intValue());
                statistic.setLeaveQueueCount(leaveQueueCount.intValue());
                statistic.setAcceptCountLt1m(acceptCountLt1m.intValue());
                statistic.setAcceptCountLt3m(acceptCountLt3m.intValue());
                statistic.setAcceptCountLt5m(acceptCountLt5m.intValue());
                statistic.setAcceptCountGt5m(acceptCountGt5m.intValue());

                statistic.setDate(date);
                statistic.setHour(hour);
                statistic.setType(type);
                statistic.setUser(admin);
                statisticRepository.save(statistic);
            }

//            Set<WorkGroup> workGroups = admin.getWorkGroups();
//            Iterator iterator = workGroups.iterator();
//            while (iterator.hasNext()) {
//                WorkGroup workGroup = (WorkGroup) iterator.next();
//                Set<User> users = workGroup.getUsers();
//                agents.addAll(users);
//            }
        }


        // 二. 客服详情
        // FIXME: 漏掉了管理员
        // FIXME: 一个客服账号一天内有多条数据
        Optional<Role> agentRoleOptional = roleRepository.findByValue(RoleConsts.ROLE_WORKGROUP_AGENT);
        List<User> agentUserList = userRepository.findByRolesContains(agentRoleOptional.get());
        for (int i = 0; i < agentUserList.size(); i++) {

            User agent = agentUserList.get(i);
            String agentUid = agent.getUid();
            logger.info("persistRedisHourly agent uid: {}", agentUid);

            //  1. 访客消息数量
            Long visitorMessageCount = redisStatisticService.visitorMessageCount(agentUid);

            // 2. 客服消息数
            Long agentMessageCount = redisStatisticService.agentMessageCount(agentUid);

            // 3. 总消息量
            Long totalMessageCount = redisStatisticService.totalMessageCount(agentUid);

            // 4. 客服字数
            Long agentWordCount = redisStatisticService.agentWordCount(agentUid);

            // 5. 客服回复消息响应时间 <= 30s
            Long responseCountLt30s = redisStatisticService.responseCountLt30s(agentUid);

            // 6.
            Long acceptCountLt1m = redisStatisticService.agentAcceptCountLt1m(agentUid);

            // 7.
            Long acceptCountLt3m = redisStatisticService.agentAcceptCountLt3m(agentUid);

            // 8.
            Long acceptCountLt5m = redisStatisticService.agentAcceptCountLt5m(agentUid);

            // 9.
            Long acceptCountGt5m = redisStatisticService.agentAcceptCountGt5m(agentUid);

            // 3. 查重并持久化
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(new Date());
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String timeTypeHour = TypeConsts.STATISTIC_TIME_TYPE_HOUR;
            String dimensionTypeAgent = TypeConsts.STATISTIC_DIMENSION_TYPE_AGENT;
            Optional<StatisticDetail> statisticDetailOptional = statisticDetailRepository.findByDateAndHourAndTimeTypeAndDimensionTypeAndUser(date,
                    hour, timeTypeHour, dimensionTypeAgent, agent);
            if (!statisticDetailOptional.isPresent()) {

                StatisticDetail statisticDetail = new StatisticDetail();

                statisticDetail.setVisitorMessageCount(visitorMessageCount.intValue());
                statisticDetail.setAgentMessageCount(agentMessageCount.intValue());
                statisticDetail.setTotalMessageCount(totalMessageCount.intValue());
                statisticDetail.setAgentWordCount(agentWordCount.intValue());
                statisticDetail.setResponseCountLt30s(responseCountLt30s.intValue());
                statisticDetail.setAcceptCountLt1m(acceptCountLt1m.intValue());
                statisticDetail.setAcceptCountLt3m(acceptCountLt3m.intValue());
                statisticDetail.setAcceptCountLt5m(acceptCountLt5m.intValue());
                statisticDetail.setAcceptCountGt5m(acceptCountGt5m.intValue());

                statisticDetail.setDate(date);
                statisticDetail.setHour(hour);
                statisticDetail.setTimeType(timeTypeHour);
                statisticDetail.setDimensionType(dimensionTypeAgent);
                statisticDetail.setUser(agent);

                statisticDetailRepository.save(statisticDetail);
            }
        }

    }


    /**
     * 每天0点，清空缓存数据
     *
     * TODO: 需要考虑多个实例同时运行，加分布式同步锁 ZooKeeper，防止重复清理
     */
     @Scheduled(cron = "0 0 0 * * *")
    private void clearRedisDaily() {
        logger.info("clearRedisDaily");

        // 一. 数据总览

        //Set<User> agents = new HashSet<>();

        Optional<Role> adminRoleOptional = roleRepository.findByValue(RoleConsts.ROLE_ADMIN);
        List<User> adminUserList = userRepository.findByRolesContains(adminRoleOptional.get());
        for (int i = 0; i < adminUserList.size(); i++) {

            User admin = adminUserList.get(i);
            String adminUid = admin.getUid();

            //
            redisStatisticService.clearAdminData(adminUid);

//            Set<WorkGroup> workGroups = admin.getWorkGroups();
//            Iterator iterator = workGroups.iterator();
//            while (iterator.hasNext()) {
//                WorkGroup workGroup = (WorkGroup) iterator.next();
//                Set<User> users = workGroup.getUsers();
//                agents.addAll(users);
//            }
        }

        // 二. 客服详情
        Optional<Role> agentRoleOptional = roleRepository.findByValue(RoleConsts.ROLE_WORKGROUP_AGENT);
        List<User> agentUserList = userRepository.findByRolesContains(agentRoleOptional.get());
        for (int i = 0; i < agentUserList.size(); i++) {

            User agent = agentUserList.get(i);
            String agentUid = agent.getUid();

            //
            redisStatisticService.clearAgentDate(agentUid);
        }

    }


    /**
     * 废弃
     * 数据总览：
     *
     * TODO: 二期修改为Redis存储，前端实时显示
     */
    // @Scheduled(fixedRate = 1000 * 60 * 5)
    public void statistic() {

        // 数据总览
        // TODO: 统计数据 + rabbitmq通知前端

        //
        Map<String, Integer> currentThreadCountMap = new HashMap<>(16);
        // 正在进行中的会话
        List<Thread> threadListNotClosed = threadRepository.findByClosed(false);
        for (int i = 0; i < threadListNotClosed.size(); i++) {
            Thread thread = threadListNotClosed.get(i);
            //
            if (thread.getAgent() != null) {
                String adminUsername = thread.getAgent().getAdmin().getUsername();
                logger.info("thread tid not closed {}, adminUsername {}",  thread.getTid(), adminUsername);

                // 正在咨询人数（当前会话数）
                Integer currentThreadCount = currentThreadCountMap.get(adminUsername);
                if (currentThreadCount == null) {
                    currentThreadCountMap.put(adminUsername, 1);
                } else {
                    currentThreadCountMap.put(adminUsername, currentThreadCount + 1);
                }
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startAt = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endAt = calendar.getTime();

        //
        Map<String, Integer> totalThreadCountMap = new HashMap<>(16);
        // 当天所有会话
        List<Thread> threadListTotal = threadRepository.findByCreatedAtBetween(startAt, endAt);
        for (int i = 0; i < threadListTotal.size(); i++) {
            Thread thread = threadListTotal.get(i);

            if (thread.getAgent() != null) {
                String adminUsername = thread.getAgent().getAdmin().getUsername();
                logger.info("thread tid total {}, adminUsername {}",  thread.getTid(), adminUsername);

                // 会话量
                Integer threadCount = totalThreadCountMap.get(adminUsername);
                if (threadCount == null) {
                    totalThreadCountMap.put(adminUsername, 1);
                } else {
                    totalThreadCountMap.put(adminUsername,  threadCount + 1);
                }
            }
        }

        //
        Map<String, Integer> rateTotalCountMap = new HashMap<>(16);
        Map<String, Integer> rateSatisfyCountMap = new HashMap<>(16);
        // 会话评价
        List<Rate> rateList = rateRepository.findByCreatedAtBetween(startAt, endAt);
        for (int i = 0; i < rateList.size(); i++) {
            Rate rate = rateList.get(i);
            String adminUsername = rate.getThread().getAgent().getAdmin().getUsername();
            logger.info("rate thread tid {}, adminUsername {}", rate.getThread().getTid(), adminUsername);

            // 满意的数量
            if (rate.getScore() >= 4) {
                Integer rateSatisfyCount = rateSatisfyCountMap.get(adminUsername);
                if (rateSatisfyCount == null) {
                    rateSatisfyCountMap.put(adminUsername, 1);
                } else {
                    rateSatisfyCountMap.put(adminUsername, rateSatisfyCount + 1);
                }
            }

            // 评价的总数
            Integer rateTotalCount = rateTotalCountMap.get(adminUsername);
            if (rateTotalCount == null) {
                rateTotalCountMap.put(adminUsername, 1);
            } else {
                rateTotalCountMap.put(adminUsername, rateTotalCount + 1);
            }
        }

        // adminUsername : rateRate
        Map<String, Double> rateThreadRateMap = new HashMap<>(16);
        // 参评率
        for (String adminUsername : totalThreadCountMap.keySet()) {
            Integer threadTotalCount = totalThreadCountMap.get(adminUsername);
            Integer rateTotalCount = rateTotalCountMap.get(adminUsername);
            if (rateTotalCount != null && threadTotalCount != null && threadTotalCount > 0) {
                logger.info("rateTotalCount {}, threadTotalCount {}", rateTotalCount, threadTotalCount);
                Double rateThreadRate = rateTotalCount * 1.0 / threadTotalCount;
                rateThreadRateMap.put(adminUsername, rateThreadRate);
            }
        }

        // adminUsername : satisfyRate
        Map<String, Double> rateSatisfyRateMap = new HashMap<>(16);
        // 会话满意率
        for (String adminUsername : rateTotalCountMap.keySet()) {
            Integer satisfyCount = rateSatisfyCountMap.get(adminUsername);
            Integer totalCount = rateTotalCountMap.get(adminUsername);
            if (totalCount > 0) {
                Double satisfyRate = satisfyCount * 1.0 / totalCount;
                rateSatisfyRateMap.put(adminUsername, satisfyRate);
            }
        }

        // 队列总量
        Map<String, Integer> queueTotalCountMap = new HashMap<>(16);
        // 排队中总量
        Map<String, Integer> queuingCountMap = new HashMap<>(16);
        // 排队接入量
        Map<String, Integer> queueAcceptCountMap = new HashMap<>(16);
        // 排队接入率
        Map<String, Double> queueAcceptRateMap = new HashMap<>(16);
        // 放弃排队量
        Map<String, Integer> queueLeaveCountMap = new HashMap<>(16);
        // 总排队接入时长
        Map<String, Long> queueAcceptSecondMap = new HashMap<>(16);
        // 平均排队时长
        Map<String, Double> queueAcceptAverageSecondMap = new HashMap<>(16);
        // 最长排队时长
        Map<String, Long> queueAcceptSecondMaxMap = new HashMap<>(16);
        // 1分钟接入量
        Map<String, Integer> queueAcceptSecondLt1mCountMap = new HashMap<>(16);
        Map<String, Double> queueAcceptSecondLt1mRateMap = new HashMap<>(16);
        // 3分钟接入量
        Map<String, Integer> queueAcceptSecondLt3mCountMap = new HashMap<>(16);
        Map<String, Double> queueAcceptSecondLt3mRateMap = new HashMap<>(16);
        // 5分钟接入量
        Map<String, Integer> queueAcceptSecondLt5mCountMap = new HashMap<>(16);
        Map<String, Double> queueAcceptSecondLt5mRateMap = new HashMap<>(16);
        // 超时接入量：超过5分钟接入
        Map<String, Integer> queueAcceptSecondGt5mCountMap = new HashMap<>(16);
        Map<String, Double> queueAcceptSecondGt5mRateMap = new HashMap<>(16);
        // 总排队放弃时长
        Map<String, Long> queueLeaveSecondMap = new HashMap<>(16);
        // 平均排队放弃时长
        Map<String, Double> queueLeaveAverageSecondMap = new HashMap<>(16);

        // 当天所有排队
        List<Queue> queueList = queueRepository.findByCreatedAtBetween(startAt, endAt);
        for (int i = 0; i < queueList.size(); i++) {
            Queue queue = queueList.get(i);
            if (queue.getAgent() == null) {
                continue;
            }
            String adminUsername = queue.getAgent().getAdmin().getUsername();
            logger.info("queue qid {}, adminUsername {}", queue.getQid(), adminUsername);

            // 排队量
            Integer queueTotalCount = queueTotalCountMap.get(adminUsername);
            if (queueTotalCount == null) {
                queueTotalCountMap.put(adminUsername, 1);
            } else {
                queueTotalCountMap.put(adminUsername, queueTotalCount + 1);
            }

            if (queue.getStatus().equals(StatusConsts.QUEUE_STATUS_QUEUING)) {
                // 正在排队中
                Integer queuingCount = queuingCountMap.get(adminUsername);
                if (queuingCount == null) {
                    queuingCountMap.put(adminUsername, 1);
                } else {
                    queuingCountMap.put(adminUsername, queuingCount + 1);
                }

            } else if (queue.getStatus().equals(StatusConsts.QUEUE_STATUS_ACCEPTED)) {
                // 排队接入量
                Integer queueAcceptCount = queueAcceptCountMap.get(adminUsername);
                if (queueAcceptCount == null) {
                    queueAcceptCountMap.put(adminUsername, 1);
                } else {
                    queueAcceptCountMap.put(adminUsername, queueAcceptCount + 1);
                }

                // 总排队时长，计算可得到平均排队时长
                Long queueAcceptSecond = queueAcceptSecondMap.get(adminUsername);
                long newAcceptSecond = (queue.getActionedAt().getTime() - queue.getCreatedAt().getTime())/1000;
                if (queueAcceptSecond == null) {
                    queueAcceptSecondMap.put(adminUsername, Long.valueOf(newAcceptSecond));
                } else {
                    queueAcceptSecondMap.put(adminUsername, queueAcceptSecond + newAcceptSecond);
                }

                // 等待时长
                if (newAcceptSecond <= 60) {
                    // 1分钟接入量
                    Integer acceptSecondLt1mCount = queueAcceptSecondLt1mCountMap.get(adminUsername);
                    if (acceptSecondLt1mCount == null) {
                        queueAcceptSecondLt1mCountMap.put(adminUsername, 1);
                    } else {
                        queueAcceptSecondLt1mCountMap.put(adminUsername, acceptSecondLt1mCount + 1);
                    }
                } else if (newAcceptSecond <= 60 * 3) {
                    // 3分钟接入量
                    Integer acceptSecondLt3mCount = queueAcceptSecondLt3mCountMap.get(adminUsername);
                    if (acceptSecondLt3mCount == null) {
                        queueAcceptSecondLt3mCountMap.put(adminUsername, 1);
                    } else {
                        queueAcceptSecondLt3mCountMap.put(adminUsername, acceptSecondLt3mCount + 1);
                    }
                } else if (newAcceptSecond <= 60 * 5) {
                    // 5分钟接入量
                    Integer acceptSecondLt5mCount = queueAcceptSecondLt5mCountMap.get(adminUsername);
                    if (acceptSecondLt5mCount == null) {
                        queueAcceptSecondLt5mCountMap.put(adminUsername, 1);
                    } else {
                        queueAcceptSecondLt5mCountMap.put(adminUsername, acceptSecondLt5mCount + 1);
                    }
                } else {
                    // 超时接入量：超过5分钟接入
                    Integer acceptSecondGt5mCount = queueAcceptSecondGt5mCountMap.get(adminUsername);
                    if (acceptSecondGt5mCount == null) {
                        queueAcceptSecondGt5mCountMap.put(adminUsername, 1);
                    } else {
                        queueAcceptSecondGt5mCountMap.put(adminUsername, acceptSecondGt5mCount + 1);
                    }
                }

                // 最长排队时长
                Long queueAcceptSecondMax = queueAcceptSecondMaxMap.get(adminUsername);
                if (queueAcceptSecondMax == null) {
                    queueAcceptSecondMaxMap.put(adminUsername, Long.valueOf(newAcceptSecond));
                } else {
                    if (newAcceptSecond > queueAcceptSecondMax) {
                        queueAcceptSecondMaxMap.put(adminUsername, newAcceptSecond);
                    }
                }

            } else if (queue.getStatus().equals(StatusConsts.QUEUE_STATUS_LEAVED)) {
                // 未接入会话量(放弃排队量)
                Integer queueLeaveCount = queueLeaveCountMap.get(adminUsername);
                if (queueLeaveCount == null) {
                    queueLeaveCountMap.put(adminUsername, 1);
                } else {
                    queueLeaveCountMap.put(adminUsername, queueLeaveCount + 1);
                }

                // 总放弃排队时长, 计算可得平均放弃时长
                Long queueLeaveSecond = queueLeaveSecondMap.get(adminUsername);
                long newLeaveSecond = (queue.getActionedAt().getTime() - queue.getCreatedAt().getTime())/1000;
                if (queueLeaveSecond == null) {
                    queueLeaveSecondMap.put(adminUsername, Long.valueOf(newLeaveSecond));
                } else {
                    queueLeaveSecondMap.put(adminUsername, queueLeaveSecond + newLeaveSecond);
                }
            }
        }

        //
        for (String adminUsername : queueTotalCountMap.keySet()) {
            Integer queueTotalCount = queueTotalCountMap.get(adminUsername);

            // 排队接入率
            Integer queueAcceptCount = queueAcceptCountMap.get(adminUsername);
            if (queueAcceptCount != null) {
                Double queueAcceptRate = queueAcceptCount * 1.0 / queueTotalCount;
                queueAcceptRateMap.put(adminUsername, queueAcceptRate);
            }

            // 平均排队时长
            Long queueAcceptSecond = queueAcceptSecondMap.get(adminUsername);
            if (queueAcceptSecond != null) {
                Double queueAcceptAverageSecond = queueAcceptSecond * 1.0 / queueTotalCount;
                queueAcceptAverageSecondMap.put(adminUsername, queueAcceptAverageSecond);
            }

            // 平均放弃时长
            Long queueLeaveSecond = queueLeaveSecondMap.get(adminUsername);
            if (queueLeaveSecond != null) {
                Double queueLeaveAverageSecond = queueLeaveSecond * 1.0 / queueTotalCount;
                queueLeaveAverageSecondMap.put(adminUsername, queueLeaveAverageSecond);
            }

            // 1分钟接入率
            Integer acceptSecondLt1mCount = queueAcceptSecondLt1mCountMap.get(adminUsername);
            if (acceptSecondLt1mCount != null) {
                Double acceptSecondLt1mRate = acceptSecondLt1mCount * 1.0 / queueTotalCount;
                queueAcceptSecondLt1mRateMap.put(adminUsername, acceptSecondLt1mRate);
            }

            // 3分钟接入率
            Integer acceptSecondLt3mCount = queueAcceptSecondLt3mCountMap.get(adminUsername);
            if (acceptSecondLt3mCount != null) {
                Double acceptSecondLt3mRate = acceptSecondLt3mCount * 1.0 / queueTotalCount;
                queueAcceptSecondLt3mRateMap.put(adminUsername, acceptSecondLt3mRate);
            }


            // 5分钟接入率
            Integer acceptSecondLt5mCount = queueAcceptSecondLt5mCountMap.get(adminUsername);
            if (acceptSecondLt5mCount != null) {
                Double acceptSecondLt5mRate = acceptSecondLt5mCount * 1.0 / queueTotalCount;
                queueAcceptSecondLt5mRateMap.put(adminUsername, acceptSecondLt5mRate);
            }

            // 超时接入率：超过5分钟接入
            Integer acceptSecondGt5mCount = queueAcceptSecondGt5mCountMap.get(adminUsername);
            if (acceptSecondGt5mCount != null) {
                Double acceptSecondGt5mRate = acceptSecondGt5mCount * 1.0 / queueTotalCount;
                queueAcceptSecondGt5mRateMap.put(adminUsername, acceptSecondGt5mRate);
            }

        }

        // 找出所有管理员角色用户
        Optional<Role> roleOptional = roleRepository.findByValue(RoleConsts.ROLE_ADMIN);
        if (roleOptional.isPresent()) {
            List<User> adminList = userRepository.findByRolesContains(roleOptional.get());
            for (int i = 0; i < adminList.size(); i++) {
                User adminUser = adminList.get(i);

                // 正在进行中的会话
                Integer currentThreadCount = currentThreadCountMap.get(adminUser.getUsername());
                // 当天所有会话
                Integer totalThreadCount = totalThreadCountMap.get(adminUser.getUsername());
                // 评价的总数
                Integer rateTotalCount = rateTotalCountMap.get(adminUser.getUsername());
                // 满意的数量
                Integer rateSatisfyCount = rateSatisfyCountMap.get(adminUser.getUsername());
                // 参评率
                Double rateThreadRate = rateThreadRateMap.get(adminUser.getUsername());
                // 会话满意率
                Double rateSatisfyRate = rateSatisfyRateMap.get(adminUser.getUsername());
                // 队列总量
                Integer queueTotalCount = queueTotalCountMap.get(adminUser.getUsername());
                // 排队中总量
                Integer queuingCount = queuingCountMap.get(adminUser.getUsername());
                // 排队接入率
                Double queueAcceptRate = queueAcceptRateMap.get(adminUser.getUsername());
                // 放弃排队量
                Integer queueLeaveCount = queueLeaveCountMap.get(adminUser.getUsername());
                // 平均排队时长
                Double queueAcceptAverageSecond = queueAcceptAverageSecondMap.get(adminUser.getUsername());
                // 最长排队时长
                Long queueAcceptSecondMax = queueAcceptSecondMaxMap.get(adminUser.getUsername());
                // 1分钟接入率
                Integer queueAcceptSecondLt1mCount = queueAcceptSecondLt1mCountMap.get(adminUser.getUsername());
                Double queueAcceptSecondLt1mRate = queueAcceptSecondLt1mRateMap.get(adminUser.getUsername());
                // 3分钟接入率
                Integer queueAcceptSecondLt3mCount = queueAcceptSecondLt3mCountMap.get(adminUser.getUsername());
                Double queueAcceptSecondLt3mRate = queueAcceptSecondLt3mRateMap.get(adminUser.getUsername());
                // 5分钟接入率
                Integer queueAcceptSecondLt5mCount = queueAcceptSecondLt5mCountMap.get(adminUser.getUsername());
                Double queueAcceptSecondLt5mRate = queueAcceptSecondLt5mRateMap.get(adminUser.getUsername());
                // 超时接入率：超过5分钟接入
                Integer queueAcceptSecondGt5mCount = queueAcceptSecondGt5mCountMap.get(adminUser.getUsername());
                Double queueAcceptSecondGt5mRate = queueAcceptSecondGt5mRateMap.get(adminUser.getUsername());
                // 平均排队放弃时长
                Double queueLeaveAverageSecond = queueLeaveAverageSecondMap.get(adminUser.getUsername());

                // 每小时存储一条，如果没有这插入，否则更新记录
                Date date = new Date();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String type = TypeConsts.STATISTIC_TIME_TYPE_HOUR;
                Optional<Statistic> statisticOptional = statisticRepository.findByDateAndHourAndTypeAndUser(date, hour, type,  adminUser);
                //
                Statistic statistic;
                if (statisticOptional.isPresent()) {
                    statistic = statisticOptional.get();
                } else {
                    statistic = new Statistic();
                }
                //
                statistic.setCurrentThreadCount(currentThreadCount == null ? 0 : currentThreadCount);
                statistic.setTotalThreadCount(totalThreadCount == null ? 0 : totalThreadCount);
                //statistic.setOnlineAgentCount();
                statistic.setRateCount(rateTotalCount == null ? 0 : rateTotalCount);
                statistic.setSatisfyCount(rateSatisfyCount == null ? 0 : rateSatisfyCount);
                statistic.setRateRate(rateThreadRate ==  null ? 0 : rateThreadRate);
                statistic.setSatisfyRate(rateSatisfyRate == null ? 0 : rateSatisfyRate);
                statistic.setQueuingCount(queuingCount == null ? 0 : queuingCount);
                statistic.setTotalQueueCount(queueTotalCount == null ? 0 : queueTotalCount);
                statistic.setAcceptQueueRate(queueAcceptRate == null ? 0 : queueAcceptRate);
                statistic.setLeaveQueueCount(queueLeaveCount == null ? 0 : queueLeaveCount);
                statistic.setAverageQueueTimeLength(queueAcceptAverageSecond == null ? 0 : queueAcceptAverageSecond);
                statistic.setMaxQueueTimeLength(queueAcceptSecondMax == null ? 0 :  queueAcceptSecondMax);
                statistic.setAverageLeaveQueueTimeLength(queueLeaveAverageSecond == null ? 0 : queueLeaveAverageSecond);
                statistic.setAcceptRateLt1m(queueAcceptSecondLt1mRate == null ? 0 : queueAcceptSecondLt1mRate);
                statistic.setAcceptCountLt1m(queueAcceptSecondLt1mCount == null ? 0 : queueAcceptSecondLt1mCount);
                statistic.setAcceptRateLt3m(queueAcceptSecondLt3mRate == null ? 0 : queueAcceptSecondLt3mRate);
                statistic.setAcceptCountLt3m(queueAcceptSecondLt3mCount == null ? 0 : queueAcceptSecondLt3mCount);
                statistic.setAcceptRateLt5m(queueAcceptSecondLt5mRate == null ? 0 : queueAcceptSecondLt5mRate);
                statistic.setAcceptCountLt5m(queueAcceptSecondLt5mCount == null ? 0 : queueAcceptSecondLt5mCount);
                statistic.setAcceptRateGt5m(queueAcceptSecondGt5mRate == null ? 0 : queueAcceptSecondGt5mRate);
                statistic.setAcceptCountGt5m(queueAcceptSecondGt5mCount == null ? 0 : queueAcceptSecondGt5mCount);

                statistic.setDate(date);
                statistic.setHour(hour);
                statistic.setType(type);
                statistic.setUser(adminUser);
                statisticRepository.save(statistic);
            }
        }
    }

    /**
     * 废弃
     *
     * 按照小时统计：
     *  每个小时的第5分钟开始运行，统计上一个小时之内产生的数据
     *
     * agent + workGroup
     *
     * cron 顺序：
     *  second, minute, hour, day, month, weekday
     *
     *  TODO: 持久化存储到Redis
     */
    // @Scheduled(cron = "0 5 * * * *")
    public void hourlyStatisticDetail() {
        logger.info("hourlyStatisticDetail {}", new SimpleDateFormat(BdConstants.DATETIMESTAMP_FORMAT).format(new Date()));

        Calendar calendar = Calendar.getInstance();

        // 按照小时统计
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startAt = calendar.getTime();
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endAt = calendar.getTime();

        // 客服用户名：访客消息数
        Map<String, Integer> visitorMessageCountMap = new HashMap<>(16);
        // 客服消息数
        Map<String, Integer> agentMessageCountMap = new HashMap<>(16);
        // 总消息数 = 访客消息数 + 客服消息数
        Map<String, Integer> totalMessageCountMap = new HashMap<>(16);
        // 客服字数
        Map<String, Integer> agentWordCountMap = new HashMap<>(16);
        // 答问比 = 客服消息数 / 访客消息数
        Map<String, Double> answerQuestionRateMap = new HashMap<>(16);
        // 某条会话内 访客消息数
        Map<String, Integer> threadVisitorMessageCountMap = new HashMap<>(16);
        // 某条会话内 客服消息数
        Map<String, Integer> threadAgentMessageCountMap = new HashMap<>(16);
        // 某条会话内 总消息数 = 访客消息数 + 客服消息数
        Map<String, Integer> threadTotalMessageCountMap = new HashMap<>(16);
        // 首次响应时长: threadTid: Second
         Map<String, Long> responseSecondInitMap = new HashMap<>(16);
        // 总响应时长：threadTid: Second
        Map<String, Long> responseSecondTotalMap = new HashMap<>(16);
        // 响应时间在30秒以内的数量, threadTid: Count
        Map<String, Integer> responseSecondLt30sCountMap = new HashMap<>(16);
        // threadTid ：agentUsername
        Map<String, String> responseSecondAgentMap = new HashMap<>(16);
        // 访客消息时间戳：threadTid: date
        Map<String, Date> visitorMessageCreatedAtMap = new HashMap<>(16);
        // 客服回复消息数（非总数，过滤掉部分）
        Map<String, Integer> agentMessageResponseCountMap = new HashMap<>(16);

        // 按照客服账号进行统计
        // 上一个小时内产生的消息
        List<Message> messageList = messageRepository.findByCreatedAtBetweenOrderByIdAsc(startAt, endAt);
        for (int i = 0; i < messageList.size(); i++) {
            Message message = messageList.get(i);
            if (message.getThread() == null || message.getThread().getAgent() == null) {
                continue;
            }

            logger.info("message mid: {}, tid {}, isVisitor {}",
                    message.getMid(), message.getThread().getTid(), message.getUser().isVisitor());

            // 消息所属会话的主客服用户名
            String agentUsername = message.getThread().getAgent().getUsername();
            String threadTid = message.getThread().getTid();
            // 访客
            if (message.getUser().isVisitor()) {

                // 客服维度-访客消息数
                Integer visitorMessageCount = visitorMessageCountMap.get(agentUsername);
                if (visitorMessageCount == null) {
                    visitorMessageCountMap.put(agentUsername, 1);
                } else {
                    visitorMessageCountMap.put(agentUsername, visitorMessageCount + 1);
                }

                // 会话thread维度进行统计
                // 每次会话访客消息数
                Integer threadVisitorMessageCount = threadVisitorMessageCountMap.get(threadTid);
                if (threadVisitorMessageCount == null) {
                    threadAgentMessageCountMap.put(threadTid, 1);
                } else {
                    threadVisitorMessageCountMap.put(threadTid, threadVisitorMessageCount + 1);
                }

                // 响应时长, 当访客连续发送多条消息的时候，仅记录访客第一条消息时间戳
                Date visitorMessageCreatedAt = visitorMessageCreatedAtMap.get(threadTid);
                if (visitorMessageCreatedAt == null) {
                    visitorMessageCreatedAtMap.put(threadTid, message.getCreatedAt());
                }

            } else {

                // FIXME: 所有非访客消息均统计为主客服消息，未区分监控者和被邀请客服、以及转接前客服账号的消息
                // 客服维度-客服消息数
                Integer agentMessageCount = agentMessageCountMap.get(agentUsername);
                if (agentMessageCount == null) {
                    agentMessageCountMap.put(agentUsername, 1);
                } else {
                    agentMessageCountMap.put(agentUsername, agentMessageCount + 1);
                }
                // 会话thread维度进行统计
                // 每次会话客服消息数
                Integer threadAgentMessageCount = threadAgentMessageCountMap.get(threadTid);
                if (threadAgentMessageCount == null) {
                    threadAgentMessageCountMap.put(threadTid, 1);
                } else {
                    threadAgentMessageCountMap.put(threadTid, threadAgentMessageCount + 1);
                }

                // 客服字数
                // TODO: 暂未考虑图片和公众号、小程序、微博等情况
                Integer messageContentWordCount;
                if (message.getContent() == null) {
                    messageContentWordCount = 0;
                } else {
                    messageContentWordCount = message.getContent().length();
                }
                Integer agentWordCount = agentWordCountMap.get(agentUsername);
                if (agentWordCount == null) {
                    agentWordCountMap.put(agentUsername, messageContentWordCount);
                } else {
                    agentWordCountMap.put(agentUsername, agentWordCount + messageContentWordCount);
                }

                // 总响应时长，针对访客的一条消息，当客服回复多条的时候，仅计算客服第一条回复
                Date visitorMessageCreatedAt = visitorMessageCreatedAtMap.get(threadTid);
                if (visitorMessageCreatedAt != null) {
                    Long responseTime = (message.getCreatedAt().getTime() - visitorMessageCreatedAt.getTime())/1000;
                    // 查询出的消息按照id增排序保证大于 0
                    if (responseTime > 0) {
                        Long responseSecond = responseSecondTotalMap.get(threadTid);
                        if (responseSecond == null) {
                            responseSecondTotalMap.put(threadTid, responseTime);
                            responseSecondAgentMap.put(threadTid, agentUsername);
                            agentMessageResponseCountMap.put(threadTid, 1);
                            // 计算首次响应时长
                            responseSecondInitMap.put(threadTid, responseTime);
                        } else {
                            responseSecondTotalMap.put(threadTid, responseSecond + responseTime);
                            // 客服响应次数
                            Integer agentMessageResponseCount = agentMessageResponseCountMap.get(threadTid);
                            if (agentMessageResponseCount != null) {
                                agentMessageResponseCountMap.put(threadTid, agentMessageResponseCount + 1);
                            }
                        }
                        // 响应时长30s以内
                        if (responseTime <= 30) {
                            Integer responseSecondLt30sCount = responseSecondLt30sCountMap.get(threadTid);
                            if (responseSecondLt30sCount == null) {
                                responseSecondLt30sCountMap.put(threadTid, 1);
                            } else {
                                responseSecondLt30sCountMap.put(threadTid, threadAgentMessageCount + 1);
                            }
                        }
                    }
                    // 删除访客时间戳
                    visitorMessageCreatedAtMap.remove(threadTid);
                }
            }

            // 总消息量
            // 客服维度统计
            Integer totalMessageCount = totalMessageCountMap.get(agentUsername);
            if (totalMessageCount == null) {
                totalMessageCountMap.put(agentUsername, 1);
            } else {
                totalMessageCountMap.put(agentUsername, totalMessageCount + 1);
            }

            // 会话thread维度统计
            // 每次会话的消息总数
            // TODO: 每次会话消息平均数
            Integer threadTotalMessageCount = threadTotalMessageCountMap.get(threadTid);
            if (threadTotalMessageCount == null) {
                threadTotalMessageCountMap.put(threadTid, 1);
            } else {
                threadTotalMessageCountMap.put(threadTid, threadTotalMessageCount + 1);
            }
        }

        // 答问比
        for (String agentUsername : agentMessageCountMap.keySet()) {
            Integer agentMessageCount = agentMessageCountMap.get(agentUsername);
            Integer visitorMessageCount = visitorMessageCountMap.get(agentUsername);

            if (visitorMessageCount != null) {
                Double rate = agentMessageCount * 1.0 / visitorMessageCount;
                answerQuestionRateMap.put(agentUsername, rate);
            } else {
                answerQuestionRateMap.put(agentUsername, agentMessageCount * 1.0);
            }
        }

        // 30s响应率
        Map<String, Double> responseSecondLt30sRateMap = new HashMap<>(16);
        for (String threadTid : responseSecondAgentMap.keySet()) {
            String agentUsername = responseSecondAgentMap.get(threadTid);
            Integer responseSecondLt30sCount = responseSecondLt30sCountMap.get(threadTid);
            Integer agentMessageResponseCount = agentMessageResponseCountMap.get(threadTid);

            if (agentMessageResponseCount > 0) {
                Double responseSecondLt30sRate = responseSecondLt30sCount * 1.0 / agentMessageResponseCount;
                //
                responseSecondLt30sRateMap.put(agentUsername, responseSecondLt30sRate);
            }

        }

        // 平均响应时长
        Map<String, Double> responseSecondAverageMap = new HashMap<>(16);
        for (String threadTid : responseSecondAgentMap.keySet()) {
            String agentUsername = responseSecondAgentMap.get(threadTid);
            Long responseSecond = responseSecondTotalMap.get(threadTid);
            Integer agentMessageResponseCount = agentMessageResponseCountMap.get(threadTid);

            if (agentMessageResponseCount  > 0) {
                Double responseSecondAverage = responseSecond * 1.0 / agentMessageResponseCount;
                //
                responseSecondAverageMap.put(agentUsername, responseSecondAverage);
            }
        }

        // 平均首次响应时长
        Map<String, Long> responseSecondInitTotalMap = new HashMap<>(16);
        Map<String, Integer> responseSecondInitCountMap = new HashMap<>(16);
        for (String threadTid : responseSecondInitMap.keySet()) {
            String agentUsername = responseSecondAgentMap.get(threadTid);
            Long responseSecondInit = responseSecondInitMap.get(threadTid);
            // 按照agentUsername统计首次响应总时长
            Long responseSecondInitTotal = responseSecondInitTotalMap.get(agentUsername);
            Integer responseSecondInitCount = responseSecondInitCountMap.get(agentUsername);
            if (responseSecondInitTotal == null) {
                responseSecondInitTotalMap.put(agentUsername, responseSecondInit);
                responseSecondInitCountMap.put(agentUsername, 1);
            } else {
                responseSecondInitTotalMap.put(agentUsername, responseSecondInitTotal + responseSecondInit);
                responseSecondInitCountMap.put(agentUsername, responseSecondInitCount + 1);
            }
        }
        Map<String, Double> responseSecondInitAverageMap = new HashMap<>(16);
        for (String agentUsername : responseSecondInitTotalMap.keySet()) {
            Long responseSecondInitTotal = responseSecondInitTotalMap.get(agentUsername);
            Integer responseSecondInitCount = responseSecondInitCountMap.get(agentUsername);
            //
            Double responseSecondInitAverage = responseSecondInitTotal * 1.0 / responseSecondInitCount;
            responseSecondInitAverageMap.put(agentUsername, responseSecondInitAverage);
        }

        // 慢响应人数, 慢响应会话数
        // TODO: 定义慢相应时间长度

        Map<String, Integer> threadTotalCountMap = new HashMap<>(16);
        Map<String, Long> threadSecondMap = new HashMap<>(16);
        // 上一个小时内产生的会话
        List<Thread> threadList = threadRepository.findByCreatedAtBetween(startAt, endAt);
        for (int i = 0; i < threadList.size(); i++) {
            Thread thread = threadList.get(i);
            String threadTid = thread.getTid();
            if (thread.getAgent() == null) {
                continue;
            }
            String adminUsername = thread.getAgent().getAdmin().getUsername();
            logger.info("thread tid {}, adminUsername {}",  threadTid, adminUsername);

            // 会话总数
            Integer threadTotalCount = threadTotalCountMap.get(adminUsername);
            if (threadTotalCount == null) {
                threadTotalCountMap.put(adminUsername, 1);
            } else {
                threadTotalCountMap.put(adminUsername, threadTotalCount + 1);
            }

            // 会话总时长
            long difference = (thread.getClosedAt().getTime() - thread.getStartedAt().getTime())/1000;
            Long threadSecond = threadSecondMap.get(adminUsername);
            if (threadSecond == null) {
                threadSecondMap.put(adminUsername, difference);
            } else {
                threadSecondMap.put(adminUsername, threadSecond + difference);
            }

            // 长接待人数，长会话数
            // TODO: 定义长接待时间

        }

        // 平均会话时长
        Map<String, Double> threadSecondAverageMap = new HashMap<>(16);
        for (String adminUsername : threadSecondMap.keySet()) {
            Integer threadTotalCount = threadTotalCountMap.get(adminUsername);
            Long threadSecond = threadSecondMap.get(adminUsername);

            Double threadSecondAverage = threadSecond * 1.0 / threadTotalCount;
            threadSecondAverageMap.put(adminUsername, threadSecondAverage);
        }

        // 最大同时接待数

        //
        Optional<Role> roleOptional = roleRepository.findByValue(RoleConsts.ROLE_WORKGROUP_AGENT);
        if (roleOptional.isPresent()) {
            List<User> agentList = userRepository.findByRolesContains(roleOptional.get());
            for (int i = 0; i < agentList.size(); i++) {
                User agentUser = agentList.get(i);
                // 客服用户名：访客消息数
                Integer visitorMessageCount = visitorMessageCountMap.get(agentUser.getUsername());
                // 客服消息数
                Integer agentMessageCount = agentMessageCountMap.get(agentUser.getUsername());
                // 总消息数 = 访客消息数 + 客服消息数
                Integer totalMessageCount = totalMessageCountMap.get(agentUser.getUsername());
                // 客服字数
                Integer agentWordCount = agentWordCountMap.get(agentUser.getUsername());
                // 答问比 = 客服消息数 / 访客消息数
                Double answerQuestionRate = answerQuestionRateMap.get(agentUser.getUsername());
                // 某条会话内 访客消息数
                Integer threadVisitorMessageCount = threadVisitorMessageCountMap.get(agentUser.getUsername());
                // 某条会话内 客服消息数
                Integer threadAgentMessageCount = threadAgentMessageCountMap.get(agentUser.getUsername());
                // 某条会话内 总消息数 = 访客消息数 + 客服消息数
                Integer threadTotalMessageCount = threadTotalMessageCountMap.get(agentUser.getUsername());
                // 首次响应时长: threadTid: Second
                Long responseSecondInit = responseSecondInitMap.get(agentUser.getUsername());
                // 总响应时长：threadTid: Second
                Long responseSecondTotal = responseSecondTotalMap.get(agentUser.getUsername());
                // 响应时间在30秒以内的数量, threadTid: Count
                Integer responseSecondLt30sCount = responseSecondLt30sCountMap.get(agentUser.getUsername());
                // threadTid ：agentUsername
                String responseSecondAgent = responseSecondAgentMap.get(agentUser.getUsername());
                // 访客消息时间戳：threadTid: date
                Date visitorMessageCreatedAt = visitorMessageCreatedAtMap.get(agentUser.getUsername());
                // 客服回复消息数（非总数，过滤掉部分）
                Integer agentMessageResponseCount = agentMessageResponseCountMap.get(agentUser.getUsername());
                // 30s响应率
                Double responseSecondLt30sRate = responseSecondLt30sRateMap.get(agentUser.getUsername());
                // 平均响应时长
                Double responseSecondAverage = responseSecondAverageMap.get(agentUser.getUsername());
                // 平均首次响应时长
                Long responseSecondInitTotal = responseSecondInitTotalMap.get(agentUser.getUsername());
                Integer responseSecondInitCount = responseSecondInitCountMap.get(agentUser.getUsername());
                Integer threadTotalCount = threadTotalCountMap.get(agentUser.getUsername());
                Long threadSecond = threadSecondMap.get(agentUser.getUsername());
                // 平均会话时长
                Double threadSecondAverage = threadSecondAverageMap.get(agentUser.getUsername());


                Date date = new Date();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String timeType = TypeConsts.STATISTIC_TIME_TYPE_HOUR;
                String dimensionType = TypeConsts.STATISTIC_DIMENSION_TYPE_AGENT;
                Optional<StatisticDetail> statisticDetailOptional = statisticDetailRepository
                        .findByDateAndHourAndTimeTypeAndDimensionTypeAndUser(date, hour, timeType, dimensionType, agentUser);
                //
                StatisticDetail statisticDetail;
                if (statisticDetailOptional.isPresent()) {
                    statisticDetail = statisticDetailOptional.get();
                } else {
                    statisticDetail = new StatisticDetail();
                }
                //
                statisticDetail.setVisitorMessageCount(visitorMessageCount ==  null ?  0 : visitorMessageCount);
                statisticDetail.setAgentMessageCount(agentMessageCount == null ? 0 :  agentMessageCount);
                statisticDetail.setTotalMessageCount(totalMessageCount == null ? 0 : totalMessageCount);
                statisticDetail.setAgentWordCount(agentWordCount ==  null ?  0 : agentWordCount);
                statisticDetail.setAnswerQuestionRate(answerQuestionRate == null ? 0 : answerQuestionRate);
                statisticDetail.setResponseRateLt30s(responseSecondLt30sRate == null ? 0 : responseSecondLt30sRate);
                statisticDetail.setAverageTimeLength(threadSecondAverage == null ? 0 : threadSecondAverage);

                statisticDetail.setDate(date);
                statisticDetail.setHour(hour);
                statisticDetail.setTimeType(timeType);
                statisticDetail.setDimensionType(dimensionType);
                statisticDetailRepository.save(statisticDetail);
            }
        }


    }


    /**
     * 废弃
     * 按照天统计:
     *  每天的 01：00 开始运行，统计昨天产生的数据
     *
     * agent + workGroup
     *
     * cron 顺序：
     *  second, minute, hour, day, month, weekday
     */
    // @Scheduled(cron = "0 0 1 * * *")
    public void dailyStatisticDetail() {
        logger.info("dailyStatisticDetail {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }



}
