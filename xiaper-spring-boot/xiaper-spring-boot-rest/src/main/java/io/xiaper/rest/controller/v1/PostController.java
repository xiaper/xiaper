package io.xiaper.rest.controller.v1;

import io.xiaper.jpa.model.User;
import io.xiaper.jpa.repository.PostRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

/**
 * 论坛帖子：Community
 *
 * @author bytedesk.com on 2019/1/23
 */
@RestController
@RequestMapping("/api/post")
public class PostController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    /**
     * 分页加载帖子
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

                jsonResult.setMessage("论坛帖子加载失败-账号不存在");
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
     * 创建
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



        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 更新
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



        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }



    /**
     * 删除
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



        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     *
     * @param principal
     * @param page
     * @param size
     * @param content
     * @param client
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             @RequestParam(value = "content") int content,
                             @RequestParam(value = "client") String client) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {



            } else {

                jsonResult.setMessage("论坛帖子搜索失败-账号不存在");
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









}
