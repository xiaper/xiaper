package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.model.Category;
import org.bytedesk.jpa.model.Cuw;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.repository.CategoryRepository;
import org.bytedesk.jpa.repository.CuwRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * 常用语：树形结构，最多二级: 一级类别，二级具体条目
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/cuw")
public class CuwController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CuwRepository cuwRepository;

    /**
     * 获取常用语
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 客服
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());

            // TODO: 获取platform类型
            Set<Category> platformCategorySet = categoryRepository.findByType(TypeConsts.CATEGORY_TYPE_CUW_PLATFORM);
            Iterator platformCategoryIterator = platformCategorySet.iterator();
            while (platformCategoryIterator.hasNext()) {
                Category category = (Category) platformCategoryIterator.next();
                Set<Cuw> cuwSet = cuwRepository.findByCategory(category);
                category.setCuwChildren(cuwSet);
            }

            // TODO: 获取公司类型
            User admin = userOptional.get().getAdmin();
            Set<Category> companyCategorySet = categoryRepository.findByTypeAndUser(TypeConsts.CATEGORY_TYPE_CUW_COMPANY, admin);
            Iterator companyCategoryIterator = companyCategorySet.iterator();
            while (companyCategoryIterator.hasNext()) {
                Category category = (Category) companyCategoryIterator.next();
                Set<Cuw> cuwSet = cuwRepository.findByCategory(category);
                category.setCuwChildren(cuwSet);
            }

            // TODO: 获取客服自己创建分组 以及 分组内常用语
            Set<Category> mineCategorySet = categoryRepository.findByTypeAndUser(TypeConsts.CATEGORY_TYPE_CUW_MINE, userOptional.get());
            Iterator mineCategoryIterator = mineCategorySet.iterator();
            while (mineCategoryIterator.hasNext()) {
                Category category = (Category) mineCategoryIterator.next();
                Set<Cuw> cuwSet = cuwRepository.findByCategory(category);
                category.setCuwChildren(cuwSet);
            }

            // 结果
            Map<String, Object> objectMap = new HashMap<>(3);
            objectMap.put("platform", platformCategorySet);
            objectMap.put("company", companyCategorySet);
            objectMap.put("mine", mineCategorySet);

            // 返回结果
            jsonResult.setMessage("获取常用语成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("获取常用语失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 获取公司类型常用语
     *
     * @param principal
     * @return
     */
    @GetMapping("/company")
    public JsonResult company(Principal principal,
                              @RequestParam(value = "categoryId") Integer categoryId,
                              @RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size) {


        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");

                if (categoryId == 0)  {

                    // 获取常用语
                    Set<Category> companyCategorySet = categoryRepository.findByTypeAndUser(TypeConsts.CATEGORY_TYPE_CUW_COMPANY, adminOptional.get());
                    Iterator companyCategoryIterator = companyCategorySet.iterator();
                    while (companyCategoryIterator.hasNext()) {
                        Category category = (Category) companyCategoryIterator.next();
                        Set<Cuw> cuwSet = cuwRepository.findByCategory(category);
                        category.setCuwChildren(cuwSet);
                    }

                    // 返回结果
                    jsonResult.setMessage("获取公司类型常用语成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(companyCategorySet);

                } else {

                }

            } else {

                jsonResult.setMessage("获取公司类型常用语失败-管理员不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("获取公司类型常用语失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 创建常用语
     *
     * @param principal
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        Integer categoryId = (Integer) map.get("categoryId");
        String name = (String) map.get("name");
        String content = (String) map.get("content");
        logger.info("create: categoryId {}, name {}, content {}", categoryId, name, content);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 创建者
            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                Cuw cuw = new Cuw();
                cuw.setCid(JpaUtil.randomId());
                cuw.setName(name);
                cuw.setContent(content);

                // 类别/分组
                Optional<Category> categoryOptional = categoryRepository.findById(Long.valueOf(categoryId));
                if (categoryOptional.isPresent()) {
                    cuw.setCategory(categoryOptional.get());
                }
                cuw.setUser(userOptional.get());

                // 持久化保存
                cuwRepository.save(cuw);

                //
                Map<String, Object> objectMap = new HashMap<>(2);
                objectMap.put("cuw", cuw);
                objectMap.put("category", cuw.getCategory());

                // 返回结果
                jsonResult.setMessage("创建常用语成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(objectMap);

            } else {

                jsonResult.setMessage("创建常用语失败-用户名不存在");
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
     * 更新常用语
     *
     * @param principal
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        Integer id = (Integer) map.get("id");
        String name = (String) map.get("name");
        String content = (String) map.get("content");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<Cuw> cuwOptional = cuwRepository.findById(Long.valueOf(id));
            if (cuwOptional.isPresent()) {

                cuwOptional.get().setName(name);
                cuwOptional.get().setContent(content);
                cuwRepository.save(cuwOptional.get());

                //
                Map<String, Object> objectMap = new HashMap<>(2);
                objectMap.put("cuw", cuwOptional.get());
                objectMap.put("category", cuwOptional.get().getCategory());

                // 返回结果
                jsonResult.setMessage("更新常用语成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(objectMap);

            } else {

                // 返回结果
                jsonResult.setMessage("更新常用语失败");
                jsonResult.setStatus_code(-1);
                jsonResult.setData(id);
            }

        } else {

            jsonResult.setMessage("更新常用语失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 删除常用语
     *
     * @param principal
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        Integer id = (Integer) map.get("id");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<Cuw> cuwOptional = cuwRepository.findById(Long.valueOf(id));
            if (cuwOptional.isPresent()) {
                cuwRepository.delete(cuwOptional.get());
            }

            //
            Map<String, Object> objectMap = new HashMap<>(2);
            objectMap.put("cuw", cuwOptional.get());
            objectMap.put("category", cuwOptional.get().getCategory());

            //
            jsonResult.setMessage("删除常用语成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("删除常用语失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }




}
