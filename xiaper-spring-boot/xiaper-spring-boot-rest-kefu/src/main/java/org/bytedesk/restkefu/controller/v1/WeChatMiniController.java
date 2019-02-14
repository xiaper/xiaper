package org.bytedesk.restkefu.controller.v1;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.bytedesk.jpa.constant.*;
import org.bytedesk.jpa.model.*;
import org.bytedesk.jpa.model.Thread;
import org.bytedesk.jpa.repository.*;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.mq.service.MessageService;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.impl.RouteServiceImpl;
import org.bytedesk.mq.service.wechat.WeChatMiniService;
import org.bytedesk.rest.controller.v1.BaseController;
import org.bytedesk.rest.util.AliYunOss;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

/**
 * 小程序
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/wechat/mini")
public class WeChatMiniController extends BaseController {

    @Autowired
    WeChatRepository weChatRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RouteServiceImpl routeService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MessageService messageService;

    @Autowired
    WeChatMiniService weChatMiniService;

    @Autowired
    WeChatUserInfoRepository weChatUserInfoRepository;

    @Autowired
    AliYunOss aliYunOss;


    /**
     * 验证:
     * https://wechat.bytedesk.com/wechat/mini/push/201810292301591
     *
     * @param token
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("/push/{token}")
    public String authGet(@PathVariable String token,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxMaService maService = weChatMiniService.getMaService(token);
        if (maService == null) {
            throw new IllegalArgumentException(String.format("未找到对应token=[%d]的配置，请核实！", token));
        }

        if (maService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    /**
     *
     * https://wechat.bytedesk.com/wechat/mini/push/201810292301591
     *
     * @param token
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param msgSignature
     * @return
     */
    @PostMapping("/push/{token}")
    public String post(@PathVariable String token,
                       @RequestBody String requestBody,
                       @RequestParam("msg_signature") String msgSignature,
                       @RequestParam("encrypt_type") String encryptType,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {

        this.logger.info("\n接收小程序请求：[msg_signature=[{}], encrypt_type=[{}], signature=[{}]," +
                        " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                msgSignature, encryptType, signature, timestamp, nonce, requestBody);

        WxMaService maService = weChatMiniService.getMaService(token);

        // 仅支持xml加密消息
        WxMaMessage inMessage = WxMaMessage.fromEncryptedXml(requestBody, maService.getWxMaConfig(), timestamp, nonce, msgSignature);
        this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());

//        {"encrypt":null,"toUser":"gh_a2745083632e","fromUser":"oYYbw0Dzzi8BeOFAGITHEZshzE1o","createTime":1540857149,
//                "msgType":"text","msgDataFormat":null,"content":"1","msgId":6617931063196434676,"picUrl":null,"mediaId":null,
//                "event":null,"title":null,"appId":null,"pagePath":null,"thumbUrl":null,"thumbMediaId":null,"sessionFrom":null}

        // 首先查找此用户是否已经注册在users表中，
        //  1. 如果没有，则注册在user表中, 并存储此用户到userinfo表中
        //  2. 如果已经注册， 则继续 （更新用户信息到userinfo表中:次要）
        Optional<WeChat> weChatOptional = weChatRepository.findFirstByToken(token);
        User visitor;
        Optional<User> visitorOptional = userRepository.findByUsername(inMessage.getFromUser());
        if (weChatOptional.isPresent() && !visitorOptional.isPresent()) {

            logger.info("sessionFrom: {}", inMessage.getSessionFrom());
            // 小程序前端自定义sessionFrom
            // "sessionFrom":"{ "nickname": "宁金鹏", "city": "Haidian"}"
            JSONObject jsonObject = JSONObject.parseObject(inMessage.getSessionFrom());

            // 保存到user表
            visitor = new User();
            visitor.setUid(JpaUtil.randomId());
            visitor.setUsername(inMessage.getFromUser());
            // TODO: 获取用户信息，设置昵称
            String nickname = jsonObject.getString("nickname") == null ? inMessage.getFromUser() : jsonObject.getString("nickname");
            visitor.setNickname(nickname);
            visitor.setEmail(inMessage.getFromUser()+BdConstants.DEFAULT_AT_EMAIL);
            visitor.setPassword(inMessage.getFromUser());
            visitor.setSubDomain(weChatOptional.get().getUser().getSubDomain());
            visitor.setClient(ClientConsts.CLIENT_WECHAT_MINI);
            userService.saveVisitor(visitor);

        } else {
            visitor = visitorOptional.get();
        }

        // TODO: 持久化消息内容到MySQL
        WorkGroup workGroup = weChatOptional.get().getWorkGroup();
        logger.info("save message to mysql");
        Message message = new Message();
        message.setMid(JpaUtil.randomId());
        message.setWid(workGroup.getWid());
        message.setType(inMessage.getMsgType());
        message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_WORK_GROUP);
        message.setStatus(StatusConsts.MESSAGE_STATUS_STORED);
        message.setUser(visitor);
        message.setClient(ClientConsts.CLIENT_WECHAT_MINI);

        // 首先查找是否已经存在进行中的会话，如果没有则创建
        Optional<Thread> threadOptional = threadRepository.findFirstByVisitorAndWorkGroupAndAppointedAndClosed(visitor, workGroup, false, false);
        //
        Thread thread;
        if (threadOptional.isPresent()) {
            thread = threadOptional.get();
        } else {
            thread = new Thread();
            // FIXME: 集群分布，当多个实例存在，tid理论上有可能重复？需要处理
            thread.setTid(JpaUtil.randomId());
            thread.setToken(token);
            thread.setType(TypeConsts.THREAD_TYPE_WORK_GROUP);
            thread.setContent(visitor.getNickname() + BdConstants.DEFAULT_WORK_GROUP_REQUEST_THREAD);
            thread.setTimestamp(new Date());
            thread.setUnreadCount(0);
            thread.setVisitor(visitor);
            thread.setWorkGroup(workGroup);
            thread.setStartedAt(new Date());
            threadRepository.save(thread);
        }
        message.setThread(thread);

        // TODO: 处理更多消息类型
        switch (inMessage.getMsgType()) {
            case TypeConsts.MESSAGE_TYPE_TEXT:
                message.setContent(inMessage.getContent());
                thread.setContent(inMessage.getContent());
                break;
            case TypeConsts.MESSAGE_TYPE_IMAGE:
                message.setPicUrl(inMessage.getPicUrl());
                // 下载图片，存在在imageUrl
                String fileName = inMessage.getFromUser() + "_" + JpaUtil.randomId() + ".png";
                String imageUrl = aliYunOss.saveWeChatImageUrl(fileName, inMessage.getPicUrl());
                message.setImageUrl(imageUrl);
                thread.setContent("[图片]");
                break;
            case TypeConsts.MESSAGE_TYPE_EVENT:
                // 访客打开客服页面：user_enter_tempsession
//        {"encrypt":null,"toUser":"gh_a2745083632e","fromUser":"oYYbw0Dzzi8BeOFAGITHEZshzE1o","createTime":1540827505,
//          "msgType":"event","msgDataFormat":null,"content":null,"msgId":null,"picUrl":null,"mediaId":null,
//          "event":"user_enter_tempsession","title":null,"appId":null,"pagePath":null,"thumbUrl":null,"thumbMediaId":null,
//          "sessionFrom":"{ "nickname": "宁金鹏", "city": "Haidian"}"}
                if (inMessage.getEvent() != null
                        && inMessage.getEvent().equals(TypeConsts.MESSAGE_TYPE_USER_ENTER_TEMPSESSION)) {
                    message.setContent("访客进入会话");
                }
                break;
            default:
                logger.info("other MINI message type");
        }
        messageRepository.save(message);

        // TODO: 重构路由模块
        // TODO: 广播给thread
        logger.info("route");
        if (threadOptional.isPresent()) {
            // thread会话已经存在

            // 判断是否为当前会话thread，如果不是，则增加
            if (!thread.isCurrent()) {
                thread.increaseUnreadCount();
            }
            thread.setTimestamp(message.getCreatedAt());
            threadRepository.save(thread);

            // 自动回复
            messageService.autoReply(message);

            // 推送通知
            rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_THREAD_MESSAGE, message);

        } else {
            // thread会话不存在，则路由给客服
            routeService.route(workGroup, thread, visitor);
        }


        return "";
    }


}
