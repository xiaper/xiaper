package io.xiaper.rest.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import io.xiaper.jpa.constant.AvatarConsts;
import io.xiaper.jpa.constant.StatusConsts;
import io.xiaper.jpa.constant.TypeConsts;
import io.xiaper.jpa.constant.WeChatConsts;
import io.xiaper.jpa.model.App;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.WeChat;
import io.xiaper.jpa.repository.AppRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.repository.WeChatRepository;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

/**
 * 应用：网站、app
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/app")
@Api(value="应用", tags="应用接口", produces = "application/json;charset=utf-8")
public class AppController extends BaseController {

    @Autowired
    AppRepository appRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WeChatRepository weChatRepository;

    /**
     * 获取web网站
     *
     * @param principal
     * @return
     */
    @ApiOperation(value="获取web网站", notes="获取web网站描述")
    @ApiImplicitParam(name = "webs", value = "获取用户所有web网站", required = true, dataType = "Json")
    @GetMapping("/webs")
    public JsonResult webs(Principal principal,
                           @RequestParam(value = "page") int page,
                           @RequestParam(value = "size") int size,
                           @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<App> appPage = appRepository.findByUserAndType(adminOptional.get(), TypeConsts.APP_TYPE_WEB, pageable);

            jsonResult.setMessage("获取网站成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(appPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 创建网站
     *
     * @param principal
     * @return
     */
    @PostMapping("/web/create")
    @ResponseBody
    public JsonResult createWeb(Principal principal, @RequestBody Map map) {

        String name = (String) map.get("name");
        String url = (String) map.get("url");
        String description = (String) map.get("description");

        JsonResult jsonResult = new JsonResult();

        //
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 创建App
            App app = new App();
            app.setAid(JpaUtil.randomId());
            app.setName(name);
            app.setUrl(url);
            app.setAvatar(AvatarConsts.DEFAULT_WEB_AVATAR_URL);
            app.setDescription(description);
            app.setKey(JpaUtil.uuid());
            app.setType(TypeConsts.APP_TYPE_WEB);
            app.setStatus(StatusConsts.APP_STATUS_DEBUG);
            app.setDefaulted(false);
            app.setUser(adminOptional.get());
            appRepository.save(app);

            // 返回结果
            jsonResult.setMessage("创建web成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(app);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 更新网站
     *
     * @param principal
     * @return
     */
    @PostMapping("/web/update")
    @ResponseBody
    public JsonResult updateWeb(Principal principal, @RequestBody Map map) {

        String aid = (String) map.get("aid");
        String name = (String) map.get("name");
        String url = (String) map.get("url");
        String description = (String) map.get("description");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<App> appOptional = appRepository.findByAid(aid);
            if (appOptional.isPresent()) {

                //
                appOptional.get().setName(name);
                appOptional.get().setUrl(url);
                appOptional.get().setDescription(description);
                appRepository.save(appOptional.get());

                // 返回结果
                jsonResult.setMessage("更新web成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(appOptional.get());

            } else {

                jsonResult.setMessage("更新web失败-web不存在");
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
     * 删除 网站
     *
     * @param principal
     * @return
     */
    @PostMapping("/web/delete")
    @ResponseBody
    public JsonResult deleteWeb(Principal principal, @RequestBody Map map) {

        String aid = (String) map.get("aid");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<App> appOptional = appRepository.findByAid(aid);
            if (appOptional.isPresent()) {

                //
                appRepository.delete(appOptional.get());

                // 返回结果
                jsonResult.setMessage("删除web成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(aid);

            } else {

                jsonResult.setMessage("删除web失败-web不存在");
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
     * 获取APP
     *
     * @param principal
     * @return
     */
    @GetMapping("/apps")
    public JsonResult apps(Principal principal,
                           @RequestParam(value = "page") int page,
                           @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<App> appPage = appRepository.findByUserAndType(adminOptional.get(), TypeConsts.APP_TYPE_APP, pageable);

            jsonResult.setMessage("获取app成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(appPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 创建app
     *
     * @param principal
     * @return
     */
    @PostMapping("/app/create")
    @ResponseBody
    public JsonResult createApp(Principal principal, @RequestBody Map map) {

        String name = (String) map.get("name");
        String avatar = (String) map.get("avatar");
        String version = (String) map.get("version");
        String tip = (String) map.get("tip");
        String url = (String) map.get("url");
        String status = (String) map.get("status");
        String platform = (String) map.get("platform");
        String description = (String) map.get("description");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 创建App
            App app = new App();
            app.setAid(JpaUtil.randomId());
            app.setName(name);
            app.setVersion(version);
            app.setUrl(url);
            app.setUrl(url);
            app.setAvatar(avatar);
            app.setDescription(description);
            app.setKey(JpaUtil.randomId());
            app.setType(TypeConsts.APP_TYPE_APP);
            app.setStatus(status);
            app.setPlatform(platform);
            app.setDefaulted(false);
            app.setUser(adminOptional.get());
            appRepository.save(app);

            //
            jsonResult.setMessage("创建App成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(app);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 更新app
     *
     * @param principal
     * @return
     */
    @PostMapping("/app/update")
    @ResponseBody
    public JsonResult updateApp(Principal principal, @RequestBody Map map) {

        String aid = (String) map.get("aid");
        String name = (String) map.get("name");
        String avatar = (String) map.get("avatar");
        String version = (String) map.get("version");
        String tip = (String) map.get("tip");
        String url = (String) map.get("url");
        String status = (String) map.get("status");
        String platform = (String) map.get("platform");
        String description = (String) map.get("description");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<App> appOptional = appRepository.findByAid(aid);
            if (appOptional.isPresent()) {

                //
                appOptional.get().setName(name);
                appOptional.get().setAvatar(avatar);
                appOptional.get().setVersion(version);
                appOptional.get().setTip(tip);
                appOptional.get().setUrl(url);
                appOptional.get().setStatus(status);
                appOptional.get().setPlatform(platform);
                appOptional.get().setDescription(description);
                appRepository.save(appOptional.get());

                // 返回结果
                jsonResult.setMessage("更新app成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(appOptional.get());

            } else {

                jsonResult.setMessage("更新app失败-app不存在");
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
     * 删除 app
     *
     * @param principal
     * @return
     */
    @PostMapping("/app/delete")
    @ResponseBody
    public JsonResult deleteApp(Principal principal, @RequestBody Map map) {

        String aid = (String) map.get("aid");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<App> appOptional = appRepository.findByAid(aid);
            if (appOptional.isPresent()) {

                //
                appRepository.delete(appOptional.get());

                // 返回结果
                jsonResult.setMessage("删除app成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(aid);

            } else {

                jsonResult.setMessage("删除app失败-app不存在");
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
     * 获取公众号
     *
     * @param principal
     * @return
     */
    @GetMapping("/mps")
    public JsonResult mps(Principal principal,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<WeChat> appPage = weChatRepository.findByUserAndMiniProgram(adminOptional.get(),  false, pageable);

            jsonResult.setMessage("获取公众号成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(appPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 创建 公众号
     *
     * @param principal
     * @return
     */
    @PostMapping("/mp/create")
    @ResponseBody
    public JsonResult createMp(Principal principal, @RequestBody Map map) {

        String nickname = (String) map.get("nickname");
        String number = (String) map.get("number");
        String description = (String) map.get("description");
        String company = (String) map.get("company");
        String original_id = (String) map.get("originalId");
        String app_id = (String) map.get("appId");
        String app_secret = (String) map.get("appSecret");
        String aes_key = (String) map.get("aesKey");
        String encode_type = (String) map.get("encodeType");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            String token = JpaUtil.randomId();
            //
            WeChat weChat = new WeChat();
            weChat.setWid(token);
            weChat.setNickname(nickname);
            weChat.setWxNumber(number);
            weChat.setDescription(description);
            weChat.setCompany(company);
            weChat.setUserName(original_id);
            weChat.setAppId(app_id);
            weChat.setAppSecret(app_secret);
            weChat.setAesKey(aes_key);
            weChat.setEncodeType(encode_type);
            weChat.setUrl(WeChatConsts.WECHAT_MP_PUSH_URL + "/" + token);
            //
            weChat.setMiniProgram(false);
            weChat.setToken(token);
            weChat.setUser(adminOptional.get());
            // 设置工作组
            weChat.setWorkGroup(adminOptional.get().defaultWorkGroup());

            // 获取access_token等
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
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
            //
            weChatRepository.save(weChat);

            // 返回结果
            jsonResult.setMessage("创建公众号成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(weChat);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 更新 公众号
     *
     * @param principal
     * @return
     */
    @PostMapping("/mp/update")
    @ResponseBody
    public JsonResult updateMp(Principal principal, @RequestBody Map map) {

        String wid = (String) map.get("wid");
        String nickname = (String) map.get("nickname");
        String number = (String) map.get("number");
        String description = (String) map.get("description");
        String company = (String) map.get("company");
        String original_id = (String) map.get("originalId");
        String app_id = (String) map.get("appId");
        String app_secret = (String) map.get("appSecret");
        String aes_key = (String) map.get("aesKey");
        String encode_type = (String) map.get("encodeType");

        JsonResult jsonResult = new JsonResult();

        //
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<WeChat> weChatOptional = weChatRepository.findByWid(wid);
            if (weChatOptional.isPresent()) {

                //
                weChatOptional.get().setNickname(nickname);
                weChatOptional.get().setWxNumber(number);
                weChatOptional.get().setDescription(description);
                weChatOptional.get().setCompany(company);
                weChatOptional.get().setUserName(original_id);
                weChatOptional.get().setAppId(app_id);
                weChatOptional.get().setAppSecret(app_secret);
                weChatOptional.get().setAesKey(aes_key);
                weChatOptional.get().setEncodeType(encode_type);

                //
                weChatRepository.save(weChatOptional.get());

                // 返回结果
                jsonResult.setMessage("更新公众号成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(weChatOptional.get());

            } else {

                jsonResult.setMessage("更新公众号失败-wid不存在");
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
     * 删除 公众号
     *
     * @param principal
     * @return
     */
    @PostMapping("/mp/delete")
    @ResponseBody
    public JsonResult deleteMp(Principal principal, @RequestBody Map map) {

        String wid = (String) map.get("wid");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<WeChat> weChatOptional = weChatRepository.findByWid(wid);
            if (weChatOptional.isPresent()) {

                //
                weChatRepository.delete(weChatOptional.get());

                // 返回结果
                jsonResult.setMessage("删除公众号成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(wid);

            } else {

                jsonResult.setMessage("删除公众号失败-wid不存在");
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
     * 获取小程序
     *
     * @param principal
     * @return
     */
    @GetMapping("/minis")
    public JsonResult minis(Principal principal,
                            @RequestParam(value = "page") int page,
                            @RequestParam(value = "size") int size) {

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            // 分页查询
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<WeChat> appPage = weChatRepository.findByUserAndMiniProgram(adminOptional.get(),  true, pageable);

            jsonResult.setMessage("获取小程序成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(appPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 创建 小程序
     *
     * @param principal
     * @return
     */
    @PostMapping("/mini/create")
    @ResponseBody
    public JsonResult createMini(Principal principal, @RequestBody Map map) {

        String nickname = (String) map.get("nickname");
        String description = (String) map.get("description");
        String company = (String) map.get("company");
        String original_id = (String) map.get("originalId");
        String app_id = (String) map.get("appId");
        String app_secret = (String) map.get("appSecret");
        String aes_key = (String) map.get("aesKey");
        String encode_type = (String) map.get("encodeType");
        String data_type = (String) map.get("dataType");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            String token = JpaUtil.randomId();

            //
            WeChat weChat = new WeChat();
            weChat.setWid(JpaUtil.randomId());
            weChat.setNickname(nickname);
            weChat.setDescription(description);
            weChat.setCompany(company);
            weChat.setUserName(original_id);
            weChat.setAppId(app_id);
            weChat.setAppSecret(app_secret);
            weChat.setAesKey(aes_key);
            weChat.setEncodeType(encode_type);
            weChat.setDataType(data_type);
            weChat.setUrl(WeChatConsts.WECHAT_MINI_PUSH_URL + "/" + token);
            //
            weChat.setMiniProgram(true);
            weChat.setToken(token);
            weChat.setUser(adminOptional.get());
            // 设置工作组
            weChat.setWorkGroup(adminOptional.get().defaultWorkGroup());

            // TODO: 获取access_token等
//            WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
//            configStorage.setAppId(weChat.getAppId());
//            configStorage.setSecret(weChat.getAppSecret());
//            configStorage.setToken(weChat.getToken());
//            configStorage.setAesKey(weChat.getAesKey());
//
//            WxMiniService wxMpService = new WxMpServiceImpl();
//            wxMpService.setWxMpConfigStorage(configStorage);
//            //
//            try {
//                weChat.setAuthorizerAccessToken(wxMpService.getAccessToken(true));
//            } catch (WxErrorException e) {
//                e.printStackTrace();
//            }

            //
            weChatRepository.save(weChat);

            // 返回结果
            jsonResult.setMessage("创建公众号成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(weChat);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }

    /**
     * 更新 小程序
     *
     * @param principal
     * @return
     */
    @PostMapping("/mini/update")
    @ResponseBody
    public JsonResult updateMini(Principal principal, @RequestBody Map map) {

        String wid = (String) map.get("wid");
        String nickname = (String) map.get("nickname");
        String description = (String) map.get("description");
        String company = (String) map.get("company");
        String original_id = (String) map.get("originalId");
        String app_id = (String) map.get("appId");
        String app_secret = (String) map.get("appSecret");
        String aes_key = (String) map.get("aesKey");
        String encode_type = (String) map.get("encodeType");
        String data_type = (String) map.get("dataType");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {


            Optional<WeChat> weChatOptional = weChatRepository.findByWid(wid);
            if (weChatOptional.isPresent()) {

                //
                weChatOptional.get().setNickname(nickname);
                weChatOptional.get().setDescription(description);
                weChatOptional.get().setCompany(company);
                weChatOptional.get().setUserName(original_id);
                weChatOptional.get().setAppId(app_id);
                weChatOptional.get().setAppSecret(app_secret);
                weChatOptional.get().setAesKey(aes_key);
                weChatOptional.get().setEncodeType(encode_type);
                weChatOptional.get().setDataType(data_type);

                //
                weChatRepository.save(weChatOptional.get());

                // 返回结果
                jsonResult.setMessage("更新小程序成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(weChatOptional.get());

            } else {

                jsonResult.setMessage("更新小程序失败-wid不存在");
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
     * 删除 小程序
     *
     * @param principal
     * @return
     */
    @PostMapping("/mini/delete")
    @ResponseBody
    public JsonResult deleteMini(Principal principal, @RequestBody Map map) {

        String wid = (String) map.get("wid");

        JsonResult jsonResult = new JsonResult();

        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {

            Optional<WeChat> weChatOptional = weChatRepository.findByWid(wid);
            if (weChatOptional.isPresent()) {

                //
                weChatRepository.delete(weChatOptional.get());

                // 返回结果
                jsonResult.setMessage("删除小程序成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(wid);

            } else {

                jsonResult.setMessage("删除小程序失败-wid不存在");
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
