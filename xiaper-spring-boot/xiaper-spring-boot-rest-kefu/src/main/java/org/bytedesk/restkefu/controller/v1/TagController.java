package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.model.Tag;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.repository.TagRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * 标签
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    /**
     * 请求机器人分类，
     * 注：用标签代替分类
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult tags(Principal principal,
                           @RequestParam(value = "robot") boolean robot) {

        Optional<User> userOptional = userRepository.findByUsername(principal.getName());

        JsonResult jsonResult = new JsonResult();

        if (userOptional.isPresent()) {

            jsonResult.setMessage("获取标签成功");
            jsonResult.setStatus_code(200);

            List<Tag> tagList = tagRepository.findByUserAndRobot(userOptional.get(), robot);

            jsonResult.setData(tagList);

        } else {

            jsonResult.setMessage("获取标签失败");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 创建机器人类别
     *
     * @param principal
     * @param name
     * @param robot
     * @return
     */
    @GetMapping("/create")
    public JsonResult tagCreate(Principal principal,
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "robot") boolean robot) {

        Optional<User> userOptional = userRepository.findByUsername(principal.getName());

        JsonResult jsonResult = new JsonResult();

        if (userOptional.isPresent()) {

            jsonResult.setMessage("创建标签成功");
            jsonResult.setStatus_code(200);

            Tag tag = new Tag();
            tag.setName(name);
            tag.setRobot(robot);
            tag.setUser(userOptional.get());
            tagRepository.save(tag);

            jsonResult.setData(tag);

        } else {

            jsonResult.setMessage("创建标签失败");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 给某个用户贴标签
     *
     * @param principal
     * @param username
     * @param name
     * @param robot
     * @return
     */
    @GetMapping("/make")
    public JsonResult tagMake(Principal principal,
                                @RequestParam(value = "username") String username,
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "robot") boolean robot) {

        Optional<User> userOptional = userRepository.findByUsername(principal.getName());

        JsonResult jsonResult = new JsonResult();

        if (userOptional.isPresent()) {

            jsonResult.setMessage("标签成功");
            jsonResult.setStatus_code(200);

            Tag tag = new Tag();
            tag.setName(name);
            tag.setRobot(robot);
            tag.setUser(userOptional.get());
            tagRepository.save(tag);

            jsonResult.setData(tag);

        } else {

            jsonResult.setMessage("创建标签失败");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 删除标签
     *
     * @param principal
     * @param tagId
     * @return
     */
    @GetMapping("/delete")
    public JsonResult tagDelete(Principal principal,
                                @RequestParam(value = "id") Long tagId) {

        Optional<User> userOptional = userRepository.findByUsername(principal.getName());

        JsonResult jsonResult = new JsonResult();

        if (userOptional.isPresent()) {

            jsonResult.setMessage("删除标签成功");
            jsonResult.setStatus_code(200);

            tagRepository.deleteById(tagId);

            jsonResult.setData(tagId);

        } else {

            jsonResult.setMessage("删除标签失败");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 请求用户标签
     *
     * @param principal
     * @return
     */
    @GetMapping("/user")
    public JsonResult user(Principal principal,
                           @RequestParam(value = "username") String username) {

        JsonResult jsonResult = new JsonResult();


        return jsonResult;
    }






}
