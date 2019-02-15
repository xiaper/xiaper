package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.model.Ip;
import io.xiaper.jpa.util.JsonResult;
import io.xiaper.jpa.util.ip.IPZone;
import io.xiaper.rest.service.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ip api:
 *
 * ip地址以及对应城市信息
 * http://ip.taobao.com/service/getIpInfo.php?ip=118.26.73.2
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/ip")
public class IpController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IpService ipService;

    /**
     * 获取ip地址:
     * http://47.96.102.83:8000/api/ip/get
     *
     * http://127.0.0.1:8000/api/ip/get
     *
     * @return
     */
    @GetMapping("/get")
    public String getIp() {
        return ipService.getIp();
    }

    /**
     * 获取位置/城市信息
     * 测试请求：
     * http://47.96.102.83:8000/api/ip/info
     *
     * 真实请求：
     * https://www.bytedesk.com/api/ip/info
     *
     * @return
     */
    @GetMapping("/info")
    public JsonResult getInfo() {

        JsonResult jsonResult = new JsonResult();

        Ip ipInfo = ipService.getInfo();

        // 返回结果
        jsonResult.setMessage("获取ip信息成功");
        jsonResult.setStatus_code(200);
        jsonResult.setData(ipInfo);

        return jsonResult;
    }


    /**
     * 基于qqwry纯真ip库匹配ip信息
     *
     * http://127.0.0.1:8000/api/ip/qqwry
     *
     * @return
     */
    @GetMapping("/qqwry")
    public JsonResult getQQWry() {

        JsonResult jsonResult = new JsonResult();

        Ip ip = ipService.getIPZone();

        // 返回结果
        jsonResult.setMessage("获取ip信息成功");
        jsonResult.setStatus_code(200);
        jsonResult.setData(ip);

        return jsonResult;
    }


    /**
     * http://127.0.0.1:8000/api/ip/qqwry/test
     *
     * @return
     */
    @GetMapping("/qqwry/test")
    public JsonResult getQQWryTest() {

        JsonResult jsonResult = new JsonResult();

        IPZone ipZone = ipService.getIPZoneTest1();

        // 返回结果
        jsonResult.setMessage("获取ip信息成功");
        jsonResult.setStatus_code(200);
        jsonResult.setData(ipZone);

        return jsonResult;
    }


    /**
     * 北京
     * http://127.0.0.1:8000/api/ip/qqwry/test2?ip=118.26.73.2
     * 济南
     * http://127.0.0.1:8000/api/ip/qqwry/test2?ip=124.133.129.228
     * 香港
     * http://127.0.0.1:8000/api/ip/qqwry/test2?ip=47.75.179.15
     * 湖北
     * http://127.0.0.1:8000/api/ip/qqwry/test2?ip=171.113.157.8
     * 河北
     * http://127.0.0.1:8000/api/ip/qqwry/test2?ip=60.8.198.94
     * 浙江杭州
     * http://127.0.0.1:8000/api/ip/qqwry/test2?ip=124.160.75.205
     * 广东
     * http://127.0.0.1:8000/api/ip/qqwry/test2?ip=112.96.109.165
     *
     * @return
     */
    @GetMapping("/qqwry/test2")
    public JsonResult getQQWryTest2(@RequestParam(value = "ip") String ip) {

        JsonResult jsonResult = new JsonResult();

        IPZone ipZone = ipService.getIPZoneTest2(ip);

        // 返回结果
        jsonResult.setMessage("获取ip信息成功");
        jsonResult.setStatus_code(200);
        jsonResult.setData(ipZone);

        return jsonResult;
    }



}
