package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.model.User;
import io.xiaper.jpa.repository.CommentRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

/**
 * 帮助中心Support：评价
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    /**
     * 获取评论
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult getCategories(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {




            } else {

                jsonResult.setMessage("获取帮助文档评论-管理员账号不存在");
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
     * 创建帮助文档评论
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {




            } else {

                jsonResult.setMessage("创建帮助文档评论-管理员账号不存在");
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
     * 更新帮助文档评论
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {




            } else {

                jsonResult.setMessage("更新帮助文档评论-管理员账号不存在");
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
     * 删除帮助文档 评论
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {



            } else {

                jsonResult.setMessage("删除帮助文档评论-管理员账号不存在");
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
