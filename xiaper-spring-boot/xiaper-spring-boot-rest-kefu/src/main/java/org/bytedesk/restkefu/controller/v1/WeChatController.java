package org.bytedesk.restkefu.controller.v1;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.apache.commons.lang3.StringUtils;
import org.bytedesk.jpa.constant.BdConstants;
import org.bytedesk.jpa.constant.TypeConsts;
import org.bytedesk.jpa.model.FuncInfo;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.model.WeChat;
import org.bytedesk.jpa.repository.MiniProgramInfoRepository;
import org.bytedesk.jpa.repository.UserRepository;
import org.bytedesk.jpa.repository.WeChatRepository;
import org.bytedesk.jpa.repository.WeChatUserInfoRepository;
import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.mq.service.wechat.WeChatOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * https://github.com/Wechat-Group/weixin-java-tools
 * https://github.com/Wechat-Group/weixin-java-open-demo
 *
 * 授权事件接收URL:
 * https://wechat.bytedesk.com/wechat/mp/oauth/callback
 *
 * 消息与事件接收URL:
 * https://wechat.bytedesk.com/wechat/mp/msgevent/$APPID$/callback
 *
 * TODO: 对接微信开放平台
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    WeChatOpenService weChatOpenService;

    @Autowired
    WeChatRepository weChatRepository;

    @Autowired
    MiniProgramInfoRepository weChatMiniRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WeChatUserInfoRepository weChatUserInfoRepository;

    /**
     * 跳转到微信授权页面 扫码授权
     * https://wechat.bytedesk.com/wechat/mp/oauth/redirect/{uid}
     *
     * @param response
     */
    @GetMapping("/mp/oauth/redirect")
    public void gotoPreAuthUrl(HttpServletResponse response) {
        try {
            String url = weChatOpenService.getWxOpenComponentService().getPreAuthUrl(BdConstants.WECHAT_OPEN_PLATFORM_OAUTH_CALLBACK_URI);
            response.sendRedirect(url);
        } catch (WxErrorException | IOException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理授权事件Get
     *
     * 授权成功之后回调地址
     *
     * 因为post过来的信息，服务端拿不到user_id, 而且消息一般都是post先到，然后浏览器回调，执行get，
     * 才能够从session中拿到user_id，故基于以上原因(假设)，在此处更新user_id
     * 绑定公众号到管理员所属的默认工作组
     *
     * @param authorizationCode
     * @return
     */
    @GetMapping("/mp/oauth/callback")
    @ResponseBody
    public void oauthCallback(@RequestParam("auth_code") String authorizationCode,
                              HttpSession session, HttpServletResponse response){

        String uid = (String) session.getAttribute("uid");
        logger.info("处理授权事件Get session uid {}", uid);

        Optional<User> userOptional = userRepository.findByUid(uid);

        try {

            WxOpenQueryAuthResult queryAuthResult = weChatOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
            WxOpenAuthorizationInfo authorizationInfo = queryAuthResult.getAuthorizationInfo();
            String authorizerAppId = authorizationInfo.getAuthorizerAppid();
            WxOpenAuthorizerInfoResult authorizerInfoResult = weChatOpenService.getWxOpenComponentService().getAuthorizerInfo(authorizerAppId);
            WxOpenAuthorizerInfo authorizerInfo = authorizerInfoResult.getAuthorizerInfo();

            //
            WeChat weChat;
            Optional<WeChat> weChatMpOptional = weChatRepository.findByAuthorizerAppId(authorizerAppId);
            if (weChatMpOptional.isPresent()) {

                weChat = weChatMpOptional.get();
            } else {
                //
                weChat = new WeChat();
            }

            weChat.setWid(JpaUtil.randomId());
            weChat.setNickname(authorizerInfo.getNickName());
            weChat.setHeadImg(authorizerInfo.getHeadImg());
            weChat.setServiceTypeInfo(authorizerInfo.getServiceTypeInfo());
            weChat.setVerifyTypeInfo(authorizerInfo.getVerifyTypeInfo());
            weChat.setUserName(authorizerInfo.getUserName());
            weChat.setPrincipalName(authorizerInfo.getPrincipalName());
            weChat.setAlias(authorizerInfo.getAlias());
            weChat.setQrcodeUrl(authorizerInfo.getQrcodeUrl());
            weChat.setSignature(authorizerInfo.getSignature());

            weChat.setBusinessInfoOpenPay(authorizerInfo.getBusinessInfo().get("open_pay"));
            weChat.setBusinessInfoOpenShake(authorizerInfo.getBusinessInfo().get("open_shake"));
            weChat.setBusinessInfoOpenScan(authorizerInfo.getBusinessInfo().get("open_scan"));
            weChat.setBusinessInfoOpenCard(authorizerInfo.getBusinessInfo().get("open_card"));
            weChat.setBusinessInfoOpenStore(authorizerInfo.getBusinessInfo().get("open_store"));

            weChat.setAuthorizationCode(authorizationCode);
            weChat.setAuthorizerAppId(authorizationInfo.getAuthorizerAppid());
            weChat.setAuthorizerAccessToken(authorizationInfo.getAuthorizerAccessToken());
            weChat.setAuthorizerRefreshToken(authorizationInfo.getAuthorizerRefreshToken());
            weChat.setExpiresIn(authorizationInfo.getExpiresIn());

            // 权限相关
            Iterator iterator = authorizationInfo.getFuncInfo().iterator();
            while (iterator.hasNext()) {
                Integer funcScopeCategoryId = (Integer) iterator.next();

                FuncInfo funcinfo = new FuncInfo();
                funcinfo.setFuncScopeCategoryId(funcScopeCategoryId);
                weChat.getFuncInfos().add(funcinfo);
            }

            // 小程序相关
            if (authorizerInfoResult.isMiniProgram()) {
                weChat.setMiniProgram(false);
            } else {
                weChat.setMiniProgram(true);
                // TODO: 小程序信息
            }

            // 绑定默认工作组
            weChat.setWorkGroup(userOptional.get().defaultWorkGroup());
            // 设置管理员账号
            weChat.setUser(userOptional.get());

            weChatRepository.save(weChat);

        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }

        // 跳转到管理后台
        String url = "https://" + userOptional.get().getSubDomain() + BdConstants.WECHAT_OPEN_PLATFORM_ADMIN_CALLBACK_URL;
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理授权事件POST
     *
     * 授权事件接收URL:
     * https://wechat.bytedesk.com/wechat/mp/oauth/callback
     *
     * 授权过程中：
     * 1. 首先接收到POST请求结果，
     * 2. 最后是GET请求，在GET函数里面执行页面跳转
     *
     * @param requestBody
     * @param timestamp
     * @param nonce
     * @param signature
     * @param encType
     * @param msgSignature
     * @return
     */
    @PostMapping("/mp/oauth/callback")
    public Object oauthCallback(@RequestBody(required = false) String requestBody,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce,
                                @RequestParam("signature") String signature,
                                @RequestParam(name = "encrypt_type", required = false) String encType,
                                @RequestParam(name = "msg_signature", required = false) String msgSignature,
                                HttpSession session) {

        logger.info("授权事件接收URL：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[{}] session uid {}",
                signature, encType, msgSignature, timestamp, nonce, requestBody, session.getAttribute("uid"));

//        if (!StringUtils.equalsIgnoreCase("aes", encType)
//                || !weChatOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
//            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
//        }
//
//        // aes加密的消息
//        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody,
//                weChatOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
//        this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
//
//        String authorizationCode = inMessage.getAuthorizationCode();
//
//        switch (inMessage.getInfoType()) {
//            case BdConstants.WECHAT_OPEN_PLATFORM_INFO_TYPE_AUTHORIZED:
//                logger.info("授权成功");
//
//                break;
//            case BdConstants.WECHAT_OPEN_PLATFORM_INFO_TYPE_UPDATE_AUTHORIZED:
//                logger.info("授权更新");
//
//                break;
//            case BdConstants.WECHAT_OPEN_PLATFORM_INFO_TYPE_UNAUTHORIZED:
//                logger.info("授权取消");
//
//                WxOpenQueryAuthResult queryAuthResult = null;
//                try {
//
//                    queryAuthResult = weChatOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
//                    WxOpenAuthorizationInfo authorizationInfo = queryAuthResult.getAuthorizationInfo();
//
//                    weChatRepository.deleteByAuthorizerAppId(authorizationInfo.getAuthorizerAppid());
//
//                } catch (WxErrorException e) {
//                    e.printStackTrace();
//                }
//
//                break;
//            case BdConstants.WECHAT_OPEN_PLATFORM_INFO_TYPE_COMPONENT_VERIFY_TICKET:
//                logger.info("心跳ticket");
//
//                break;
//            default:
//                logger.info("oauthCallback 其他");
//        }
//
//        try {
//            // 用于第三方包内部更新component ticket信息
//            String out = weChatOpenService.getWxOpenComponentService().route(inMessage);
//            this.logger.debug("\n组装回复信息：{}", out);
//        } catch (WxErrorException e) {
//            this.logger.error("receive_ticket", e);
//        }

        return "success";
    }

    /**
     * 消息与事件接收URL:
     * https://wechat.bytedesk.com/wechat/mp/msgevent/$APPID$/callback
     *
     * 公众号消息与事件接收URL:
     * http://wechat.kefudashi.com/wechat/mp/msgevent/$APPID$/callback
     *
     * 接收普通消息推送文档
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453
     *
     * 接收时间推送文档
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454
     *
     * @param requestBody
     * @param appId
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return
     */
    @RequestMapping("/mp/msgevent/{appId}/callback")
    public Object msgEventCallback(@RequestBody(required = false) String requestBody,
                           @PathVariable("appId") String appId,
                           @RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("openid") String openid,
                           @RequestParam("encrypt_type") String encType,
                           @RequestParam("msg_signature") String msgSignature) {

        logger.info("消息与事件接收URL：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType)
                || !weChatOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody, weChatOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);

        // 找到对应的公众号
        Optional<WeChat> weChatOptional = weChatRepository.findByUserName(inMessage.getToUser());
        if (!weChatOptional.isPresent()) {
            // 公众号不存在，出现此种情况为bug，需要修复
            return "";
        }

        // 用户取消关注，忽略
        if (inMessage.getMsgType().equals(TypeConsts.MESSAGE_TYPE_EVENT)
                && inMessage.getEventKey().equals(TypeConsts.MESSAGE_EVENT_TYPE_UNSUBSCRIBE)) {
            return "";
        }

        return "success";
    }


    /**
     * 全网发布接入检测说明：
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318611&lang=zh_CN
     *
     * 使用：
     * return fullNetPublish(inMessage, requestBody, appId, signature, timestamp, nonce, openid, encType, msgSignature);
     *
     * 自动化测试的专用测试公众号的信息如下：
     *
     * （1）appid： wx570bc396a51b8ff8
     *
     * （2）Username： gh_3c884a361561
     *
     * 自动化测试的专用测试小程序的信息如下：
     *
     * （1）appid：wxd101a85aa106f53e
     *
     * （2）Username： gh_8dad206e9538
     *
     * @param requestBody
     * @param appId
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return
     */
    public Object fullNetPublish(WxMpXmlMessage inMessage, String requestBody, String appId, String signature,
                                 String timestamp, String nonce, String openid, String encType, String msgSignature) {

        String out = "";

        // 全网发布测试用例
        if (StringUtils.equalsAnyIgnoreCase(appId, "wxd101a85aa106f53e", "wx570bc396a51b8ff8")) {

            try {

                if (StringUtils.equals(inMessage.getMsgType(), "text")) {

                    if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {

                        out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                                WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                        .fromUser(inMessage.getToUser())
                                        .toUser(inMessage.getFromUser())
                                        .build(),
                                weChatOpenService.getWxOpenConfigStorage()
                        );

                    } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {

                        String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
                        WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser()).build();
                        weChatOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                    }

                } else if (StringUtils.equals(inMessage.getMsgType(), "event")) {

                    WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback").toUser(inMessage.getFromUser()).build();
                    weChatOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);

                }

            } catch (WxErrorException e) {
                logger.error("callback", e);
            }

        } else {

            WxMpXmlOutMessage outMessage = weChatOpenService.getWxOpenMessageRouter().route(inMessage, appId);

            if(outMessage != null){

                out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outMessage, weChatOpenService.getWxOpenConfigStorage());
            }
        }

        return out;

    }

    /**
     * 公众号消息 推送
     *
     * @param map
     */
    @PostMapping("/mp/push")
    @ResponseBody
    public void mpPush(@RequestBody Map map) {

        logger.info("mp push");


    }


    /**
     * 小程序 推送
     *
     * @param map
     */
    @PostMapping("/mini/push")
    @ResponseBody
    public void miniPush(@RequestBody Map map) {

        logger.info("mini push");


    }


}






