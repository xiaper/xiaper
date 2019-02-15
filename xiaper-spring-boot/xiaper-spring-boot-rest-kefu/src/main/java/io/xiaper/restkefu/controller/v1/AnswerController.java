package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.constant.BdConstants;
import io.xiaper.jpa.constant.ClientConsts;
import io.xiaper.jpa.constant.TypeConsts;
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
import java.util.*;

/**
 * 机器人、智能客服: 问答库
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/answer")
public class AnswerController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    AnswerRateRepository answerRateRepository;

    @Autowired
    AnswerQueryRepository answerQueryRepository;

    @Autowired
    SynonymRepository synonymRepository;

    /**
     * 访客端初始化：自助答疑
     *
     * @param principal
     * @return
     */
    @GetMapping("/init")
    public JsonResult init(Principal principal,
                           @RequestParam(value = "uid") String uid,
                           @RequestParam(value = "tid") String tid,
                           @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            Optional<Thread> threadOptional = threadRepository.findByTid(tid);

            if (visitorOptional.isPresent()) {

                Optional<User> adminOptional = userRepository.findByUid(uid);
                if (adminOptional.isPresent()) {

                    // 自助答疑，返回消息
                    Message replyMessage = new Message();
                    replyMessage.setMid(JpaUtil.randomId());
                    if (threadOptional.get().getWorkGroup() != null) {
                        replyMessage.setWid(threadOptional.get().getWorkGroup().getWid());
                    } else {
                        replyMessage.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    }
                    replyMessage.setClient(ClientConsts.CLIENT_SYSTEM);
                    replyMessage.setThread(threadOptional.get());
                    replyMessage.setType(TypeConsts.MESSAGE_TYPE_ROBOT);

                    // FIXME:支持自定义欢迎语
                    // "您好，我是您的智能助理"  + adminOptional.get().getRoBotUser().getNickname()+ ",请问有什么可以帮您的？"
                    replyMessage.setContent(adminOptional.get().getRoBotUser().getWelcomeTip());
                    replyMessage.setUser(adminOptional.get().getRoBotUser());

                    // TODO: 携带热门问答5条
                    Set<Answer> answers = answerRepository.findTop5ByUserAndRelatedOrderByQueryCountDesc(adminOptional.get(), false);
                    replyMessage.setAnswers(answers);

                    messageRepository.save(replyMessage);

                    // 返回结果
                    jsonResult.setMessage("初始化自助答疑成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(replyMessage);

                } else {

                    jsonResult.setMessage("初始化自助答疑失败-uid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("初始化自助答疑失败-访客账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("初始化自助答疑失败-access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 访客端获取：最热智能问答20条
     *
     * @param principal
     * @return
     */
    @GetMapping("/top")
    public JsonResult top(Principal principal,
                          @RequestParam(value = "uid") String uid,
                          @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                Optional<User> adminOptional = userRepository.findByUid(uid);
                if (adminOptional.isPresent()) {

                    // 分页查询
                    Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "queryCount");
                    Page<Answer> answerPage = answerRepository.findByUserAndRelated(adminOptional.get(), false, pageable);

                    // 返回结果
                    jsonResult.setMessage("获取最热问答成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(answerPage);

                } else {

                    jsonResult.setMessage("获取最热问答失败-uid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("获取最热问答失败-访客账号不存在");
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
     * 访客端根据aid获取智能答案
     *
     * @param principal
     * @return
     */
    @GetMapping("/query")
    public JsonResult query(Principal principal,
                            @RequestParam(value = "uid") String uid,
                            @RequestParam(value = "tid") String tid,
                            @RequestParam(value = "aid") String aid,
                            @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 访客是否存在
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            // 管理员
            Optional<User> userOptional = userRepository.findByUid(uid);

            if (visitorOptional.isPresent() && userOptional.isPresent()) {

                // 会话thread是否存在
                Optional<Thread> threadOptional = threadRepository.findByTid(tid);
                if (threadOptional.isPresent()) {

                    //
                    Optional<Answer> answerOptional = answerRepository.findByAid(aid);
                    if (answerOptional.isPresent()) {

                        // 更新查询记录
                        AnswerQuery answerQuery = new AnswerQuery();
                        answerQuery.setAnswer(answerOptional.get());
                        answerQuery.setUser(visitorOptional.get());
                        answerQueryRepository.save(answerQuery);

                        // 更新问答查询次数
                        answerOptional.get().updateQueryCount();
                        answerRepository.save(answerOptional.get());

                        // 请求消息
                        Message queryMessage = new Message();
                        queryMessage.setMid(JpaUtil.randomId());
                        if (threadOptional.get().getWorkGroup() != null) {
                            queryMessage.setWid(threadOptional.get().getWorkGroup().getWid());
                        } else {
                            queryMessage.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                        }
                        queryMessage.setClient(ClientConsts.CLIENT_SYSTEM);
                        queryMessage.setThread(threadOptional.get());
                        queryMessage.setType(TypeConsts.MESSAGE_TYPE_ROBOT);
                        queryMessage.setContent(answerOptional.get().getQuestion());
                        queryMessage.setAnswer(answerOptional.get());
                        queryMessage.setUser(visitorOptional.get());
                        messageRepository.save(queryMessage);

                        // 返回消息
                        Message replyMessage = new Message();
                        replyMessage.setMid(JpaUtil.randomId());
                        if (threadOptional.get().getWorkGroup() != null) {
                            replyMessage.setWid(threadOptional.get().getWorkGroup().getWid());
                        } else {
                            replyMessage.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                        }
                        replyMessage.setClient(ClientConsts.CLIENT_SYSTEM);
                        replyMessage.setThread(threadOptional.get());
                        replyMessage.setType(TypeConsts.MESSAGE_TYPE_ROBOT);
                        replyMessage.setContent(answerOptional.get().getAnswer());
                        replyMessage.setAnswer(answerOptional.get());
                        replyMessage.setUser(userOptional.get().getRoBotUser());

                        // TODO: 返回消息携带 同类别top5问题

                        messageRepository.save(replyMessage);

                        // 组合
                        Map<String, Object> objectMap = new HashMap<>(2);
                        objectMap.put("query", queryMessage);
                        objectMap.put("reply", replyMessage);

                        // 返回结果
                        jsonResult.setMessage("获取相应问答成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(objectMap);

                    } else {

                        jsonResult.setMessage("获取相应问答失败-问答aid不存在");
                        jsonResult.setStatus_code(-3);
                        jsonResult.setData(false);
                    }

                } else {

                    jsonResult.setMessage("获取相应问答失败-会话tid不存在");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("获取相应问答失败-访客账号不存在");
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
     * 访客端发送内容，请求智能问答
     *
     * @param principal
     * @param uid
     * @param tid
     * @param content
     * @param client
     * @return
     */
    @GetMapping("/message")
    public JsonResult message(Principal principal,
                            @RequestParam(value = "uid") String uid,
                            @RequestParam(value = "tid") String tid,
                            @RequestParam(value = "content") String content,
                            @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 访客是否存在
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            // 管理员
            Optional<User> userOptional = userRepository.findByUid(uid);
            //
            if (visitorOptional.isPresent() && userOptional.isPresent()) {

                // 会话thread是否存在
                Optional<Thread> threadOptional = threadRepository.findByTid(tid);
                if (threadOptional.isPresent()) {

                    // 请求消息
                    Message queryMessage = new Message();
                    queryMessage.setMid(JpaUtil.randomId());
                    if (threadOptional.get().getWorkGroup() != null) {
                        queryMessage.setWid(threadOptional.get().getWorkGroup().getWid());
                    } else {
                        queryMessage.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                    }
                    queryMessage.setClient(client);
                    queryMessage.setThread(threadOptional.get());
                    queryMessage.setType(TypeConsts.MESSAGE_TYPE_ROBOT);
                    queryMessage.setContent(content);
                    queryMessage.setUser(visitorOptional.get());
                    messageRepository.save(queryMessage);

                   //
                    Optional<Answer> answerOptional = answerRepository.findFirstByUserAndQuestionContainingOrAnswerContaining(userOptional.get(),
                            content, content);
                    if (answerOptional.isPresent()) {
                        logger.info("1. content {}, answer {}", content, answerOptional.get().getAnswer());

                        // 更新查询记录
                        AnswerQuery answerQuery = new AnswerQuery();
                        answerQuery.setAnswer(answerOptional.get());
                        answerQuery.setUser(visitorOptional.get());
                        answerQueryRepository.save(answerQuery);

                        // 更新问答查询次数
                        answerOptional.get().updateQueryCount();
                        answerRepository.save(answerOptional.get());

                        // 返回消息
                        Message replyMessage = new Message();
                        replyMessage.setMid(JpaUtil.randomId());
                        if (threadOptional.get().getWorkGroup() != null) {
                            replyMessage.setWid(threadOptional.get().getWorkGroup().getWid());
                        } else {
                            replyMessage.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                        }
                        replyMessage.setClient(ClientConsts.CLIENT_SYSTEM);
                        replyMessage.setThread(threadOptional.get());
                        replyMessage.setType(TypeConsts.MESSAGE_TYPE_ROBOT);
                        replyMessage.setContent(answerOptional.get().getAnswer());
                        replyMessage.setAnswer(answerOptional.get());
                        replyMessage.setUser(userOptional.get().getRoBotUser());

                        // TODO: 返回其余相关问题

                        messageRepository.save(replyMessage);

                        // 组合
                        Map<String, Object> objectMap = new HashMap<>(2);
                        objectMap.put("query", queryMessage);
                        objectMap.put("reply", replyMessage);

                        // 返回结果
                        jsonResult.setMessage("获取智能问答成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(objectMap);

                    } else {

                        // TODO: 匹配相似问法

                        // TODO: 添加通过相似词进行匹配

                        List<Synonym> synonymList = synonymRepository.findByUser(userOptional.get());
                        Iterator iterator = synonymList.iterator();
                        while (iterator.hasNext()) {
                            Synonym synonym = (Synonym) iterator.next();
                            //
                            if (synonym.getStandard().trim().length() > 0 && content.contains(synonym.getStandard())) {
                                logger.info("2. content: {}, synonym: {}", content, synonym.getStandard());
                                //
                                Optional<Answer> optionalAnswer = answerRepository.findFirstByUserAndQuestionContainingOrAnswerContaining(userOptional.get(),
                                        synonym.getStandard(), synonym.getStandard());
                                if (optionalAnswer.isPresent()) {
                                    //
                                    // 更新查询记录
                                    AnswerQuery answerQuery = new AnswerQuery();
                                    answerQuery.setAnswer(optionalAnswer.get());
                                    answerQuery.setUser(visitorOptional.get());
                                    answerQueryRepository.save(answerQuery);

                                    // 更新问答查询次数
                                    optionalAnswer.get().updateQueryCount();
                                    answerRepository.save(optionalAnswer.get());

                                    // 返回消息
                                    Message replyMessage = new Message();
                                    replyMessage.setMid(JpaUtil.randomId());
                                    if (threadOptional.get().getWorkGroup() != null) {
                                        replyMessage.setWid(threadOptional.get().getWorkGroup().getWid());
                                    } else {
                                        replyMessage.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                                    }
                                    replyMessage.setClient(ClientConsts.CLIENT_SYSTEM);
                                    replyMessage.setThread(threadOptional.get());
                                    replyMessage.setType(TypeConsts.MESSAGE_TYPE_ROBOT);
                                    replyMessage.setContent(optionalAnswer.get().getAnswer());
                                    replyMessage.setAnswer(optionalAnswer.get());
                                    replyMessage.setUser(userOptional.get().getRoBotUser());

                                    // TODO: 返回其余相关问题

                                    messageRepository.save(replyMessage);

                                    // 组合
                                    Map<String, Object> objectMap = new HashMap<>(2);
                                    objectMap.put("query", queryMessage);
                                    objectMap.put("reply", replyMessage);

                                    // 返回结果
                                    jsonResult.setMessage("获取智能问答成功");
                                    jsonResult.setStatus_code(200);
                                    jsonResult.setData(objectMap);

                                    // 返回第一个匹配的结果
                                    return jsonResult;
                                }

                            }

                        }

                        // 返回消息
                        Message replyMessage = new Message();
                        replyMessage.setMid(JpaUtil.randomId());
                        if (threadOptional.get().getWorkGroup() != null) {
                            replyMessage.setWid(threadOptional.get().getWorkGroup().getWid());
                        } else {
                            replyMessage.setWid(TypeConsts.THREAD_TYPE_APPOINTED);
                        }
                        replyMessage.setClient(ClientConsts.CLIENT_SYSTEM);
                        replyMessage.setThread(threadOptional.get());
                        replyMessage.setType(TypeConsts.MESSAGE_TYPE_ROBOT);
                        replyMessage.setContent(BdConstants.DEFAULT_WORK_GROUP_ROBOT_ANSWER_NOT_FOUND);
                        replyMessage.setUser(userOptional.get().getRoBotUser());
                        messageRepository.save(replyMessage);

                        // 组合
                        Map<String, Object> objectMap = new HashMap<>(2);
                        objectMap.put("query", queryMessage);
                        objectMap.put("reply", replyMessage);

                        // TODO: 返回其他可能相关问题答案

                        // 返回结果
                        jsonResult.setMessage("获取智能问答失败-未找到相应答案");
                        jsonResult.setStatus_code(201);
                        jsonResult.setData(objectMap);
                    }

                } else {

                    jsonResult.setMessage("获取智能问答失败-会话tid不存在");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("获取智能问答失败-访客账号不存在");
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
     * 问答：有帮助、无帮助
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
        String mid = (String) map.get("mid");
        Boolean rate = (Boolean) map.get("rate");
        String client = (String) map.get("client");
        logger.info("uid {}, aid {}, rate {}, client {}", uid, aid, rate, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 访客是否存在
            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                // 文章是否存在
                Optional<Answer> answerOptional = answerRepository.findByAid(aid);
                if (answerOptional.isPresent()) {

                    // 消息记录是否存
                    Optional<Message> messageOptional = messageRepository.findByMid(mid);
                    if (messageOptional.isPresent()) {

                        // 是否已经评价过
                        Optional<AnswerRate> answerRateOptional = answerRateRepository.findByAnswerAndUser(answerOptional.get(), visitorOptional.get());
                        if (!answerRateOptional.isPresent()) {

                            // 记录评价记录
                            AnswerRate answerRate = new AnswerRate();
                            answerRate.setHelpful(rate);
                            answerRate.setMessage(messageOptional.get());
                            answerRate.setAnswer(answerOptional.get());
                            answerRate.setUser(visitorOptional.get());
                            answerRateRepository.save(answerRate);

                            // 更新
                            answerOptional.get().rate(rate);
                            answerRepository.save(answerOptional.get());

                            // 返回结果
                            jsonResult.setMessage("点评问答成功");
                            jsonResult.setStatus_code(200);
                            jsonResult.setData(answerOptional.get());

                        } else {

                            //
                            jsonResult.setMessage("点评问答失败-不能重复评价");
                            jsonResult.setStatus_code(-5);
                            jsonResult.setData(aid);
                        }
                    } else {

                        //
                        jsonResult.setMessage("点评问答失败-消息记录mid不存在");
                        jsonResult.setStatus_code(-4);
                        jsonResult.setData(aid);
                    }
                } else {

                    //
                    jsonResult.setMessage("点评问答失败-aid未找到");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(aid);
                }

            } else {

                jsonResult.setMessage("点评问答失败-访客账号不存在");
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
     * 获取智能问答
     *
     * @param principal
     * @return
     */
    @GetMapping("/get")
    public JsonResult getAnswers(Principal principal,
                                 @RequestParam(value = "categoryId") Integer categoryId,
                                 @RequestParam(value = "page") int page,
                                 @RequestParam(value = "size") int size,
                                 @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");

                if (categoryId == 0)  {

                    Page<Answer> answerPage = answerRepository.findByUserAndRelated(adminOptional.get(), false, pageable);

                    jsonResult.setMessage("获取首页问答成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(answerPage);

                } else {

                    Optional<Category> categoryOptional = categoryRepository.findById(Long.valueOf(categoryId));
                    if (categoryOptional.isPresent()) {

                        Page<Answer> answerPage = answerRepository.findByCategoriesContains(categoryOptional.get(), pageable);

                        //
                        jsonResult.setMessage("获取分类问答成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(answerPage);

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
     * 创建智能问答
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        // String code = (String) map.get("code");
        String question = (String) map.get("question");
        List<String> relateds = (List<String>) map.get("relateds");
        String answerStr = (String) map.get("answer");
        List<Integer> categories = (List<Integer>) map.get("categories");
        logger.info("question {}, relateds {}, answer {}, categories {}", question, relateds, answerStr, categories);

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Answer answer = new Answer();
            // FIXME: 检查code唯一性
            // answer.setCode(code);
            answer.setAid(JpaUtil.randomId());
            answer.setQuestion(question);
            answer.setAnswer(answerStr);
            answer.setRelated(false);

            // related 相关问题
            for (int i = 0; i < relateds.size(); i++) {
                String quest = relateds.get(i);
                //
                Answer related = new Answer();
                related.setAid(JpaUtil.randomId());
                related.setQuestion(quest);
                related.setAnswer(answerStr);
                related.setRelated(true);
                related.setUser(adminOptional.get());
                answerRepository.save(related);

                answer.getRelateds().add(related);
            }
            answer.setUser(adminOptional.get());

            for (int i = 0; i < categories.size(); i++) {
                Long categoryId = Long.valueOf(categories.get(i));

                Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                if (categoryOptional.isPresent()) {
                    answer.getCategories().add(categoryOptional.get());
                }
            }
            answerRepository.save(answer);

            // 返回结果
            jsonResult.setMessage("创建智能问答成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(answer);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 更新智能问答
     *
     * @param principal
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        String aid = (String) map.get("aid");
        // String code = (String) map.get("code");
        String question = (String) map.get("question");
        List<String> relateds = (List<String>) map.get("relateds");
        String answerStr = (String) map.get("answer");
        List<Integer> categories = (List<Integer>) map.get("categories");
        logger.info("question {}, relateds {}, answer {}, categories {}", question, relateds, answerStr, categories);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Optional<Answer> answerOptional = answerRepository.findByAid(aid);
                if (adminOptional.isPresent()) {

                    answerOptional.get().setQuestion(question);
                    answerOptional.get().setAnswer(answerStr);

                    // FIXME: 有待优化
                    // 首先删除原先，然后重新创建新的
                    Set<Answer> relatedSet =answerOptional.get().getRelateds();
                    Iterator relatedIterator = relatedSet.iterator();
                    while (relatedIterator.hasNext()) {
                        relatedIterator.next();
                        relatedIterator.remove();
                    }

                    // 重新创建 related 相关问题
                    answerOptional.get().getRelateds().clear();
                    for (int i = 0; i < relateds.size(); i++) {
                        String quest = relateds.get(i);
                        //
                        Answer related = new Answer();
                        related.setAid(JpaUtil.randomId());
                        related.setQuestion(quest);
                        related.setAnswer(answerStr);
                        related.setRelated(true);
                        related.setUser(adminOptional.get());
                        answerRepository.save(related);

                        answerOptional.get().getRelateds().add(related);
                    }

                    // FIXME: 有待优化
                    // 首先删除原先，然后重新创建新的
                    Set<Category> categorySet = answerOptional.get().getCategories();
                    Iterator categoryIterator = categorySet.iterator();
                    while (categoryIterator.hasNext()) {
                        categoryIterator.next();
                        categoryIterator.remove();
                    }

                    for (int i = 0; i < categories.size(); i++) {
                        Long categoryId = Long.valueOf(categories.get(i));

                        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                        if (categoryOptional.isPresent()) {
                            answerOptional.get().getCategories().add(categoryOptional.get());
                        }
                    }
                    answerRepository.save(answerOptional.get());

                    // 返回结果
                    jsonResult.setMessage("更新智能问答成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(answerOptional.get());

                } else {

                    jsonResult.setMessage("更新智能问答失败-aid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("管理员账号不存在");
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
     * 删除智能问答
     *
     * @param principal
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        String aid = (String) map.get("aid");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Optional<Answer> answerOptional = answerRepository.findByAid(aid);
                if (adminOptional.isPresent()) {

                    // TODO：首先删除
                    Set<Answer> relatedSet = answerOptional.get().getRelateds();
                    Iterator relatedIterator = relatedSet.iterator();
                    while (relatedIterator.hasNext()) {
                        relatedIterator.next();
                        relatedIterator.remove();
                    }

                    // TODO：首先删除
                    Set<Category> categorySet = answerOptional.get().getCategories();
                    Iterator categoryIterator = categorySet.iterator();
                    while (categoryIterator.hasNext()) {
                        categoryIterator.next();
                        categoryIterator.remove();
                    }

                    // TODO：最后删除自己
                    answerRepository.delete(answerOptional.get());

                    // 返回结果
                    jsonResult.setMessage("删除智能问答成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(aid);

                } else {

                    jsonResult.setMessage("更新智能问答失败-aid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("删除-管理员账号不存在");
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


