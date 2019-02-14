package org.bytedesk.mybatis.service;

import org.bytedesk.mybatis.domain.City;
import org.bytedesk.mybatis.mapper.CityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bytedesk.com on 2019/1/4
 */
@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityMapper cityMapper;

    @Override
    public City getByProvinceId(Long provinceId) {
        return cityMapper.findByProvinceId(provinceId);
    }

    @Override
    public List<City> getAll() {
        return cityMapper.findAll();
    }
}
