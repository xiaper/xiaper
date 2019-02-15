package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.constant.StatusConsts;
import io.xiaper.jpa.constant.TypeConsts;
import io.xiaper.jpa.model.Status;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.WorkGroup;
import io.xiaper.jpa.repository.MessageRepository;
import io.xiaper.jpa.repository.StatusRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.repository.WorkGroupRepository;
import io.xiaper.jpa.util.JsonResult;
import org.bytedesk.mq.service.StatusService;
import org.bytedesk.mq.service.redis.RedisConnectService;
import org.bytedesk.mq.service.redis.RedisService;
import io.xiaper.rest.controller.v1.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * 手动改变 在线状态 接口
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/status")
public class StatusController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    WorkGroupRepository workgroupRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RedisService redisService;

    @Autowired
    StatusService statusService;

    @Autowired
    RedisConnectService redisConnectService;

    /**
     * 分页获取状态记录
     *
     * @param principal
     * @return
     */
    @GetMapping("/request")
    public JsonResult request(Principal principal,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                //
                Page<Status> statusPage = statusRepository.findByUserOrUser_User(adminOptional.get(), adminOptional.get(), pageable);

                //
                jsonResult.setMessage("获取状态成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(statusPage);

            } else {

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


    /**
     * 设置接待状态
     *
     * @param principal
     * @return
     */
    @PostMapping("/set")
    @ResponseBody
    public JsonResult set(Principal principal, @RequestBody Map map) {

        String status = (String) map.get("status");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 保存接待状态
                Status statusObject = new Status();
                statusObject.setClient(client);
                statusObject.setStatus(status);
                statusObject.setUser(adminOptional.get());
                statusRepository.save(statusObject);

                if (status.equals(StatusConsts.USER_STATUS_ONLINE)) {
                    // TODO:  更新redis中状态ZSets, 如果已经存在其中，则不变，如果不存在，则从中删除
                    Iterator iterator = adminOptional.get().getWorkGroups().iterator();
                    while (iterator.hasNext()) {
                        WorkGroup workGroup = (WorkGroup) iterator.next();
                        boolean isExist = redisService.isRoundRobinAgentExist(workGroup.getWid(), adminOptional.get().getUid());
                        if (!isExist) {
                            redisService.addRoundRobinAgent(workGroup.getWid(), adminOptional.get().getUid());
                        }
                    }
                } else {
                    // 删除redis中状态ZSets
                    Iterator iterator = adminOptional.get().getWorkGroups().iterator();
                    while (iterator.hasNext()) {
                        WorkGroup workGroup = (WorkGroup) iterator.next();
                        redisService.removeRoundRobinAgent(workGroup.getWid(), adminOptional.get().getUid());
                    }
                }

                // TODO: 设置客服在线状态
                adminOptional.get().setAcceptStatus(status);
                userRepository.save(adminOptional.get());

                // TODO: 通知公司所有同事
                statusService.notifyAcceptStatus(adminOptional.get(), client, status);

                // 返回结果
                jsonResult.setMessage("设置接待状态成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(statusObject);

            } else {

                jsonResult.setMessage("设置接待状态-管理员账号不存在");
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
     * 设置连接状态client
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/connect")
    @ResponseBody
    public JsonResult connect(Principal principal, @RequestBody Map map) {

        String sessionId = (String) map.get("sessionId");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                Optional<Status> statusOptional = statusRepository.findByStatusAndSessionIdAndClientAndUser(StatusConsts.USER_STATUS_CONNECTED,
                        sessionId, null, userOptional.get());
                if (statusOptional.isPresent()) {
                    statusOptional.get().setClient(client);
                    statusRepository.save(statusOptional.get());

                    // 返回结果
                    jsonResult.setMessage("设置连接状态成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(statusOptional.get());

                } else {

                    // 返回结果
                    jsonResult.setMessage("设置连接状态失败-status不存在");
                    jsonResult.setStatus_code(-3);
                }

            } else {

                jsonResult.setMessage("设置连接-管理员账号不存在");
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
     * 设置断开连接状态client
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/disconnect")
    @ResponseBody
    public JsonResult disconnect(Principal principal, @RequestBody Map map) {

        String sessionId = (String) map.get("sessionId");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {


                Optional<Status> statusOptional = statusRepository.findByStatusAndSessionIdAndClientAndUser(StatusConsts.USER_STATUS_DISCONNECTED,
                        sessionId, null, userOptional.get());
                if (statusOptional.isPresent()) {
                    statusOptional.get().setClient(client);
                    statusRepository.save(statusOptional.get());

                    // 返回结果
                    jsonResult.setMessage("设置断开连接状态成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(statusOptional.get());

                } else {

                    // 返回结果
                    jsonResult.setMessage("设置断开连接状态失败-status不存在");
                    jsonResult.setStatus_code(-3);
                }

            } else {

                jsonResult.setMessage("设置断开连接-管理员账号不存在");
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
     * 获取客服当前在线状态
     *
     * @param principal
     * @return
     */
    @GetMapping("/agent")
    public JsonResult agent(Principal principal,
                            @RequestParam(value = "uid") String uid,
                            @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<User> agentOptional = userRepository.findByUid(uid);
                if (agentOptional.isPresent()) {

                    Map<String, Object> objectMap = new HashMap<>(2);
                    objectMap.put("uid", uid);
                    objectMap.put("status", redisConnectService.isConnectedAgent(uid) ? "online" : "offline");

                    jsonResult.setMessage("获取客服当前在线状态成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(objectMap);

                } else {

                    //
                    jsonResult.setMessage("获取客服当前在线状态-客服账号不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("获取客服当前在线状态-访客账号不存在");
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
     * 获取工作组当前在线状态
     * TODO: 工作组内如果有一个客服在线，则为online，否则为offline
     *
     * @param principal
     * @return
     */
    @GetMapping("/workGroup")
    public JsonResult workGroup(Principal principal,
                                @RequestParam(value = "wid") String wid,
                                @RequestParam(value = "client") String client) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                //
                Optional<WorkGroup> workGroupOptional = workgroupRepository.findByWid(wid);
                if (workGroupOptional.isPresent()) {

                    Map<String, Object> objectMap = new HashMap<>(2);
                    objectMap.put("wid", wid);
                    objectMap.put("status", redisService.hasRoundRobinAgentOnline(workGroupOptional.get().getWid()) ? "online" : "offline");

                    jsonResult.setMessage("获取工作组当前在线状态成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(objectMap);

                } else {

                    //
                    jsonResult.setMessage("获取工作组当前在线状态-工作组不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("获取工作组当前在线状态-访客账号不存在");
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
     * 搜索过滤在线状态
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             //
                             @RequestParam(value = "createdAtStart") String createdAtStart,
                             @RequestParam(value = "createdAtEnd") String createdAtEnd,
                             @RequestParam(value = "agentRealName") String agentRealName,
                             @RequestParam(value = "client") String client) {

        logger.info("page {}, size {}, createdAtStart {},  createdAtEnd {},  agentRealName {}, client {}",
                page, size, createdAtStart, createdAtEnd, agentRealName, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {
                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                Specification specification = getSpecification(adminOptional.get(), TypeConsts.SPECIFICATION_TYPE_STATUS, "", createdAtStart, createdAtEnd, "",
                        agentRealName, client);
                Page<Status> statusPage = statusRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索在线状态成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(statusPage);
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


