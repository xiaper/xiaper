package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.util.JsonResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Map;

/**
 * @author bytedesk.com on 2018/12/24
 */
@RestController
@Configuration
@RequestMapping("/api/ticket")
public class TicketController {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * http://localhost:8000/ticket/test
     *
     * @return
     */
    @GetMapping("/test")
    public String ticket() {
        RestTemplate restTemplate = getRestTemplate();
        //
        String ticket = restTemplate.getForObject("http://ticketservice/api/process/hello", String.class);
        return ticket;
    }


    /**
     * 分页查询工单列表
     *
     * @param principal
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "type") String type,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size,
                          @RequestParam(value = "client") String client) {
        //
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





}
