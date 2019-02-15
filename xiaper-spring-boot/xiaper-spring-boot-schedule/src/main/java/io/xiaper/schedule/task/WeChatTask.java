package io.xiaper.schedule.task;


import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.apache.curator.framework.CuratorFramework;
import io.xiaper.jpa.constant.BdConstants;
import io.xiaper.jpa.model.WeChat;
import io.xiaper.jpa.repository.WeChatRepository;
import org.bytedesk.mq.service.wechat.WeChatOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * 定时刷新 微信开放平台 token
 *
 * FIXME: 对于分布式集群部署的情况，需要保证同时只有一个实例执行定时任务
 * ZooKeeper 分布式锁
 *
 * 参考源码：
 * https://github.com/wechat-group/weixin-java-tools/blob/master/weixin-java-open/src/main/java/me/chanjar/weixin/open/api/impl/WxOpenComponentServiceImpl.java
 *
 * @author bytedesk.com
 */
@Component
public class WeChatTask {

    @Autowired
    WeChatOpenService weChatOpenService;

    @Autowired
    WeChatRepository weChatRepository;

    @Autowired
    CuratorFramework zkClient;

    /**
     * 60分钟，即一个小时刷新一次
     *
     * TODO: 持久化到redis
     */
    // @Scheduled(fixedRate = 1000 * 60 * 60)
    public void refreshOpenToken() {

        if (BdConstants.IS_DEBUG) {
            return;
        }

        List<WeChat> weChatList = weChatRepository.findAll();
        Iterator iterator = weChatList.iterator();
        while (iterator.hasNext()) {

            WeChat weChat = (WeChat) iterator.next();
            //
            try {

                String access_token = weChatOpenService.getWxOpenComponentService().getAuthorizerAccessToken(weChat.getAuthorizerAppId(), false);
                String refresh_token = weChatOpenService.getWxOpenComponentService().getWxOpenConfigStorage().getAuthorizerRefreshToken(weChat.getAuthorizerAppId());

                weChat.setAuthorizerAccessToken(access_token);
                weChat.setAuthorizerRefreshToken(refresh_token);

            } catch (WxErrorException e) {
                e.printStackTrace();
            }

            weChatRepository.save(weChat);
        }
    }

    /**
     * 60分钟，即一个小时刷新一次
     *
     * TODO: 持久化到redis
     *
     * FIXME: 运行多个服务器实例的情况下，需要通过zookeeper协同统一，只执行一次
     *
     */
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void refreshMpToken() {

        List<WeChat> weChatList = weChatRepository.findAll();
        Iterator iterator = weChatList.iterator();
        while (iterator.hasNext()) {
            WeChat weChat = (WeChat) iterator.next();

            if (!weChat.isMiniProgram()) {

                // 公众号
                WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
                configStorage.setAppId(weChat.getAppId());
                configStorage.setSecret(weChat.getAppSecret());
                configStorage.setToken(weChat.getToken());
                configStorage.setAesKey(weChat.getAesKey());

                WxMpService wxMpService = new WxMpServiceImpl();
                wxMpService.setWxMpConfigStorage(configStorage);

                //
                try {
                    weChat.setAuthorizerAccessToken(wxMpService.getAccessToken(true));
                    weChatRepository.save(weChat);
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }
            } else {

                // 小程序
            }
        }

    }



}
