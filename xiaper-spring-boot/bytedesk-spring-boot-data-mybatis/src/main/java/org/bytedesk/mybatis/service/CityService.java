package org.bytedesk.mybatis.service;

import org.bytedesk.mybatis.domain.City;

import java.util.List;

/**
 * @author bytedesk.com on 2019/1/4
 */
public interface CityService {

    City getByProvinceId(Long provinceId);

    List<City> getAll();

}
