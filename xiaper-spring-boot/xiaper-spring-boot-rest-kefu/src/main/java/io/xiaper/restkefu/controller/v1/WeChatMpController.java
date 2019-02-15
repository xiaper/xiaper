package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.constant.*;
import io.xiaper.jpa.model.*;
import io.xiaper.jpa.repository.*;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import io.xiaper.jpa.util.JpaUtil;
import org.bytedesk.mq.service.MessageService;
import org.bytedesk.mq.service.UserService;
import org.bytedesk.mq.service.impl.RouteServiceImpl;
import org.bytedesk.mq.service.wechat.WeChatMpService;
import io.xiaper.rest.controller.v1.BaseController;
import io.xiaper.rest.util.AliYunOss;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.Optional;

/**
 * 公众号
 * TODO: 直接通过提供URL对接公众号
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/wechat/mp")
public class WeChatMpController extends BaseController {

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
    WeChatMpService weChatMpService;

    @Autowired
    WeChatUserInfoRepository weChatUserInfoRepository;

    @Autowired
    AliYunOss aliYunOss;


    /**
     * 验证:
     * https://wechat.bytedesk.com/wechat/mp/push/201810291632211
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

        this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        // 参考：第三方BaseWxMpServiceImpl类
        // https://github.com/Wechat-Group/weixin-java-tools/blob/master/weixin-java-mp/src/main/java/me/chanjar/weixin/mp/api/impl/BaseWxMpServiceImpl.java
        if (SHA1.gen(token, timestamp, nonce).equals(signature)) {
            return echostr;
        }

        return "非法请求";
    }

    /**
     * 文档地址：
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453
     *
     * https://wechat.bytedesk.com/wechat/mp/push/201810291632211
     *
     * /wechat/mp/push/201810291632211?
     * signature=79ced8d11bfd81e54f7f1d31adf6cc6ebd1688e3
     * &timestamp=1540821487&nonce=1587490428
     * &openid=oZdJ7jrF0LEwtIpFv9eXgUqPKClk
     * &encrypt_type=aes
     * &msg_signature=86df9ad95d6f76179598ffbdd911d6185b7193ac
     *
     * @param token
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return
     */
    @PostMapping("/push/{token}")
    public String post(@PathVariable String token,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        this.logger.info("\n接收公众号请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, weChatMpService.getMpService(token).getWxMpConfigStorage(),
                timestamp, nonce, msgSignature);
        this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());

        // 用户取消关注，忽略
        if (inMessage.getMsgType().equals(TypeConsts.MESSAGE_TYPE_EVENT)
                && inMessage.getEventKey() != null
                && inMessage.getEventKey().equals(TypeConsts.MESSAGE_EVENT_TYPE_UNSUBSCRIBE)) {
            return "";
        }

        // 首先查找此用户是否已经注册在users表中，
        //  1. 如果没有，则注册在user表中, 并存储此用户到userinfo表中
        //  2. 如果已经注册， 则继续 （更新用户信息到userinfo表中:次要）
        Optional<WeChat> weChatOptional = weChatRepository.findFirstByToken(token);
        User visitor = new User();
        Optional<User> visitorOptional = userRepository.findByUsername(inMessage.getFromUser());
        if (weChatOptional.isPresent() && !visitorOptional.isPresent()) {

            //用户首次发生消息，存储到user表中
            try {
                WxMpUser wxMpUser = weChatMpService.getMpService(token).getUserService().userInfo(inMessage.getFromUser());
                logger.info("save user info");

                // 保存到user表
                visitor = new User();
                visitor.setUid(JpaUtil.randomId());
                visitor.setUsername(wxMpUser.getOpenId());
                visitor.setNickname(wxMpUser.getNickname() == null ? wxMpUser.getOpenId() : wxMpUser.getNickname());
                visitor.setEmail(wxMpUser.getOpenId()+BdConstants.DEFAULT_AT_EMAIL);
                visitor.setAvatar(wxMpUser.getHeadImgUrl());
                visitor.setPassword(wxMpUser.getOpenId());
                visitor.setSubDomain(weChatOptional.get().getUser().getSubDomain());
                visitor.setClient(ClientConsts.CLIENT_WECHAT_MP);
                userService.saveVisitor(visitor);

                // 保存微信用户信息
                WeChatUserInfo weChatUserInfo = new WeChatUserInfo();
                weChatUserInfo.setSubscribe(wxMpUser.getSubscribe());
                weChatUserInfo.setOpenId(wxMpUser.getOpenId());
                weChatUserInfo.setNickname(visitor.getNickname());
                weChatUserInfo.setSex(wxMpUser.getSex());
                weChatUserInfo.setCity(wxMpUser.getCity());
                weChatUserInfo.setLanguage(wxMpUser.getLanguage());
                weChatUserInfo.setProvince(wxMpUser.getProvince());
                weChatUserInfo.setCountry(wxMpUser.getCountry());
                weChatUserInfo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
                weChatUserInfo.setSubscribeTime(wxMpUser.getSubscribeTime());
                weChatUserInfo.setUnionId(wxMpUser.getUnionId());
                weChatUserInfo.setRemark(wxMpUser.getRemark());
                weChatUserInfo.setGroupId(wxMpUser.getGroupId());
                weChatUserInfo.setWeChat(weChatOptional.get());
                weChatUserInfo.setUser(visitor);
                weChatUserInfoRepository.save(weChatUserInfo);

            } catch (WxErrorException e) {
                e.printStackTrace();
            }

        } else {
            visitor = visitorOptional.get();
        }

        // 持久化消息内容到MySQL
        WorkGroup workGroup = weChatOptional.get().getWorkGroup();
        //
        Message message = new Message();
        message.setMid(JpaUtil.randomId());
        message.setWid(workGroup.getWid());
        message.setType(inMessage.getMsgType());
        message.setSessionType(TypeConsts.MESSAGE_SESSION_TYPE_WORK_GROUP);
        message.setStatus(StatusConsts.MESSAGE_STATUS_STORED);
        message.setUser(visitor);
        message.setClient(ClientConsts.CLIENT_WECHAT_MP);

        // 首先查找是否已经存在进行中的会话，如果没有则创建
        Optional<Thread> threadOptional = threadRepository.findFirstByVisitorAndWorkGroupAndAppointedAndClosed(visitor, workGroup, false, false);
        //
        Thread thread;
        if (threadOptional.isPresent()) {
            thread = threadOptional.get();
        } else {
            thread = new Thread();
            // FIXME: 集群分布，当多个实例存在，tid理论上有可能重复？另外单独部署一个服务？
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

        switch (inMessage.getMsgType()) {
            case TypeConsts.MESSAGE_TYPE_TEXT:
                message.setContent(inMessage.getContent());
                thread.setContent(inMessage.getContent());
                break;
            case TypeConsts.MESSAGE_TYPE_IMAGE:
                // https://github.com/Wechat-Group/weixin-java-tools/blob/master/weixin-java-mp/src/main/java/me/chanjar/weixin/mp/api/impl/WxMpMaterialServiceImpl.java
                message.setPicUrl(inMessage.getPicUrl());
                // 下载图片，存在在imageUrl
                String imageName = inMessage.getFromUser() + "_" + JpaUtil.randomId() + ".png";
                String imageUrl = aliYunOss.saveWeChatImageUrl(imageName, inMessage.getPicUrl());
                message.setImageUrl(imageUrl);
                message.setMediaId(inMessage.getMediaId());
                thread.setContent("[图片]");
                break;
            case TypeConsts.MESSAGE_TYPE_VOICE:
                // https://github.com/Wechat-Group/weixin-java-tools/blob/master/weixin-java-mp/src/main/java/me/chanjar/weixin/mp/api/impl/WxMpMaterialServiceImpl.java
                String voiceName = inMessage.getFromUser() + "_" + JpaUtil.randomId() + "." + inMessage.getFormat();
                WxMpMaterialService mpMaterialService = weChatMpService.getMpService(token).getMaterialService();
                try {
                    File voiceFile = mpMaterialService.mediaDownload(inMessage.getMediaId());
                    String voiceUrl = aliYunOss.saveWeChatVoice(voiceName, voiceFile);
                    message.setVoiceUrl(voiceUrl);
                    message.setMediaId(inMessage.getMediaId());
                    message.setFormat(inMessage.getFormat());
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }
                thread.setContent("[语音]");
                break;
            case TypeConsts.MESSAGE_TYPE_VIDEO:
            case TypeConsts.MESSAGE_TYPE_SHORT_VIDEO:
                String videoName = inMessage.getFromUser() + "_" + JpaUtil.randomId();
                String videoThumbName = inMessage.getFromUser() + "_" + JpaUtil.randomId();
                try {
                    File videoFile = getMaterialService(token).mediaDownload(inMessage.getMediaId());
                    String videoUrl = aliYunOss.saveWeChatVideo(videoName, videoFile);
                    message.setVideoOrShortUrl(videoUrl);
                    //
                    File videoThumbFile = getMaterialService(token).mediaDownload(inMessage.getThumbMediaId());
                    String videoThumbUrl = aliYunOss.saveWeChatThumb(videoThumbName, videoThumbFile);
                    message.setVideoOrShortThumbUrl(videoThumbUrl);
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }
                message.setMediaId(inMessage.getMediaId());
                message.setThumbMediaId(inMessage.getThumbMediaId());
                thread.setContent("[视频]");
                break;
            case TypeConsts.MESSAGE_TYPE_LOCATION:
                message.setLocationX(inMessage.getLocationX());
                message.setLocationY(inMessage.getLocationY());
                message.setScale(inMessage.getScale());
                message.setLabel(inMessage.getLabel());
                thread.setContent("[地理位置]");
                break;
            case TypeConsts.MESSAGE_TYPE_LINK:
                message.setTitle(inMessage.getTitle());
                message.setDescription(inMessage.getDescription());
                message.setUrl(inMessage.getUrl());
                thread.setContent("[链接]");
                break;
            case TypeConsts.MESSAGE_TYPE_EVENT:
                // FIXME: 访客点击进入公众号页面事件，应该忽略
                // 点击自定义菜单
                if (inMessage.getEvent().equals(TypeConsts.MESSAGE_EVENT_TYPE_CLICK)) {
                    //
                    if (inMessage.getEventKey().equals(TypeConsts.MESSAGE_EVENT_KEY_AGENT)) {
                        // 点击'在线客服'

                    } else if (inMessage.getEventKey().equals(TypeConsts.MESSAGE_EVENT_KEY_ABOUT)) {
                        // 点击 '关于我们'
                    }
                }
                message.setContent(inMessage.getEventKey());
                thread.setContent("[事件]:" + inMessage.getEvent());
                break;
            default:
                logger.info("other MP message type");
                thread.setContent("[其他]");
        }
        messageRepository.save(message);

        // TODO: 智能机器人自动回复

        // TODO: 重构路由模块
        // TODO: 广播给thread
        logger.info("route");
        if (threadOptional.isPresent()) {
            // thread会话已经存在

            // 判断是否为当前会话thread，如果不是，则增加
            if (!thread.isCurrent()) {
                thread.increaseUnreadCount();
            }
            //
            thread.setTimestamp(message.getCreatedAt());
            threadRepository.save(thread);

            // 忽略掉Event类型消息
            if (!inMessage.getMsgType().equals(TypeConsts.MESSAGE_TYPE_EVENT)) {
                // 推送通知
                rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DEFAULT_TOPIC_NAME, MqConsts.TOPIC_THREAD_MESSAGE, message);
                // 自动回复
                messageService.autoReply(message);
            }

        } else {

            // 忽略掉Event类型消息
            if (!inMessage.getMsgType().equals(TypeConsts.MESSAGE_TYPE_EVENT)) {
                // thread会话不存在，则路由给客服
                routeService.route(workGroup, thread, visitor);
            }
        }

        return "";
    }


    private WxMpMaterialService getMaterialService(String token) {
        return weChatMpService.getMpService(token).getMaterialService();
    }




}
