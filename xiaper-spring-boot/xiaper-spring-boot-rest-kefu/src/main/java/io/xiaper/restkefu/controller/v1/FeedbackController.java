package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.util.JsonResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * 意见反馈
 *
 * @author bytedesk.com on 2019/1/30
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    /**
     * 分页获取意见反馈
     *
     * @param principal
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GetMapping("/get")
    public JsonResult getFeedbacks(Principal principal,
                                  @RequestParam(value = "categoryId") Integer categoryId,
                                  @RequestParam(value = "page") int page,
                                  @RequestParam(value = "size") int size,
                                  @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");

            if (categoryId == 0)  {



            } else {

            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 历史反馈，访客端查看自己提交的历史反馈
     *
     * @param principal
     * @param page
     * @param size
     * @param client
     * @return
     */
    @GetMapping("/my")
    public JsonResult getMyFeedbacks(Principal principal,
                                   @RequestParam(value = "page") int page,
                                   @RequestParam(value = "size") int size,
                                   @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");



        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 访客端提交意见反馈
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
     * 客服回复意见反馈
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
     * 管理员删除意见反馈
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
