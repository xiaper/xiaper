package io.xiaper.rest.controller.v1;

import io.swagger.annotations.Api;
import io.xiaper.jpa.model.Block;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.repository.BlockRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

/**
 * 黑名单
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/block")
@Api(value="黑名单", tags="黑名单接口", produces = "application/json;charset=utf-8")
public class BlockController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlockRepository blockRepository;

    /**
     * 获取拉黑的用户
     *
     * @param principal
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

            Optional<User> agentOptional = userRepository.findByUsername(principal.getName());
            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            // 查找
            Page<Block> blockPage = blockRepository.findByUser(agentOptional.get(), pageable);

            // 返回结果
            jsonResult.setMessage("查询拉黑用户成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(blockPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 添加拉黑
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public JsonResult add(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String type = (String) map.get("type");
        String note = (String) map.get("note");
        String client = (String) map.get("client");

        // TODO: 添加拉黑时长到日期之后自动取消拉黑，或者永久拉黑

        //
        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> agentOptional = userRepository.findByUsername(principal.getName());
            Optional<User> visitorOptional = userRepository.findByUid(uid);

            if (agentOptional.get().getUid().equals(visitorOptional.get().getUid())) {

                jsonResult.setMessage("拉黑失败-不能拉黑自己");
                jsonResult.setStatus_code(-1);
                jsonResult.setData("failed");

                return jsonResult;
            }

            Optional<Block> blockOptional = blockRepository.findByUserAndBlockedUser(agentOptional.get(), visitorOptional.get());
            if (blockOptional.isPresent()) {

                jsonResult.setMessage("拉黑失败-不需要重复拉黑");
                jsonResult.setStatus_code(-1);
                jsonResult.setData("failed");

                return jsonResult;
            }

            // TODO: 拉黑用户的同时，自动关闭会话，并发布通知

            //
            Block block = new Block();
            block.setBid(JpaUtil.randomId());
            block.setType(type);
            block.setNote(note);
            block.setBlockedUser(visitorOptional.get());
            block.setUser(agentOptional.get());
            blockRepository.save(block);

            // 返回结果
            jsonResult.setMessage("拉黑成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(block);

        } else {

            jsonResult.setMessage("拉黑失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 取消拉黑
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/remove")
    @ResponseBody
    public JsonResult remove(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> agentOptional = userRepository.findByUsername(principal.getName());
            Optional<User> visitorOptional = userRepository.findByUid(uid);

            //
            if (visitorOptional.isPresent()) {

                //
                Optional<Block> blockOptional = blockRepository.findByUserAndBlockedUser(agentOptional.get(), visitorOptional.get());
                if (blockOptional.isPresent()) {

                    //
                    blockRepository.delete(blockOptional.get());

                    // 返回结果
                    jsonResult.setMessage("取消拉黑成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(uid);

                } else {

                    jsonResult.setMessage("取消拉黑失败-非黑名单用户");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData("failed");
                }

            } else {

                jsonResult.setMessage("取消拉黑失败-uid不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData("failed");
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }







}







