package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.model.User;
import io.xiaper.jpa.repository.NoticeRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JsonResult;
import io.xiaper.rest.controller.v1.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * 通知接口
 *
 * @author bytedesk.com on 2018/11/26
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoticeRepository noticeRepository;

    /**
     * 分页加载通知
     *
     * @param principal
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size,
                          @RequestParam(value = "client") String client) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {





            } else {

                jsonResult.setMessage("查询通知失败-账号不存在");
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
     * 查看通知详情，同时设置通知未已读
     *
     * @param principal
     * @param nid
     * @param client
     * @return
     */
    @GetMapping("/detail")
    public JsonResult detail(Principal principal,
                             @RequestParam(value = "nid") String nid,
                             @RequestParam(value = "client") String client) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

        } else {

            jsonResult.setMessage("接入队列失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }












}
