package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * 问卷
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


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


        return jsonResult;
    }




}
