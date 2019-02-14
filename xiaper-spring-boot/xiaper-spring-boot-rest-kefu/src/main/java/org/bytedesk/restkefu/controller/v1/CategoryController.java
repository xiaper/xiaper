package org.bytedesk.restkefu.controller.v1;

import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.model.Article;
import org.bytedesk.jpa.model.Category;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.repository.ArticleRepository;
import org.bytedesk.jpa.repository.CategoryRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * 帮助中心Support：类别
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleRepository articleRepository;

    /**
     * 获取类别、分组成功
     *
     * @param principal
     * @return
     */
    @GetMapping("/all")
    public JsonResult all(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Set<Category> categorySet = categoryRepository.findByUserAndParent(adminOptional.get(), null);
                Iterator iterator = categorySet.iterator();
                while (iterator.hasNext()) {
                    Category category = (Category) iterator.next();
                    Set<Category> children = categoryRepository.findByUserAndParent(adminOptional.get(), category);
                    category.setChildren(children);
                }

                //
                jsonResult.setMessage("获取全部类别/分组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(categorySet);

            } else {

                jsonResult.setMessage("获取全部类别分组失败-管理员账号不存在");
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
     * 获取智能问答类别
     *
     * @param principal
     * @return
     */
    @GetMapping("/robot")
    public JsonResult robot(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {
            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Set<Category> categorySet = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(), TypeConsts.CATEGORY_TYPE_ROBOT, null);
                Iterator iterator = categorySet.iterator();
                while (iterator.hasNext()) {
                    Category category = (Category) iterator.next();
                    Set<Category> children = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(),  TypeConsts.CATEGORY_TYPE_ROBOT, category);
                    category.setChildren(children);
                }
                //
                jsonResult.setMessage("获取智能类别/分组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(categorySet);

            } else {
                //
                jsonResult.setMessage("获取智能类别分组失败-管理员账号不存在");
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
     * 获取帮助中心类别
     *
     * @param principal
     * @return
     */
    @GetMapping("/support")
    public JsonResult support(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {
            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Set<Category> categorySet = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(), TypeConsts.CATEGORY_TYPE_SUPPORT, null);
                Iterator iterator = categorySet.iterator();
                while (iterator.hasNext()) {
                    Category category = (Category) iterator.next();
                    Set<Category> children = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(),  TypeConsts.CATEGORY_TYPE_SUPPORT, category);
                    category.setChildren(children);
                }
                //
                jsonResult.setMessage("获取帮助类别/分组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(categorySet);

            } else {
                //
                jsonResult.setMessage("获取帮助类别分组失败-管理员账号不存在");
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
     * 获取意见反馈类别
     *
     * @param principal
     * @return
     */
    @GetMapping("/feedback")
    public JsonResult feedback(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {
            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Set<Category> categorySet = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(), TypeConsts.CATEGORY_TYPE_FEEDBACK, null);
                Iterator iterator = categorySet.iterator();
                while (iterator.hasNext()) {
                    Category category = (Category) iterator.next();
                    Set<Category> children = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(),  TypeConsts.CATEGORY_TYPE_FEEDBACK, category);
                    category.setChildren(children);
                }
                //
                jsonResult.setMessage("获取帮助类别/分组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(categorySet);

            } else {
                //
                jsonResult.setMessage("获取帮助类别分组失败-管理员账号不存在");
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
     * 获取公司常用语类别
     *
     * @param principal
     * @return
     */
    @GetMapping("/cuw/company")
    public JsonResult cuwCompany(Principal principal) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {
            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {
                //
                Set<Category> categorySet = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(), TypeConsts.CATEGORY_TYPE_CUW_COMPANY, null);
                Iterator iterator = categorySet.iterator();
                while (iterator.hasNext()) {
                    Category category = (Category) iterator.next();
                    Set<Category> children = categoryRepository.findByUserAndTypeAndParent(adminOptional.get(),  TypeConsts.CATEGORY_TYPE_CUW_COMPANY, category);
                    category.setChildren(children);
                }
                //
                jsonResult.setMessage("获取公司常用语类别/分组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(categorySet);

            } else {
                //
                jsonResult.setMessage("获取公司常用语类别分组失败-管理员账号不存在");
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
     * 创建帮助文档类别
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        Integer parentId = (Integer) map.get("parentId");
        String name = (String) map.get("name");
        String type = (String) map.get("type");

        logger.info("parent_id {}, name {}", parentId, name);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Category category = new Category();
                category.setCid(JpaUtil.randomId());
                category.setType(type);
                category.setName(name);
                category.setUser(adminOptional.get());

                if (parentId != null && parentId != 0) {
                    Optional<Category> parentCategoryOptional = categoryRepository.findById(Long.valueOf(parentId));
                    if (parentCategoryOptional.isPresent()) {
                        category.setParent(parentCategoryOptional.get());
                    }
                }
                categoryRepository.save(category);

                //
                jsonResult.setMessage("创建类别/分组成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(category);

            } else {

                jsonResult.setMessage("创建类别/分组失败-管理员账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("创建类别/分组失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }
        return jsonResult;
    }


    /**
     * 更新类别/分组
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        Integer categoryId = (Integer) map.get("categoryId");
        String name = (String) map.get("name");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Optional<Category> categoryOptional = categoryRepository.findById(Long.valueOf(categoryId));
                if (categoryOptional.isPresent()) {

                    categoryOptional.get().setName(name);
                    categoryRepository.save(categoryOptional.get());

                    //
                    jsonResult.setMessage("更新类别/分组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(categoryOptional.get());

                } else {

                    jsonResult.setMessage("更新类别/分组失败-类别不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(categoryId);
                }

            } else {

                jsonResult.setMessage("更新类别/分组失败-管理员账号不存在");
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
     * 删除类别、分组
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        Integer categoryId = (Integer) map.get("categoryId");

        logger.info("category id {}", categoryId);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                //
                Optional<Category> categoryOptional = categoryRepository.findById(Long.valueOf(categoryId));
                if (categoryOptional.isPresent()) {

                    //
                    List<Article> articleList = articleRepository.findByCategoriesContainsOrderByIdDesc(categoryOptional.get());
                    Iterator iterator = articleList.iterator();
                    while (iterator.hasNext()) {
                        Article article = (Article) iterator.next();
                        article.getCategories().remove(categoryOptional.get());
                        articleRepository.save(article);
                    }
                    categoryRepository.delete(categoryOptional.get());

                    // 返回结果
                    jsonResult.setMessage("删除类别/分组成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(categoryOptional.get());

                } else {

                    jsonResult.setMessage("删除类别/分组失败-类别id不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(categoryId);
                }

            } else {

                jsonResult.setMessage("删除类别/分组失败-管理员账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(categoryId);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(categoryId);
        }
        return jsonResult;
    }



    /**
     * 帮助文档类别详情：
     * 返回子分类、分类内所含帮助文档
     *
     * @param principal
     * @param cid
     * @return
     */
    @GetMapping("/detail")
    public JsonResult detail(Principal principal,
                             @RequestParam(value = "cid") String cid) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Optional<Category> categoryOptional = categoryRepository.findByCid(cid);
                if (categoryOptional.isPresent()) {

                    // TODO: 查询类别内相关帮助文档
                    Map<String, Object> objectMap = new HashMap<>(2);
                    objectMap.put("category", categoryOptional.get());
                    objectMap.put("articles", categoryOptional.get().getArticles());

                    //
                    jsonResult.setMessage("获取帮助文档类别详情成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(objectMap);

                } else {

                    jsonResult.setMessage("获取帮助文档类别详情失败-类别不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(cid);
                }

            } else {

                jsonResult.setMessage("获取帮助文档类别详情失败-管理员账号不存在");
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
     * 搜索帮助文档：
     *
     * @param principal
     * @param content
     * @return
     */
    @GetMapping("/search")
    public JsonResult search(Principal principal,
                             @RequestParam(value = "content") String content) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
                Page<Article> articlePage = articleRepository.findByTitleContainingOrContentContaining(content, content, pageable);

                // 返回结果
                jsonResult.setMessage("搜索帮助文档成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(articlePage);

            } else {

                jsonResult.setMessage("搜索帮助文档失败-管理员账号不存在");
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



}
