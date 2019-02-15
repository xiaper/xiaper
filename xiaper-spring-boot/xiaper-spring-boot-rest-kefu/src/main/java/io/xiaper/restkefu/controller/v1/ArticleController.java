package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.model.*;
import io.xiaper.jpa.repository.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 帮助中心Support: 文章
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleRateRepository articleRateRepository;

    @Autowired
    ArticleReadRepository articleReadRepository;

    /**
     * 获取帮助文档
     * 注：
     * 如果 categoryId == 0，则返回 全部，
     * categoryId == -1, 返回未分类
     * 否则返回相关article
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult getArticles(Principal principal,
                                  @RequestParam(value = "categoryId") Integer categoryId,
                                  @RequestParam(value = "page") int page,
                                  @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

                if (categoryId == 0)  {

                    Page<Article> articlePage = articleRepository.findByUser(adminOptional.get(), pageable);

                    //
                    jsonResult.setMessage("获取帮助文档成功1");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(articlePage);

                } else {

                    Optional<Category> categoryOptional = categoryRepository.findById(Long.valueOf(categoryId));
                    if (categoryOptional.isPresent()) {

                        Page<Article> articlePage = articleRepository.findByCategoriesContains(categoryOptional.get(), pageable);

                        //
                        jsonResult.setMessage("获取帮助文档成功2");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(articlePage);

                    } else {

                        jsonResult.setMessage("类别id错误");
                        jsonResult.setStatus_code(-3);
                        jsonResult.setData(categoryId);
                    }
                }

            } else {

                jsonResult.setMessage("获取帮助文档-管理员账号不存在");
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
     * 获取推荐帮助文档：
     * 右下角对话框：常见问题
     *
     * @return
     */
    @GetMapping("/recommends")
    public JsonResult getRecommends(Principal principal,
                                    @RequestParam(value = "uid") String uid,
                                    @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUid(uid);
            if (adminOptional.isPresent()) {

                List<Article> articleList = articleRepository.findByUserAndRecommend(adminOptional.get(), true);
                //
                jsonResult.setMessage("获取推荐帮助文档成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(articleList);

            } else {

                jsonResult.setMessage("获取推荐帮助文档失败-管理员账号不存在");
                jsonResult.setStatus_code(-1);
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
     * 创建帮助文档
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        String title = (String) map.get("title");
        String content = (String) map.get("content");
        boolean recommend = (boolean) map.get("recommend");
        boolean markdown = (boolean) map.get("markdown");
        List<Integer> categories = (List<Integer>) map.get("categories");
        logger.info("content {}, categories {}", content, categories);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Article article = new Article();
                article.setAid(JpaUtil.randomId());
                article.setTitle(title);
                article.setContent(content);
                article.setRecommend(recommend);
                article.setMarkdown(markdown);
                article.setUser(adminOptional.get());

                for (int i = 0; i < categories.size(); i++) {
                    Integer categoryId = categories.get(i);

                    Optional<Category> categoryOptional = categoryRepository.findById(Long.valueOf(categoryId));
                    if (categoryOptional.isPresent()) {

                        article.getCategories().add(categoryOptional.get());
                    }
                }
                articleRepository.save(article);

                //
                jsonResult.setMessage("创建帮助文档成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(article);

            } else {

                jsonResult.setMessage("创建帮助文档-管理员账号不存在");
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
     * 更新帮助文档
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        Integer articleId = (Integer) map.get("articleId");
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        boolean recommend = (boolean) map.get("recommend");
        boolean markdown = (boolean) map.get("markdown");
        List<Integer> categories = (List<Integer>) map.get("categories");
        logger.info("content {}, categories {}", content, categories);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Optional<Article> articleOptional = articleRepository.findById(Long.valueOf(articleId));
                if (articleOptional.isPresent()) {

                    articleOptional.get().setTitle(title);
                    articleOptional.get().setContent(content);
                    articleOptional.get().setRecommend(recommend);
                    articleOptional.get().setMarkdown(markdown);
                    articleOptional.get().getCategories().clear();

                    if (categories != null && categories.size() > 0) {
                        for (int i = 0; i < categories.size(); i++) {
                            Integer categoryId = categories.get(i);

                            Optional<Category> categoryOptional = categoryRepository.findById(Long.valueOf(categoryId));
                            if (categoryOptional.isPresent()) {

                                articleOptional.get().getCategories().add(categoryOptional.get());
                            }
                        }
                    }
                    articleRepository.save(articleOptional.get());

                    //
                    jsonResult.setMessage("更新帮助文档成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(articleOptional.get());

                } else {

                    jsonResult.setMessage("更新帮助文档失败-文档不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(articleId);
                }

            } else {

                jsonResult.setMessage("更新帮助文档-管理员账号不存在");
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
     * 删除帮助文档
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal,
                             @RequestBody Map map) {

        Integer articleId = (Integer) map.get("articleId");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                articleRepository.deleteById(Long.valueOf(articleId));

                //
                jsonResult.setMessage("删除帮助文档成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(articleId);

            } else {

                jsonResult.setMessage("删除帮助文档-管理员账号不存在");
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
     * 获取某帮助文档详情
     *
     * @return
     */
    @GetMapping("/detail")
    public JsonResult detail(Principal principal,
                             @RequestParam(value = "aid") String aid) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 访客是否存在
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                // 文章是否存在
                Optional<Article> articleOptional = articleRepository.findByAid(aid);
                if (articleOptional.isPresent()) {

                    Map<String, Object> objectMap = new HashMap<>(16);
                    if (articleOptional.isPresent()) {

                        // 记录阅读记录
                        ArticleRead articleRead = new ArticleRead();
                        articleRead.setArticle(articleOptional.get());
                        articleRead.setUser(visitorOptional.get());
                        articleReadRepository.save(articleRead);

                        // 增加阅读数量
                        articleOptional.get().updateReadCount();
                        articleRepository.save(articleOptional.get());

                        // FIXME: 每个文档至少属于一个分类，如果文档属于多个分类，仅取第一个分类
                        Category category = (Category) articleOptional.get().getCategories().toArray()[0];
                        //
                        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "updatedAt");
                        Page<Article> articlePage = articleRepository.findByCategoriesContains(category, pageable);
                        //
                        objectMap.put("article", articleOptional.get());
                        objectMap.put("related", articlePage.getContent());
                    }

                    //
                    jsonResult.setMessage("获取帮助文档详情成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(objectMap);

                } else {

                    jsonResult.setMessage("获取帮助文档详情失败-aid未找到");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(aid);
                }

            } else {
                //
                jsonResult.setMessage("获取帮助文档详情失败-访客账号不存在");
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
     * 帮助文档：有帮助、无帮助
     *
     * FIXME: 一个访客仅允许评价一次，不允许重复评价，但可以取消评价，或者改变评价
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/rate")
    @ResponseBody
    public JsonResult rate(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String aid = (String) map.get("aid");
        Boolean rate = (Boolean) map.get("rate");
        String client = (String) map.get("client");
        logger.info("uid {}, aid {}, rate {}, client {}", uid, aid, rate, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 访客是否存在
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                // 文章是否存在
                Optional<Article> articleOptional = articleRepository.findByAid(aid);
                if (articleOptional.isPresent()) {

                    // 是否已经评价过
                    Optional<ArticleRate> articleRateOptional = articleRateRepository.findByArticleAndUser(articleOptional.get(), visitorOptional.get());
                    if (!articleRateOptional.isPresent()) {

                        // 存储评价记录
                        ArticleRate articleRate = new ArticleRate();
                        articleRate.setHelpful(rate);
                        articleRate.setArticle(articleOptional.get());
                        articleRate.setUser(visitorOptional.get());
                        articleRateRepository.save(articleRate);

                        // 更新
                        articleOptional.get().rate(rate);
                        articleRepository.save(articleOptional.get());

                        // 返回结果
                        jsonResult.setMessage("点评帮助文档成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(articleOptional.get());

                    } else {

                        //
                        jsonResult.setMessage("点评帮助文档失败-不能重复评价");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(aid);
                    }

                } else {

                    //
                    jsonResult.setMessage("点评帮助文档失败-aid未找到");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(aid);
                }

            } else {

                jsonResult.setMessage("点评帮助文档失败-访客账号不存在");
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
