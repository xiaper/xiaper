package org.bytedesk.rest.service;

import org.bytedesk.jpa.constant.BdConstants;
import org.bytedesk.jpa.model.Ip;
import org.bytedesk.jpa.model.ip.IpInfo;
import org.bytedesk.jpa.repository.IpRepository;
import org.bytedesk.jpa.util.ip.IPZone;
import org.bytedesk.jpa.util.ip.QQWry;
import org.bytedesk.rest.handler.IpMappingJackson2HttpMessageConverter;
import org.bytedesk.rest.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * 获取ip，已经ip对应地理位置等信息
 *
 * @author bytedesk.com
 */
@Service
public class IpService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IpRepository ipRepository;

    @Autowired
    HttpServletRequest request;

    /**
     * 获取ip地址:
     * http://47.96.102.83:8000/api/ip/get
     *
     * 其他：
     * 北京ip：
     * http://ip.taobao.com/service/getIpInfo.php?ip=118.26.73.2
     * 济南ip：
     * http://ip.taobao.com/service/getIpInfo.php?ip=124.133.129.228
     * 香港ip：
     * http://ip.taobao.com/service/getIpInfo.php?ip=47.75.179.15
     * 武汉ip：
     * http://ip.taobao.com/service/getIpInfo.php?ip=171.113.157.8
     *
     * data: {
     * ip: "171.113.157.8",
     * country: "中国",
     * area: "",
     * region: "湖北",
     * city: "武汉",
     * county: "XX",
     * isp: "电信",
     * country_id: "CN",
     * area_id: "",
     * region_id: "420000",
     * city_id: "420100",
     * county_id: "xx",
     * isp_id: "100017"
     * }
     *
     * request.getRemoteAddr() : 0:0:0:0:0:0:0:1
     *
     * @return
     */
    public String getIp() {
        return Util.getIpAddress(request);
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
    public Ip getInfo() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new IpMappingJackson2HttpMessageConverter());

        String url = BdConstants.IP_INFO_TAOBAO_URL + this.getIp();
        IpInfo ipInfo = restTemplate.getForObject(url, IpInfo.class);

        // 保存持久化
        Optional<Ip> ipOptional = ipRepository.findByIp(ipInfo.getData().getIp());

        Ip ip;
        if (!ipOptional.isPresent()) {

            ip = new Ip();
            ip.setIp(ipInfo.getData().getIp());
            ip.setCountry(ipInfo.getData().getCountry());
            ip.setArea(ipInfo.getData().getArea());
            ip.setRegion(ipInfo.getData().getRegion());
            ip.setCity(ipInfo.getData().getCity());
            ip.setCounty(ipInfo.getData().getCounty());
            ip.setIsp(ipInfo.getData().getIsp());

            ip.setCountryId(ipInfo.getData().getCountryId());
            ip.setAreaId(ipInfo.getData().getAreaId());
            ip.setRegionId(ipInfo.getData().getRegionId());
            ip.setCityId(ipInfo.getData().getCityId());
            ip.setCountyId(ipInfo.getData().getCountyId());
            ip.setIspId(ipInfo.getData().getIspId());

            ipRepository.save(ip);

        } else {
            ip = ipOptional.get();
        }

        return ip;
    }


    /**
     * 基于qqwry纯真ip库匹配ip信息
     *
     * TODO: 首先匹配QQWry，如果未匹配到，再匹配taobao api
     *
     * @return
     */
    public Ip getIPZone() {

        Ip ip = new Ip();

        try {

            // 首先查询本地数据库是否存在ip
            Optional<Ip> ipOptional = ipRepository.findByIp(this.getIp());
            if (ipOptional.isPresent()) {
                // 存在，则直接返回

                ip = ipOptional.get();

            } else {
                // 数据库中不存在，则首先调用QQWry库

                QQWry qqwry = new QQWry();
                IPZone ipzone = qqwry.findIP(this.getIp());

                // 1. 判断mainInfo是否包含 '省'，split,
                // 2. 如果不含有 '省', 判断是否含有 '市'，
                // 3. 如果含有 '省', 判断 '省' 后面部分是否含有 '市' / '州'
                // 4. 即不含有 '省', 也不含有 '市'，则为 '国家'
                // 5. 例外： '内蒙古'、'新疆'、'宁夏'、'西藏'、'广西'，'香港'，'澳门'

                String zg = "中国";
                String province = "省";
                String city = "市";
                // String zhou = "州"; // 暂时不考虑 '州'
                String nmg = "内蒙古";
                String xj = "新疆";
                String nx= "宁夏";
                String xz = "西藏";
                String gx = "广西";
                String xg = "香港";
                String am = "澳门";

                //
                String subInfo = ipzone.getSubInfo();
                ip.setIsp(subInfo);

                //
                String mainInfo = ipzone.getMainInfo();

                if (mainInfo.contains(province)) {
                    // 含有 '省'

                    String[] strings = mainInfo.split(province);

                    ip.setCounty(zg);
                    ip.setRegion(strings[0] + province);

                    if (mainInfo.contains(city)) {
                        // 1. 既含有 '省'，也含有 '市'，如：'山东省日照市'

                        ip.setCity(strings[1]);

                    } else {
                        // 2. 含有 '省'，不含有 '市'， 如：'湖北省', '青海省果洛州'

                        if (strings.length > 1) {

                            ip.setCity(strings[1]);
                        }
                    }

                } else if (mainInfo.contains(city)) {
                    // 不含有 '省'，但含有 '市'，如：'北京市'，'上海市'

                    ip.setCountry(zg);
                    ip.setRegion(mainInfo);
                    ip.setCity(mainInfo);

                } else {
                    // 既不含有 '省'，也不含有 '市'

                    // 1. 处理例外情况，如：'内蒙古'、'新疆'、'宁夏'、'西藏'、'广西'，'香港'，'澳门'
                    if (mainInfo.contains(nmg)) {

                        ip.setCountry(zg);
                        ip.setRegion(nmg);
                        ip.setCity(mainInfo.substring(3));

                    } else if (mainInfo.contains(xj)) {

                        ip.setCountry(zg);
                        ip.setRegion(xj);
                        ip.setCity(mainInfo.substring(2));

                    } else if (mainInfo.contains(nx)) {

                        ip.setCountry(zg);
                        ip.setRegion(nx);
                        ip.setCity(mainInfo.substring(2));

                    } else if (mainInfo.contains(xz)) {

                        ip.setCountry(zg);
                        ip.setRegion(xz);
                        ip.setCity(mainInfo.substring(2));

                    } else if (mainInfo.contains(gx)) {

                        ip.setCountry(zg);
                        ip.setRegion(gx);
                        ip.setCity(mainInfo.substring(2));

                    } else if (mainInfo.contains(xg)) {

                        ip.setCountry(zg);
                        ip.setRegion(xg);

                    } else if (mainInfo.contains(am)) {

                        ip.setCountry(zg);
                        ip.setRegion(am);

                    } else {

                        // 国外ip地址
                        ip.setCountry(mainInfo);
                    }
                }
                ipRepository.save(ip);

                // TODO: 如果qqwry库不存在，则调用taobao api
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return ip;
    }


    public IPZone getIPZoneTest1() {

        IPZone ipzone = null;

        try {

            QQWry qqwry = new QQWry();
            ipzone = qqwry.findIP(this.getIp());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipzone;
    }

    /**
     * 测试用途
     *
     * @param ip
     * @return
     */
    public IPZone getIPZoneTest2(String ip) {

        IPZone ipzone = null;

        try {

            QQWry qqwry = new QQWry();
            ipzone = qqwry.findIP(ip);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipzone;
    }





}









