package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.model.Synonym;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.repository.SynonymRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.rest.controller.v1.BaseController;
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
 * 机器人、智能客服: 相似词
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/synonym")
public class SynonymController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SynonymRepository synonymRepository;

    /**
     * 获取相似词
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size,
                          @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
                Page<Synonym> synonymPage = synonymRepository.findByUserAndSynonym(adminOptional.get(), false, pageable);

                // 返回结果
                jsonResult.setMessage("获取相似词成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(synonymPage);

            } else {

                jsonResult.setMessage("获取相似词失败-用户不存在");
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
     * 创建相似词
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

            // 标准词
            Synonym synonym = new Synonym();
            synonym.setSid(JpaUtil.randomId());
            synonym.setStandard(standard);
            synonym.setSynonym(false);
            synonym.setUser(adminOptional.get());

            // 相似词
            Iterator iterator = synonyms.iterator();
            while (iterator.hasNext()) {
                String syn = (String) iterator.next();
                //
                Synonym synonym1 = new Synonym();
                synonym1.setSid(JpaUtil.randomId());
                synonym1.setStandard(syn);
                synonym1.setSynonym(true);
                synonym1.setUser(adminOptional.get());
                synonymRepository.save(synonym1);
                //
                synonym.getSynonyms().add(synonym1);
            }
            synonymRepository.save(synonym);

            //
            jsonResult.setMessage("创建相似词成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(synonym);


        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }



        return jsonResult;
    }

    /**
     * 更新
     *
     * @param principal
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        String sid = (String) map.get("sid");
        String standard = (String) map.get("standard");
        List<String> synonyms = (List<String>) map.get("synonyms");
        logger.info("standard {}, synonyms {}", standard, synonyms);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

            //
            Optional<Synonym> synonymOptional = synonymRepository.findBySid(sid);
            if (synonymOptional.isPresent()) {

                // 更新标准词
                synonymOptional.get().setStandard(standard);

                // 删除原先的相似词
                Iterator iterator = synonymOptional.get().getSynonyms().iterator();
                while (iterator.hasNext()) {
                    Synonym syn = (Synonym) iterator.next();
                    synonymRepository.delete(syn);
                    iterator.remove();
                }

                // 添加新的相似词
                Iterator iterator1 = synonyms.iterator();
                while (iterator1.hasNext()) {
                    String syn = (String) iterator1.next();
                    //
                    Synonym synonymSyn = new Synonym();
                    synonymSyn.setSid(JpaUtil.randomId());
                    synonymSyn.setStandard(syn);
                    synonymSyn.setSynonym(true);
                    synonymSyn.setUser(adminOptional.get());
                    synonymOptional.get().getSynonyms().add(synonymSyn);
                }
                synonymRepository.save(synonymOptional.get());

                // 返回结果
                jsonResult.setMessage("更新相似词成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(synonymOptional.get());

            } else {

                jsonResult.setMessage("更新相似词失败-sid不存在");
                jsonResult.setStatus_code(-3);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("更新相似词失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 删除
     *
     * @param principal
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        String sid = (String) map.get("sid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Synonym> synonymOptional = synonymRepository.findBySid(sid);

            // 删除原先的相似词
            Iterator iterator = synonymOptional.get().getSynonyms().iterator();
            while (iterator.hasNext()) {
                Synonym syn = (Synonym) iterator.next();
                synonymRepository.delete(syn);
                iterator.remove();
            }
            //
            synonymRepository.delete(synonymOptional.get());

            // 返回结果
            jsonResult.setMessage("删除相似词成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(sid);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }




}
