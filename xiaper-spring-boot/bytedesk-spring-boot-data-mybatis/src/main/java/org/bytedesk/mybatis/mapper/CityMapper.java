package org.bytedesk.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.bytedesk.mybatis.domain.City;

import java.util.List;

/**
 * @author bytedesk.com on 2019/1/4
 */
@Mapper
public interface CityMapper {

    /**
     * 返回结果实体属性与数据库字段转换
     *
     * @param provinceId
     * @return
     */
    @Select("select * from city where province_id = #{provinceId}")
    @Results({
            @Result(property = "provinceId", column = "province_id"),
            @Result(property = "cityName", column = "city_name")
    })
    City findByProvinceId(@Param("provinceId") Long provinceId);


//    City findByProvinceId(Long provinceId);


    List<City> findAll();

}
