package org.bytedesk.rest.controller.v1;

import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.model.*;
import org.bytedesk.jpa.model.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * 基类
 *
 * @author bytedesk.com
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 创建动态搜索
     *
     * @param nickname
     * @param createdAtStart
     * @param createdAtEnd
     * @param workGroupNickname
     * @param agentRealName
     * @param client
     * @return
     */
    public Specification getSpecification(User user,
                                          String type, String nickname, String createdAtStart, String createdAtEnd,
                                          String workGroupNickname, String agentRealName, String client) {

        // 构建动态查询条件
        Specification specification;

        if (type.equals(TypeConsts.SPECIFICATION_TYPE_THREAD)) {

            // 构建动态查询条件
            specification = (Specification<Thread>) (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicateList = new ArrayList<>();

                String invalidDate = "Invalid date";
                if (null != createdAtStart && !createdAtStart.equals(invalidDate)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startAt = new Date();
                    try {
                        startAt = dateFormat.parse(createdAtStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"), startAt));
                }

                if (null != createdAtEnd && !createdAtEnd.equals(invalidDate)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date endAt = new Date();
                    try {
                        endAt = dateFormat.parse(createdAtEnd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"), endAt));
                }

                // 搜索过滤历史会话
                predicateList = threadSpecific(user, nickname, workGroupNickname, agentRealName, client, root, criteriaBuilder, predicateList);

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            };

            return specification;

        } else if (type.equals(TypeConsts.SPECIFICATION_TYPE_RATE))  {
            // 构建动态查询条件
            specification = (Specification<Rate>) (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicateList = new ArrayList<>();

                String invalidDate = "Invalid date";
                if (null != createdAtStart && !createdAtStart.equals(invalidDate)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startAt = new Date();
                    try {
                        startAt = dateFormat.parse(createdAtStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"), startAt));
                }

                if (null != createdAtEnd && !createdAtEnd.equals(invalidDate)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date endAt = new Date();
                    try {
                        endAt = dateFormat.parse(createdAtEnd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"), endAt));
                }
                // 搜索过滤历史评价
                predicateList = rateSpecific(user, nickname, workGroupNickname, agentRealName, client, root, criteriaBuilder, predicateList);

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            };

            return specification;

        } else if (type.equals(TypeConsts.SPECIFICATION_TYPE_LEAVE_MESSAGE))  {
            // 构建动态查询条件
            specification = (Specification<LeaveMessage>) (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicateList = new ArrayList<>();

                String invalidDate = "Invalid date";
                if (null != createdAtStart && !createdAtStart.equals(invalidDate)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startAt = new Date();
                    try {
                        startAt = dateFormat.parse(createdAtStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"), startAt));
                }

                if (null != createdAtEnd && !createdAtEnd.equals(invalidDate)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date endAt = new Date();
                    try {
                        endAt = dateFormat.parse(createdAtEnd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"), endAt));
                }
                // 搜索过滤历史留言
                predicateList = leaveMessageSpecific(user, nickname, workGroupNickname, agentRealName, client, root, criteriaBuilder, predicateList);

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            };

            return specification;
        }

//        else if (type.equals(TypeConsts.SPECIFICATION_TYPE_MESSAGE))  {
//            // 搜索过滤历史消息
//            predicateList = messageSpecific(user, nickname, workGroupNickname, agentRealName, client, root, criteriaBuilder, predicateList);
//        }  else if (type.equals(TypeConsts.SPECIFICATION_TYPE_STATUS))  {
//            // 搜索过滤历史状态
//            predicateList = statusSpecific(user, agentRealName, client, root, criteriaBuilder, predicateList);
//        } else if (type.equals(TypeConsts.SPECIFICATION_TYPE_QUEUE))  {
//            // 搜索过滤历史排队
//            predicateList = queueSpecific(user, nickname, workGroupNickname, agentRealName, client, root, criteriaBuilder, predicateList);
//        } else if (type.equals(TypeConsts.SPECIFICATION_TYPE_BROWSE))  {
//            // 搜索过滤历史浏览记录
//            predicateList = browseSpecific(user, nickname, workGroupNickname, agentRealName, client, root, criteriaBuilder, predicateList);
//        }

        return null;
    }


    /**
     * 过滤历史会话
     *
     * FIXME: 仅显示成功接入客服的会话
     *
     * TODO：管理员获取所有历史会话
     * TODO：客服组长获取组内成员所以历史会话
     * TODO：客服账号获取自己的历史会话
     *
     * @param nickname
     * @param workGroupNickname
     * @param agentRealName
     * @param client
     * @param root
     * @param criteriaBuilder
     * @param predicateList
     * @return
     */
    private List<Predicate> threadSpecific(User user, String nickname, String workGroupNickname, String agentRealName, String client,
                                            Root<Thread> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {

        // 访客都跟本账号相同
        predicateList.add(criteriaBuilder.equal(root.get("visitor").get("subDomain"), user.getSubDomain()));

        // 会话类型：thread or appointed
        predicateList.add(criteriaBuilder.or(
                criteriaBuilder.equal(root.get("type"), TypeConsts.THREAD_TYPE_WORK_GROUP),
                criteriaBuilder.equal(root.get("type"), TypeConsts.THREAD_TYPE_APPOINTED)
        ));

        // 会话已经关闭
        predicateList.add(criteriaBuilder.equal(root.get("closed"), true));

        // agent not null
        predicateList.add(criteriaBuilder.isNotNull(root.get("agent")));

        if (null != nickname) {
            predicateList.add(criteriaBuilder.like(root.get("visitor").get("nickname"), "%" + nickname + "%"));
        }

        String all = "all";
        if (null != workGroupNickname && !workGroupNickname.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("workGroup").get("nickname"), "%" + workGroupNickname + "%"));
        }

        if (null != agentRealName && !agentRealName.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("agent").get("realName"), "%" + agentRealName + "%"));
        }

        if (null != client && !client.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("visitor").get("client"), "%" + client + "%"));
        }

        //
        if (user.isAdmin()) {
            // 管理员不需要添加额外过滤

        } else if (user.isWorkGroupAdmin()) {
            // 客服组长

            // 工作组会话 + 指定坐席
//            predicateList.add(criteriaBuilder.or(
//                    criteriaBuilder.equal(root.get("workGroup").get("admin").get("uid"), user.getUid())
////                    criteriaBuilder.isMember(root.get("agent"), )
//            ));

            // FIXME: 缺少指定坐席

            predicateList.add(criteriaBuilder.equal(root.get("workGroup").get("admin").get("uid"), user.getUid()));

        } else {

            // 客服本人
            predicateList.add(criteriaBuilder.equal(root.get("agent").get("uid"), user.getUid()));
        }

        return predicateList;
    }


    private List<Predicate> rateSpecific(User user, String nickname, String workGroupNickname, String agentRealName, String client,
                                         Root<Rate> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {

        if (null != nickname) {
            predicateList.add(criteriaBuilder.like(root.get("visitor").get("nickname"), "%" + nickname + "%"));
        }

        String all = "all";
        if (null != workGroupNickname && !workGroupNickname.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("thread").get("workGroup").get("nickname"), "%" + workGroupNickname + "%"));
        }

        if (null != agentRealName && !agentRealName.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("thread").get("agent").get("realName"), "%" + agentRealName + "%"));
        }

        if (null != client && !client.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("visitor").get("client"), "%" + client + "%"));
        }

        //
        if (user.isAdmin()) {
            // 管理员
            logger.info("filter admin");
            // FIXME: 未显示管理员的
            predicateList.add(criteriaBuilder.like(root.get("user").get("username"), "%" + user.getUsername() + "%"));
//            predicateList.add(criteriaBuilder.or(
//                    criteriaBuilder.equal(root.get("agent").get("username"), user.getUsername()),
//                    criteriaBuilder.equal(root.get("agent").get("user").get("username"), user.getUsername())
//            ));
        } else if (user.isWorkGroupAdmin()) {
            logger.info("filter group admin");
            // FIXME: 多个工作组
            // 客服组长
            WorkGroup workGroup = (WorkGroup) user.getWorkGroups().toArray()[0];
            predicateList.add(criteriaBuilder.isMember(workGroup, root.get("agent").get("workGroups")));

        } else {
            logger.info("filter agent");
            // 客服账号
            predicateList.add(criteriaBuilder.equal(root.get("agent").get("username"), user.getUsername()));
        }

        predicateList.add(criteriaBuilder.isNotNull(root.get("thread").get("agent")));

        return predicateList;
    }

    private List<Predicate> messageSpecific(User user, String nickname, String workGroupNickname, String agentRealName, String client,
                                            Root<Message> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {

        if (null != nickname) {
            predicateList.add(criteriaBuilder.like(root.get("user").get("nickname"), "%" + nickname + "%"));
        }

        String all = "all";
        if (null != workGroupNickname && !workGroupNickname.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("thread").get("workGroup").get("nickname"), "%" + workGroupNickname + "%"));
        }

        if (null != agentRealName && !agentRealName.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("thread").get("agent").get("realName"), "%" + agentRealName + "%"));
        }

        if (null != client && !client.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("user").get("client"), "%" + client + "%"));
        }

        //
        if (user.isAdmin()) {

        } else if (user.isWorkGroupAdmin()) {

        } else {

        }

        return predicateList;
    }

    private List<Predicate> leaveMessageSpecific(User user, String nickname, String workGroupNickname, String agentRealName, String client,
                                                 Root<LeaveMessage> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {

        if (null != nickname) {
            predicateList.add(criteriaBuilder.like(root.get("visitor").get("nickname"), "%" + nickname + "%"));
        }

        String all = "all";
        if (null != workGroupNickname && !workGroupNickname.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("workGroup").get("nickname"), "%" + workGroupNickname + "%"));
        }

        if (null != agentRealName && !agentRealName.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("agent").get("realName"), "%" + agentRealName + "%"));
        }

        if (null != client && !client.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("visitor").get("client"), "%" + client + "%"));
        }

        //
        if (user.isAdmin()) {
            // 管理员
            predicateList.add(criteriaBuilder.like(root.get("user").get("username"), "%" + user.getUsername() + "%"));
        } else if (user.isWorkGroupAdmin()) {
            // FIXME: 多个工作组
            // 客服组长
            WorkGroup workGroup = (WorkGroup) user.getWorkGroups().toArray()[0];
            predicateList.add(criteriaBuilder.isMember(workGroup, root.get("agent").get("workGroups")));
        } else {
            // 客服账号
            predicateList.add(criteriaBuilder.equal(root.get("agent").get("username"), user.getUsername()));
        }

        return predicateList;
    }

    private List<Predicate> statusSpecific(User user, String agentRealName, String client,
                                           Root<Statistic> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {

        String all = "all";
        if (null != agentRealName && !agentRealName.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("user").get("realName"), "%" + agentRealName + "%"));
        }

        if (null != client && !client.equals(all)) {
            predicateList.add(criteriaBuilder.like(root.get("client"), "%" + client + "%"));
        }

        //
        if (user.isAdmin()) {

        } else if (user.isWorkGroupAdmin()) {

        } else {

        }

        return predicateList;
    }

    private List<Predicate> queueSpecific(User user, String nickname, String workGroupNickname, String agentRealName, String client,
                                         Root<Queue> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {


        //
        if (user.isAdmin()) {

        } else if (user.isWorkGroupAdmin()) {

        } else {

        }

        return predicateList;
    }

    private List<Predicate> browseSpecific(User user, String nickname, String workGroupNickname, String agentRealName, String client,
                                         Root<Browse> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {


        //
        if (user.isAdmin()) {

        } else if (user.isWorkGroupAdmin()) {

        } else {

        }
        return predicateList;
    }


}






