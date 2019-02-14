package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.model.LeaveMessage;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.model.WorkGroup;
import org.bytedesk.jpa.repository.LeaveMessageRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.repository.WorkGroupRepository;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.rest.controller.v1.BaseController;
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
import java.util.Map;
import java.util.Optional;

/**
 * 留言
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/leavemsg")
public class LeaveMessageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LeaveMessageController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    LeaveMessageRepository leaveMessageRepository;

    @Autowired
    WorkGroupRepository workGroupRepository;

    /**
     * 分页获取留言记录
     *
     * FIXME: 管理员账号下面的所有客服账号均可见
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

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                Page<LeaveMessage> leaveMessagePage;

                if (adminOptional.get().isAdmin()) {
                    // 管理员
                    leaveMessagePage = leaveMessageRepository.findByUser(adminOptional.get(), pageable);
                } else if (adminOptional.get().isWorkGroupAdmin()) {
                    // FIXME: 多个工作组
                    // 客服组长
                    WorkGroup workGroup = (WorkGroup) adminOptional.get().getWorkGroups().toArray()[0];
                    leaveMessagePage = leaveMessageRepository.findByWorkGroup(workGroup, pageable);
                } else {
                    // 客服账号
                    leaveMessagePage = leaveMessageRepository.findByAgent(adminOptional.get(), pageable);
                }

                //
                jsonResult.setMessage("获取留言成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(leaveMessagePage);

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
     * 保存留言
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public JsonResult save(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String wid = (String) map.get("wid");
        String aid = (String) map.get("aid");
        String type = (String) map.get("type");
        //
        String mobile = (String) map.get("mobile");
        String email = (String) map.get("email");
        String content = (String) map.get("content");
        String client = (String) map.get("client");

        JsonResult jsonResult = new JsonResult();

        // 检查内容是否为空
        if (null == mobile || mobile.trim().length() == 0
            || null == content || content.trim().length() == 0) {

            jsonResult.setMessage("留言失败-手机号和留言内容不能为空");
            jsonResult.setStatus_code(-4);
            jsonResult.setData(false);

            return jsonResult;
        }

        //
        if (principal != null) {

            // 管理员账号
            Optional<User> adminOptional = userRepository.findByUid(uid);
            if (adminOptional.isPresent()) {

                // 访客账号
                Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
                if (visitorOptional.isPresent()) {

                    // 保存留言
                    LeaveMessage leaveMessage = new LeaveMessage();
                    leaveMessage.setLid(JpaUtil.randomId());
                    leaveMessage.setMobile(mobile);
                    leaveMessage.setEmail(email);
                    leaveMessage.setContent(content);
                    leaveMessage.setType(type);
                    leaveMessage.setVisitor(visitorOptional.get());
                    leaveMessage.setUser(adminOptional.get());

                    if (type.equals(TypeConsts.THREAD_REQUEST_TYPE_APPOINTED)) {
                        // 指定会话留言
                        Optional<User> agentOptional = userRepository.findByUid(aid);
                        if (agentOptional.isPresent()) {
                            leaveMessage.setAgent(agentOptional.get());
                        }
                    } else {
                        // 工作组会话留言
                        Optional<WorkGroup> workGroupOptional = workGroupRepository.findByWid(wid);
                        if (workGroupOptional.isPresent()) {
                            leaveMessage.setWorkGroup(workGroupOptional.get());
                        }
                    }
                    leaveMessageRepository.save(leaveMessage);

                    // 返回结果
                    jsonResult.setMessage("留言成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(leaveMessage);

                } else {

                    jsonResult.setMessage("留言失败-访客用户不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("留言失败-管理员用户不存在");
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
     * 备注留言回复
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/reply")
    @ResponseBody
    public JsonResult reply(Principal principal, @RequestBody Map map) {

        String lid = (String) map.get("lid");
        Boolean replied = (Boolean) map.get("replied");
        String reply = (String) map.get("reply");

        JsonResult jsonResult = new JsonResult();

        //
        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Optional<LeaveMessage> leaveMessageOptional = leaveMessageRepository.findByLid(lid);
                if (leaveMessageOptional.isPresent()) {

                    // 持久化保存
                    leaveMessageOptional.get().setReplied(replied);
                    leaveMessageOptional.get().setReply(reply);
                    leaveMessageRepository.save(leaveMessageOptional.get());

                    // 返回结果
                    jsonResult.setMessage("备注留言回复成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(leaveMessageOptional.get());

                } else {

                    jsonResult.setMessage("备注留言回复失败-管理员用户不存在");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("备注留言回复失败-管理员用户不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("备注留言回复失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 搜索过滤留言
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
                             @RequestParam(value = "createdAtStart") String createdAtStart,
                             @RequestParam(value = "createdAtEnd") String createdAtEnd,
                             @RequestParam(value = "workGroupNickname") String workGroupNickname,
                             @RequestParam(value = "agentRealName") String agentRealName,
                             @RequestParam(value = "client") String client) {

        logger.info("page {}, size {}, nickname {}, createdAtStart {}, createdAtEnd {}, wgNickname {}, agentRealName {}, client {}",
                page, size, nickname, createdAtStart, createdAtEnd, workGroupNickname, agentRealName, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                Specification specification = getSpecification(adminOptional.get(), TypeConsts.SPECIFICATION_TYPE_LEAVE_MESSAGE, nickname, createdAtStart, createdAtEnd,
                        workGroupNickname, agentRealName, client);
                Page<LeaveMessage> leaveMessagePage = leaveMessageRepository.findAll(specification, pageable);

                // 返回结果
                jsonResult.setMessage("搜索留言成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(leaveMessagePage);

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
