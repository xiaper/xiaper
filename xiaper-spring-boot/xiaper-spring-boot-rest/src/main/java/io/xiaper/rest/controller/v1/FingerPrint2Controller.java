package io.xiaper.rest.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xiaper.jpa.constant.ClientConsts;
import io.xiaper.jpa.model.FingerPrint2;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.ua.UserAgent;
import io.xiaper.jpa.repository.FingerPrint2Repository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * 设备唯一指纹
 * TODO: 增加ip、pv、地域信息
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/fingerprint2")
public class FingerPrint2Controller {

    private static final Logger logger = LoggerFactory.getLogger(FingerPrint2Controller.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FingerPrint2Repository fingerPrint2Repository;

    /**
     * 保存浏览器指纹信息
     *
     * TODO: 优化保存到redis
     * TODO: 获取ip，地理位置
     *
     * @param principal
     * @return
     */
    @PostMapping("/browser")
    @ResponseBody
    public JsonResult browser(Principal principal, @RequestParam Map map) {

        JsonResult jsonResult = new JsonResult();
        //
        Optional<User> userOptional = userRepository.findByUsername(principal.getName());
        if (userOptional.isPresent()) {

             // 分解user_agent
             String userAgentString = (String) map.get("user_agent");
             if (userAgentString != null) {

                 ObjectMapper mapper = new ObjectMapper();

                 try {
                     UserAgent userAgent = mapper.readValue(userAgentString, UserAgent.class);
                     // logger.info("user-agent {}: ",userAgent.toString());

                     saveKeyValue("","ua", userAgent.getUa(), userOptional.get());

                     saveKeyValue("操作系统","os-name", userAgent.getOs().getName(), userOptional.get());
                     saveKeyValue("操作系统版本","os-version", userAgent.getOs().getVersion(), userOptional.get());

                     saveKeyValue("浏览器引擎","engine-name", userAgent.getEngine().getName(), userOptional.get());
                     saveKeyValue("浏览器引擎版本","engine-version", userAgent.getEngine().getVersion(), userOptional.get());

                     saveKeyValue("浏览器主版本","browser-major", userAgent.getBrowser().getMajor(), userOptional.get());
                     saveKeyValue("浏览器","browser-name", userAgent.getBrowser().getName(), userOptional.get());
                     saveKeyValue("浏览器版本","browser-version", userAgent.getBrowser().getVersion(), userOptional.get());

                     saveKeyValue("","cpu-architecture", userAgent.getCpu().getArchitecture(), userOptional.get());

                     saveKeyValue("","device-model", userAgent.getDevice().getModel(), userOptional.get());
                     saveKeyValue("","device-type", userAgent.getDevice().getType(), userOptional.get());
                     saveKeyValue("","device-vendor", userAgent.getDevice().getVendor(), userOptional.get());

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

            //
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry)it.next();
                String key = pair.getKey().toString();
                String value = pair.getValue().toString();

                saveKeyValue("", key, value, userOptional.get());
            }

            jsonResult.setMessage("设置成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(true);

        } else {

            jsonResult.setMessage("用户不存在");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }





    /**
     * 客服获取访客 用户信息 + 设备信息
     *
     * @param principal
     * @param uid
     * @param client
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "uid") String uid,
                          @RequestParam(value = "client") String client) {

        logger.info("uid {},  client {}", uid, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUid(uid);
            if (visitorOptional.isPresent()) {

                Map infoMap = formatMap(visitorOptional.get());

                //
                jsonResult.setMessage("获取用户信息成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(infoMap);

            } else {

                jsonResult.setMessage("获取访客用户信息和设备信息-访客不存在");
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
     * 用户获取自己key/value信息
     *
     * @param principal
     * @param client
     * @return
     */
    @GetMapping("/userInfo")
    public JsonResult getUserInfo(Principal principal,
                                  @RequestParam(value = "client") String client) {

        logger.info("client {}", client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                Map infoMap = formatMap(userOptional.get());

                //
                jsonResult.setMessage("获取用户key/value信息成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(infoMap);

            } else {

                jsonResult.setMessage("获取用户key/value信息-访客不存在");
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
     * 开发者自定义设置用户信息
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/userInfo")
    @ResponseBody
    public JsonResult setUserInfo(Principal principal, @RequestBody Map map) {

        String name = (String) map.get("name");
        String key = (String) map.get("key");
        String value = (String) map.get("value");

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> userOptional = userRepository.findByUsername(principal.getName());
            if (userOptional.isPresent()) {

                //
                Optional<FingerPrint2> fingerPrint2Optional = fingerPrint2Repository.findByVisitorAndKeyAndSystem(userOptional.get(), key, false);
                if (fingerPrint2Optional.isPresent()) {

                    fingerPrint2Optional.get().setName(name);
                    fingerPrint2Optional.get().setValue(value);
                    fingerPrint2Repository.save(fingerPrint2Optional.get());

                    //
                    jsonResult.setMessage("设置访客信息成功1");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(fingerPrint2Optional.get());

                } else {

                    FingerPrint2 fingerPrint2 = new FingerPrint2();
                    fingerPrint2.setName(name);
                    fingerPrint2.setKey(key);
                    fingerPrint2.setValue(value);
                    fingerPrint2.setVisitor(userOptional.get());
                    fingerPrint2.setSystem(false);
                    fingerPrint2Repository.save(fingerPrint2);

                    //
                    jsonResult.setMessage("设置访客信息成功2");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(fingerPrint2);
                }

            } else {

                jsonResult.setMessage("设置访客信息-访客账号不存在");
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
     * 客服端备注访客信息
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/note")
    @ResponseBody
    public JsonResult setNote(Principal principal, @RequestBody Map map) {

        String uid = (String) map.get("uid");
        String value = (String) map.get("value");

        // 定义固定key
        String name = "备注";
        String key = "agent_note";

        //
        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            // 客服
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 没有在表中增加字段，而是将备注者的真实姓名添加到备注内容后面
                value = value + "(" +adminOptional.get().getRealName()+ ")";

                // 访客
                Optional<User> visitorOptional = userRepository.findByUid(uid);
                if (visitorOptional.isPresent()) {

                    //
                    Optional<FingerPrint2> fingerPrint2Optional = fingerPrint2Repository.findByVisitorAndKeyAndSystem(visitorOptional.get(), key, false);
                    if (fingerPrint2Optional.isPresent()) {

                        fingerPrint2Optional.get().setName(name);
                        fingerPrint2Optional.get().setValue(value);
                        fingerPrint2Repository.save(fingerPrint2Optional.get());

                        // 返回结果
                        jsonResult.setMessage("备注访客信息成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(fingerPrint2Optional.get());

                    } else {

                        FingerPrint2 fingerPrint2 = new FingerPrint2();
                        fingerPrint2.setName(name);
                        fingerPrint2.setKey(key);
                        fingerPrint2.setValue(value);
                        fingerPrint2.setVisitor(visitorOptional.get());
                        fingerPrint2.setSystem(false);
                        fingerPrint2Repository.save(fingerPrint2);

                        // 返回结果
                        jsonResult.setMessage("备注访客信息成功");
                        jsonResult.setStatus_code(200);
                        jsonResult.setData(fingerPrint2);
                    }

                } else {

                    jsonResult.setMessage("备注访客信息失败-访客uid账号不存在");
                    jsonResult.setStatus_code(-2);
                    jsonResult.setData(false);
                }

            } else {

                jsonResult.setMessage("备注访客信息失败-管理员账号不存在");
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
     * 设置访客设备信息
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/deviceInfo")
    @ResponseBody
    public JsonResult setDeviceInfo(Principal principal,
                                  @RequestBody Map map) {

        String osName = (String) map.get("os-name");
        String osVersion = (String) map.get("os-version");

        String deviceName = (String) map.get("device-name");
        String deviceModel = (String) map.get("device-model");
        String deviceLocalModel = (String) map.get("device-localmodel");

        String appName = (String) map.get("app-name");
        String appVersion = (String) map.get("app-version");
        String appLanguage = (String) map.get("app-language");
        String appCountry = (String) map.get("app-country");

        //
        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Optional<User> visitorOptional = userRepository.findByUsername(principal.getName());
            if (visitorOptional.isPresent()) {

                //
                saveKeyValue("操作系统", "os-name", osName, visitorOptional.get());
                saveKeyValue("操作系统版本", "os-version", osVersion, visitorOptional.get());

                saveKeyValue("设备名称", "device-name", deviceName, visitorOptional.get());
                saveKeyValue("设备模型", "device-model", deviceModel, visitorOptional.get());
                saveKeyValue("设备本地模型", "device-localmodel", deviceLocalModel, visitorOptional.get());

                saveKeyValue("应用名称", "app-name", appName, visitorOptional.get());
                saveKeyValue("应用版本", "app-version", appVersion, visitorOptional.get());
                saveKeyValue("应用语言", "app-language", appLanguage, visitorOptional.get());
                saveKeyValue("应用国家", "app-country", appCountry, visitorOptional.get());

                //
                jsonResult.setMessage("设置访客设备信息成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(visitorOptional.get());

            } else {

                jsonResult.setMessage("设置访客设备信息-访客账号不存在");
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
     * 保存用户信息
     *
     * @param key
     * @param value
     * @param user
     */
    private void saveKeyValue(String name, String key, String value, User user) {

        Optional<FingerPrint2> fingerPrint2Optional = fingerPrint2Repository.findFirstByKeyAndVisitor(key, user);
        if (fingerPrint2Optional.isPresent()) {

            fingerPrint2Optional.get().setName(name);
            fingerPrint2Optional.get().setKey(key);
            fingerPrint2Optional.get().setValue(value);
            fingerPrint2Optional.get().setSystem(true);
            fingerPrint2Repository.save(fingerPrint2Optional.get());

        } else {

            FingerPrint2 fingerPrint2 = new FingerPrint2();
            fingerPrint2.setName(name);
            fingerPrint2.setKey(key);
            fingerPrint2.setValue(value);
            fingerPrint2.setVisitor(user);
            fingerPrint2.setSystem(true);
            fingerPrint2Repository.save(fingerPrint2);
        }
    }


    private Map formatMap(User visitor) {

        Set<String> keySet = new HashSet<>(16);
        if (visitor.getClient() == null) {

            keySet = new HashSet<>(Arrays.asList("os-name", "os-version"));

        } else if (visitor.getClient().equals(ClientConsts.CLIENT_WEB)) {

            keySet = new HashSet<>(Arrays.asList("os-name", "os-version", "browser-name", "browser-version"));

        } else if (visitor.getClient().equals(ClientConsts.CLIENT_ANDROID)) {

            keySet = new HashSet<>(Arrays.asList("os-name", "os-version"));

        } else if (visitor.getClient().equals(ClientConsts.CLIENT_IOS)) {

            keySet = new HashSet<>(Arrays.asList("os-name", "os-version"));

        } else if (visitor.getClient().equals(ClientConsts.CLIENT_WECHAT_MP)) {

            keySet = new HashSet<>(Arrays.asList("os-name", "os-version"));

        } else if (visitor.getClient().equals(ClientConsts.CLIENT_WECHAT_MINI)) {

            keySet = new HashSet<>(Arrays.asList("os-name", "os-version"));
        }

        // 系统定义
        Set<FingerPrint2> fingerPrint2SystemSet = fingerPrint2Repository.findByVisitorAndKeyIn(visitor, keySet);
        // 开发者自定义
        Set<FingerPrint2> fingerPrint2UserSet = fingerPrint2Repository.findByVisitorAndSystem(visitor, false);

        // 合并
        Set<FingerPrint2> fingerPrint2Set = new HashSet<>(fingerPrint2SystemSet);
        fingerPrint2Set.addAll(fingerPrint2UserSet);

        //
        Map infoMap = new HashMap(2);
        infoMap.put("nickname", visitor.getNickname());
        infoMap.put("fingerPrints", fingerPrint2Set);

        return infoMap;
    }

}
