package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.constant.RoleConsts;
import org.bytedesk.jpa.constant.StatusConsts;
import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.model.Role;
import org.bytedesk.jpa.model.Statistic;
import org.bytedesk.jpa.model.StatisticDetail;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.repository.RoleRepository;
import org.bytedesk.jpa.repository.StatisticDetailRepository;
import org.bytedesk.jpa.repository.StatisticRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.mq.service.redis.RedisStatisticService;
import org.bytedesk.rest.controller.v1.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计数据
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/statistic")
public class StatisticController extends BaseController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    StatisticDetailRepository statisticDetailRepository;

    @Autowired
    RedisStatisticService redisStatisticService;

    /**
     * 获取Dashboard统计数据
     * 仅有管理员查看
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/all")
    public JsonResult all(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 一. 数据总览

            // 直接从redis中获取数据
            String adminUid = adminOptional.get().getUid();

            // 1. 在线客服数
            Long onlineAgentCount = redisStatisticService.onlineAgentCount(adminUid);

            // 2. 当前会话数
            Long currentThreadCount = redisStatisticService.currentThreadCount(adminUid);

            // 3. 当前排队数
            Long currentQueueCount = redisStatisticService.currentQueueCount(adminUid);

            // 4. 今日放弃排队量
            Long todayLeaveQueueCount = redisStatisticService.leaveQueueCount(adminUid);

            // 5. 今日会话总量
            Long todayTotalThreadCount = redisStatisticService.totalThreadCount(adminUid);

            // 6. 今日排队总量
            Long todayTotalQueueCount = redisStatisticService.totalQueueCount(adminUid);

            // 7. 当天从0点开始，会话总量趋势图
            // 8. 当天从0点开始，排队总量趋势图
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(new Date());
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String timeTypeHour = TypeConsts.STATISTIC_TIME_TYPE_HOUR;
            List<Statistic> statisticList = statisticRepository.findByDateAndTypeAndUser(date, timeTypeHour, adminOptional.get());

            // 二. 客服详情
            Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
            Page<StatisticDetail> statisticDetailList = statisticDetailRepository.findByDateAndTimeTypeAndUser_User(date, timeTypeHour, adminOptional.get(), pageable);

            // 结果map
            Map<String, Object> objectMap = new HashMap<>(8);
            objectMap.put("onlineAgentCount", onlineAgentCount);
            objectMap.put("currentThreadCount", currentThreadCount);
            objectMap.put("currentQueueCount", currentQueueCount);
            objectMap.put("todayTotalThreadCount", todayTotalThreadCount);
            objectMap.put("todayTotalQueueCount", todayTotalQueueCount);
            objectMap.put("todayLeaveQueueCount", todayLeaveQueueCount);
            objectMap.put("statisticList", statisticList);
            objectMap.put("statisticDetailList", statisticDetailList);

            // 返回结果
            jsonResult.setMessage("获取统计数据成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 获取统计数据详情
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/detail")
    public JsonResult detail(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             @RequestParam(value = "client") String client) {

        logger.info("page {}, size {}, client {}", page, size, client);

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            //
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(new Date());
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String timeTypeHour = TypeConsts.STATISTIC_TIME_TYPE_HOUR;

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<StatisticDetail> statisticDetailPage = statisticDetailRepository.findByDateAndTimeTypeAndUser_User(date, timeTypeHour, adminOptional.get(), pageable);

            // 返回结果
            jsonResult.setMessage("获取统计数据详情成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(statisticDetailPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 获取Thread会话分析
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/thread")
    public JsonResult thread(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            //
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(new Date());
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String timeTypeHour = TypeConsts.STATISTIC_TIME_TYPE_HOUR;

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<StatisticDetail> statisticDetailPage = statisticDetailRepository.findByDateAndTimeTypeAndUser_User(date, timeTypeHour, adminOptional.get(), pageable);

            //
            jsonResult.setMessage("获取会话统计数据成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(statisticDetailPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 获取rate会话分析
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/rate")
    public JsonResult rate(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Date date = new Date();
            String timeType = TypeConsts.STATISTIC_TIME_TYPE_HOUR;
            List<StatisticDetail> statisticDetailList = statisticDetailRepository.findByDateAndTimeTypeAndUser(date, timeType, adminOptional.get());

            //
            jsonResult.setMessage("获取评价统计数据成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(statisticDetailList);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 获取当前在线客服
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/onlineAgent")
    public JsonResult onlineAgent(Principal principal,
                                  @RequestParam(value = "page") int page,
                                  @RequestParam(value = "size") int size,
                                  @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        //
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

            Optional<Role> roleOptional = roleRepository.findByValue(RoleConsts.ROLE_VISITOR);

            // 客服账号
            Page<User> userPage = userRepository.findBySubDomainAndConnectionStatusAndRolesNotContains(adminOptional.get().getSubDomain(),
                    StatusConsts.USER_STATUS_CONNECTED, roleOptional.get(), pageable);

            // 返回结果
            jsonResult.setMessage("获取当前在线客服成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(userPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 分页查询当前会话
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/currentThread")
    public JsonResult currentThread(Principal principal,
                                    @RequestParam(value = "page") int page,
                                    @RequestParam(value = "size") int size,
                                    @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

            //


            jsonResult.setMessage("获取当前会话成功");
            jsonResult.setStatus_code(200);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 分页查询当前排队
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/currentQueue")
    public JsonResult currentQueue(Principal principal,
                                   @RequestParam(value = "page") int page,
                                   @RequestParam(value = "size") int size,
                                   @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");



            jsonResult.setMessage("获取当前排队成功");
            jsonResult.setStatus_code(200);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 分页查询当日放弃排队
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/leaveQueue")
    public JsonResult leaveQueue(Principal principal,
                                 @RequestParam(value = "page") int page,
                                 @RequestParam(value = "size") int size,
                                 @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

            jsonResult.setMessage("获取当日放弃排队成功");
            jsonResult.setStatus_code(200);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 分页查询当日排队
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/totalQueue")
    public JsonResult totalQueue(Principal principal,
                                 @RequestParam(value = "page") int page,
                                 @RequestParam(value = "size") int size,
                                 @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");



            jsonResult.setMessage("获取当日排队成功");
            jsonResult.setStatus_code(200);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 分页查询当日会话
     * TODO: 根据当前用户角色，返回相关数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/totalThread")
    public JsonResult totalThread(Principal principal,
                                  @RequestParam(value = "page") int page,
                                  @RequestParam(value = "size") int size,
                                  @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");


            jsonResult.setMessage("获取当日会话成功");
            jsonResult.setStatus_code(200);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 搜索过滤统计数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             //
                             @RequestParam(value = "date") String date,
                             @RequestParam(value = "realName") String realName,
                             @RequestParam(value = "client") String client) {

        logger.info("page {}, size {}, date {}, realName {}, client {}", page, size, date, realName, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                // 构建动态查询条件
                Specification specification = (Specification<Thread>) (root, criteriaQuery, criteriaBuilder) -> {

                    List<Predicate> predicateList = new ArrayList<>();

                    String invalidDate = "Invalid date";
                    if (null != date && !date.equals("")&& !date.equals(invalidDate)) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startAt = new Date();
                        try {
                            startAt = dateFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String timeType = TypeConsts.STATISTIC_TIME_TYPE_DAY;
                        predicateList.add(criteriaBuilder.equal(root.get("date"), startAt));
                        predicateList.add(criteriaBuilder.equal(root.get("timeType"), timeType));
                    }

                    if (null != realName) {
                        predicateList.add(criteriaBuilder.like(root.get("user").get("realName"), "%" + realName + "%"));
                    }

                    // 限定当前管理员
                    predicateList.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("user").get("username"), principal.getName()),
                            criteriaBuilder.equal(root.get("user").get("user").get("username"), principal.getName())
                    ));

                    return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
                };

                Page<StatisticDetail> statisticDetailPage = statisticDetailRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索统计详情成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(statisticDetailPage);


            }  else {

                jsonResult.setMessage("管理员用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }















}




