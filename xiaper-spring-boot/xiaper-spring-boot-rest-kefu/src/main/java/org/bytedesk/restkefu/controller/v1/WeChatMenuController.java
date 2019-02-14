package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.rest.controller.v1.BaseController;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * 公众号自定义菜单
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/wechat/mp/menu")
public class WeChatMenuController extends BaseController {


    /**
     * 查询自定义菜单
     *
     * @param principal
     * @param client
     * @return
     */
    @GetMapping("/query")
    public JsonResult query(Principal principal,
                            @RequestParam(value = "client") String client) {

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
     * 创建自定义菜单
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        String username = (String) map.get("username");

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
     * 删除自定义菜单
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        String username = (String) map.get("username");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {



        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }


        return jsonResult;
    }






}
