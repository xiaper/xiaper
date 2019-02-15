package io.xiaper.restkefu.controller.v1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.xiaper.jpa.constant.BdConstants;
import io.xiaper.jpa.model.App;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.WorkGroup;
import io.xiaper.jpa.model.WorkTime;
import io.xiaper.jpa.repository.AppRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.repository.WorkGroupRepository;
import io.xiaper.jpa.repository.WorkTimeRepository;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作组管理
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/workgroup")
public class WorkGroupController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkGroupRepository workGroupRepository;

    @Autowired
    WorkTimeRepository workTimeRepository;

    @Autowired
    AppRepository appRepository;

    /**
     * 管理员账号拥有的工作组 或者 客服账号所在的工作组
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                // 工作组
                Page<WorkGroup> workGroupPage;
                //
                if (userOptional.get().isAdmin()) {

                    workGroupPage = workGroupRepository.findByUser(userOptional.get(), pageable);

                } else {

                    User admin = userOptional.get().getAdmin();
                    workGroupPage = workGroupRepository.findByUser(admin, pageable);
                }

                // 返回结果
                jsonResult.setMessage("获取工作组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(workGroupPage);

            } else {

                jsonResult.setMessage("用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 创建工作组
     *
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        String nickname = (String) map.get("nickname");
        String avatar = (String) map.get("avatar");
        boolean defaultRobot = (boolean) map.get("defaultRobot");
        boolean offlineRobot = (boolean) map.get("offlineRobot");
        boolean forceRate = (boolean) map.get("forceRate");
        String routeType = (String) map.get("routeType");
        String welcomeTip = (String) map.get("welcomeTip");
        String acceptTip = (String) map.get("acceptTip");
        String offlineTip = (String) map.get("offlineTip");
        String workTimes = (String) map.get("workTimes");
        // 用户uid
        List<String> users = (List<String>) map.get("users");
        String onDutyWorkGroupWid = (String) map.get("onDutyWorkGroupWid");
        String adminUid = (String) map.get("admin");
        //
        logger.info("nickname {}, routeType {}, welcomeTip {}, acceptTip {}, offlineTip {}, workTimes {}, users {}, onDutyWorkGroupWid {}, admin {}",
                nickname, routeType, welcomeTip, acceptTip, offlineTip, workTimes, users, onDutyWorkGroupWid, adminUid);

        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            //
            Optional<App> appOptional = appRepository.findFirstByUser(adminOptional.get());
            Optional<WorkGroup> workGroupOptional = workGroupRepository.findByUserAndNickname(adminOptional.get(), nickname);
            if (!workGroupOptional.isPresent())  {

                // 创建工作组
                WorkGroup workGroup = new WorkGroup();
                workGroup.setWid(JpaUtil.randomId());
                workGroup.setNickname(nickname);
                workGroup.setAvatar(avatar);
                workGroup.setDefaultRobot(defaultRobot);
                workGroup.setOfflineRobot(offlineRobot);
                workGroup.setForceRate(forceRate);
                workGroup.setRouteType(routeType);
                workGroup.setDepartment(false);
                workGroup.setWelcomeTip(welcomeTip);
                workGroup.setAcceptTip(acceptTip);
                workGroup.setOfflineTip(offlineTip);
                workGroup.setNonWorkingTimeTip(BdConstants.DEFAULT_WORK_GROUP_NON_WORKING_TIME_TIP);
                workGroup.setUser(adminOptional.get());
                workGroup.getApps().add(appOptional.get());

                if (onDutyWorkGroupWid != null && onDutyWorkGroupWid.trim().length() > 0) {
                    Optional<WorkGroup>  onDutyWorkGroupOptional = workGroupRepository.findByWid(onDutyWorkGroupWid);
                    if (onDutyWorkGroupOptional.isPresent()) {
                        workGroup.setOnDutyWorkGroup(onDutyWorkGroupOptional.get());
                    }
                } else {
                    workGroup.setOnDutyWorkGroup(null);
                }

                // 工作时间段
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                JSONArray jsonArray = JSONArray.parseArray(workTimes);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String startTime = (String) jsonObject.get("startTime");
                    String endTime = (String) jsonObject.get("endTime");
                    //
                    WorkTime workTime = new WorkTime();
                    try {
                        workTime.setStartTime(dateFormat.parse(startTime));
                        workTime.setEndTime(dateFormat.parse(endTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    workGroup.getWorkTimes().add(workTime);
                }

                //
                Optional<User> workGroupAdminOptional = userRepository.findByUid(adminUid);
                if (workGroupAdminOptional.isPresent()) {
                    workGroup.setAdmin(workGroupAdminOptional.get());
                }

                // 用户
                Iterator usersIterator =  users.iterator();
                while (usersIterator.hasNext()) {
                    String uid = (String) usersIterator.next();
                    //
                    Optional<User> userOptional = userRepository.findByUid(uid);
                    if (userOptional.isPresent()) {
                        //
//                        userOptional.get().getWorkGroups().add(workGroup);
//                        userRepository.save(userOptional.get());
                        //
                        workGroup.getUsers().add(userOptional.get());
                    }
                }
                workGroupRepository.save(workGroup);

                jsonResult.setMessage("工作组创建成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(workGroup);

            } else {

                jsonResult.setMessage("工作组昵称已经存在");
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


    /**
     * 更新工作组
     *
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        Integer id = (Integer) map.get("id");
        String nickname = (String) map.get("nickname");
        String avatar = (String) map.get("avatar");
        boolean defaultRobot = (boolean) map.get("defaultRobot");
        boolean offlineRobot = (boolean) map.get("offlineRobot");
        boolean forceRate = (boolean) map.get("forceRate");
        String routeType = (String) map.get("routeType");
        String welcomeTip = (String) map.get("welcomeTip");
        String acceptTip = (String) map.get("acceptTip");
        String offlineTip = (String) map.get("offlineTip");
        String workTimes = (String) map.get("workTimes");
        // 用户uid
        List<String> users = (List<String>) map.get("users");
        String onDutyWorkGroupWid = (String) map.get("onDutyWorkGroupWid");
        String adminUid = (String) map.get("admin");
        //
        logger.info("nickname {}, routeType {}, welcomeTip {}, acceptTip {}, offlineTip {}, workTimes {}, users {}, onDutyWorkGroupWid {}, admin {}",
                nickname, routeType, welcomeTip, acceptTip, offlineTip, workTimes, users, onDutyWorkGroupWid, adminUid);

        JsonResult jsonResult = new JsonResult();

        // 验证管理员
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<WorkGroup> workGroupOptional = workGroupRepository.findById(Long.valueOf(id));
            if (workGroupOptional.isPresent()) {

                WorkGroup workGroup = workGroupOptional.get();
                workGroup.setNickname(nickname);
                workGroup.setAvatar(avatar);
                workGroup.setDefaultRobot(defaultRobot);
                workGroup.setOfflineRobot(offlineRobot);
                workGroup.setForceRate(forceRate);
                workGroup.setRouteType(routeType);
                workGroup.setDepartment(false);
                workGroup.setWelcomeTip(welcomeTip);
                workGroup.setAcceptTip(acceptTip);
                workGroup.setOfflineTip(offlineTip);

                if (onDutyWorkGroupWid != null && onDutyWorkGroupWid.trim().length() > 0) {
                    Optional<WorkGroup>  onDutyWorkGroupOptional = workGroupRepository.findByWid(onDutyWorkGroupWid);
                    if (onDutyWorkGroupOptional.isPresent()) {
                        workGroup.setOnDutyWorkGroup(onDutyWorkGroupOptional.get());
                    }
                } else {
                    workGroup.setOnDutyWorkGroup(null);
                }

                // 工作时间段
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                JSONArray jsonArray = JSONArray.parseArray(workTimes);
                Set<WorkTime> workTimeSet = new HashSet<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String startTime = (String) jsonObject.get("startTime");
                    String endTime = (String) jsonObject.get("endTime");
                    //
                    WorkTime workTime = new WorkTime();
                    try {
                        workTime.setStartTime(dateFormat.parse(startTime));
                        workTime.setEndTime(dateFormat.parse(endTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    workTimeSet.add(workTime);
                }
                workGroup.setWorkTimes(workTimeSet);

                //
                Optional<User> workGroupAdminOptional = userRepository.findByUid(adminUid);
                if (workGroupAdminOptional.isPresent()) {
                    workGroup.setAdmin(workGroupAdminOptional.get());
                }

                // 用户
                // 清空原先的
                workGroup.getUsers().clear();
                // 新添加的
                Iterator usersIterator =  users.iterator();
                while (usersIterator.hasNext()) {
                    String uid = (String) usersIterator.next();
                    //
                    Optional<User> userOptional = userRepository.findByUid(uid);
                    if (userOptional.isPresent()) {
                        //
                        userOptional.get().getWorkGroups().add(workGroup);
                        userRepository.save(userOptional.get());
                        //
                        workGroup.getUsers().add(userOptional.get());
                    }
                }
                workGroupRepository.save(workGroup);


                jsonResult.setMessage("工作组更新成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(workGroup);

            } else {

                jsonResult.setMessage("工作组不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(id);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 删除工作组
     *
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        Integer id = (Integer) map.get("id");

        JsonResult jsonResult = new JsonResult();

        logger.info("delete workgroup id {}", id);

        // 验证管理员
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<WorkGroup> workGroup = workGroupRepository.findById(Long.valueOf(id));
            if (workGroup.isPresent()) {

                // 删除
                workGroupRepository.deleteById(Long.valueOf(id));

                //
                jsonResult.setMessage("删除工作组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(id);

            } else {

                jsonResult.setMessage("删除工作组失败-id不存在 ");
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

    /**
     * 1. 管理员账号拥有的所有工作组
     * TODO: 2. 客服组长、客服账号 所在工作组
     *
     * @param principal
     * @return
     */
    @GetMapping("/all")
    public JsonResult all(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {
            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {
                //
                List<WorkGroup> workGroups;
                //
                if (userOptional.get().isAdmin()) {
                    // 注册管理员
                    workGroups = workGroupRepository.findByUser(userOptional.get());
                } else if (userOptional.get().isWorkGroupAdmin()) {
                    // 客服组长
                    workGroups = workGroupRepository.findByUsersContains(userOptional.get());
                } else {
                    // 客服账号
                    workGroups = workGroupRepository.findByUsersContains(userOptional.get());
                }
                // 返回结果
                jsonResult.setMessage("获取所有工作组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(workGroups);

            } else {

                jsonResult.setMessage("用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }

    /**
     * 搜索过滤工作组
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             //
                             @RequestParam(value = "nickname") String nickname,
                             @RequestParam(value = "client") String client) {

        logger.info(" nickname {}, client {}", nickname, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

            // 构建动态查询条件
            Specification specification = (Specification<WorkGroup>) (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicateList = new ArrayList<>();

                predicateList.add(criteriaBuilder.like(root.get("nickname"), "%" + nickname + "%"));

                // 有可能查到其他企业的工作组账号
                predicateList.add(criteriaBuilder.equal(root.get("user").get("subDomain"), adminOptional.get().getSubDomain()));

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            };

            Page<WorkGroup> workGroupPage = workGroupRepository.findAll(specification, pageable);

            // 返回结果
            jsonResult.setMessage("搜索工作组成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(workGroupPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


}
