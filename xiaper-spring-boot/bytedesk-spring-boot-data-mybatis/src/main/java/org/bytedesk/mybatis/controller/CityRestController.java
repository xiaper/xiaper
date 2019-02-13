package org.bytedesk.mybatis.controller;

import org.bytedesk.mybatis.domain.City;
import org.bytedesk.mybatis.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bytedesk.com on 2019/1/4
 */
@RestController
@RequestMapping("/api")
public class CityRestController {

    @Resource
    private CityService cityService;

    @GetMapping("/cities")
    public List<City> cities () {
        return cityService.getAll();
    }

    @GetMapping("/city/{provinceId}")
    public City city(@PathVariable long provinceId) {
        return cityService.getByProvinceId(provinceId);
    }

}
