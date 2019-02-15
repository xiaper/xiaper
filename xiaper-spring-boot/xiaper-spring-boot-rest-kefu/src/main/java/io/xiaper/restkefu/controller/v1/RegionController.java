package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.constant.TypeConsts;
import io.xiaper.jpa.model.Region;
import io.xiaper.jpa.repository.RegionRepository;
import io.xiaper.jpa.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 省、市、区/县、镇
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/region")
public class RegionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RegionRepository regionRepository;

    /**
     * 省、市 二级联动
     *
     * @param principal
     * @param client
     * @return
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();
        //
        if (principal != null) {

            //
            List<Region> provinces =  regionRepository.findByType(TypeConsts.REGION_TYPE_PROVINCE);

            // TODO：查询各个省所对应的市级, 自带省级数据
            List<Region> cities = regionRepository.findByType(TypeConsts.REGION_TYPE_CITY);

            //
            Map<String, Object> objectMap = new HashMap<>(2);
            objectMap.put("provinces", provinces);
            objectMap.put("cities", cities);

            // 返回结果
            jsonResult.setMessage("获取省市成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(objectMap);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }



}
