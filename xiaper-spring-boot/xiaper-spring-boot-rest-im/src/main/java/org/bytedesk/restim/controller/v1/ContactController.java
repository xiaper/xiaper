package org.bytedesk.restim.controller.v1;


import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.rest.controller.v1.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * 联系人相关接口
 *
 * @author bytedesk.com on 2018/11/26
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController extends BaseController {

    @Autowired
    UserRepository userRepository;

    /**
     * 查询联系人
     * TODO
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {




            } else {

                jsonResult.setMessage("查询联系人失败-账号不存在");
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
