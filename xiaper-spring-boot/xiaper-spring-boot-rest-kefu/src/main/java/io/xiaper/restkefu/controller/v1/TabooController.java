package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.model.Taboo;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.repository.TabooRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import io.xiaper.rest.controller.v1.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 过滤敏感词、敏感图片等
 *
 * @author bytedesk.com on 2018/12/8
 */
@RestController
@RequestMapping("/api/taboo")
public class TabooController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TabooRepository tabooRepository;

    /**
     * 分页查询敏感词
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

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<Taboo> filterList = tabooRepository.findByUserAndSynonym(adminOptional.get(), false, pageable);

            // 返回结果
            jsonResult.setMessage("获取敏感词成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(filterList);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 创建敏感词
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        String standard = (String) map.get("standard");
        List<String> synonyms = (List<String>) map.get("synonyms");
        logger.info("standard {}, synonyms {}", standard, synonyms);


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

            //
            Taboo taboo = new Taboo();
            taboo.setTid(JpaUtil.randomId());
            taboo.setStandard(standard);
            taboo.setSynonym(false);
            taboo.setUser(adminOptional.get());

            //
            Iterator iterator = synonyms.iterator();
            while (iterator.hasNext()) {
                String syn = (String) iterator.next();
                //
                Taboo taboo1 = new Taboo();
                taboo1.setTid(JpaUtil.randomId());
                taboo1.setStandard(syn);
                taboo1.setSynonym(true);
                taboo1.setUser(adminOptional.get());
                tabooRepository.save(taboo1);
                //
                taboo.getTaboos().add(taboo1);
            }
            tabooRepository.save(taboo);

            // 返回结果
            jsonResult.setMessage("创建敏感词成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(taboo);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 更新敏感词
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");
        String standard = (String) map.get("standard");
        List<String> synonyms = (List<String>) map.get("synonyms");
        logger.info("standard {}, synonyms {}", standard, synonyms);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

            //
            Optional<Taboo> tabooOptional = tabooRepository.findByTid(tid);
            if (tabooOptional.isPresent()) {

                //
                tabooOptional.get().setStandard(standard);

                //
                Iterator iterator = tabooOptional.get().getTaboos().iterator();
                while (iterator.hasNext()) {
                    Taboo taboo = (Taboo) iterator.next();
                    tabooRepository.delete(taboo);
                    iterator.remove();
                }

                //
                Iterator iterator1 = synonyms.iterator();
                while (iterator1.hasNext()) {
                    String syn = (String) iterator1.next();
                    //
                    Taboo taboo = new Taboo();
                    taboo.setTid(JpaUtil.randomId());
                    taboo.setStandard(syn);
                    taboo.setSynonym(true);
                    taboo.setUser(adminOptional.get());
                    tabooRepository.save(taboo);

                    //
                    tabooOptional.get().getTaboos().add(taboo);
                }
                tabooRepository.save(tabooOptional.get());

                // 返回结果
                jsonResult.setMessage("更新敏感词成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(tabooOptional.get());

            } else {

                jsonResult.setMessage("更新敏感词失败-tid不存在");
                jsonResult.setStatus_code(-3);
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
     * 删除敏感词
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        String tid = (String) map.get("tid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Taboo> tabooOptional = tabooRepository.findByTid(tid);

            //
            Iterator iterator = tabooOptional.get().getTaboos().iterator();
            while (iterator.hasNext()) {
                Taboo taboo = (Taboo) iterator.next();
                tabooRepository.delete(taboo);
                iterator.remove();
            }

            tabooRepository.delete(tabooOptional.get());

            // 返回结果
            jsonResult.setMessage("删除敏感词成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(tid);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData("failed");
        }

        return jsonResult;
    }


    /**
     * 搜索敏感词
     *
     * @param principal
     * @param nickname
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "nickname") String nickname) {


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

















