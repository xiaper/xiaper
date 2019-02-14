package org.bytedesk.mybatis.domain;

import java.io.Serializable;

/**
 * @author bytedesk.com on 2019/1/4
 */
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long provinceId;

    private String cityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
