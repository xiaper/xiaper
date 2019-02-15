package io.xiaper.jpa.model;

import javax.persistence.*;

/**
 * ip地址以及对应城市信息*
 * 北京ip：
 * http://ip.taobao.com/service/getIpInfo.php?ip=118.26.73.2
 * 济南ip：
 * http://ip.taobao.com/service/getIpInfo.php?ip=124.133.129.228
 * 香港ip：
 * http://ip.taobao.com/service/getIpInfo.php?ip=47.75.179.15
 * 武汉ip：
 * http://ip.taobao.com/service/getIpInfo.php?ip=171.113.157.8
 *
 * {
 * code: 0,
 * data: {
 * ip: "118.26.73.2",
 * country: "中国",
 * area: "",
 * region: "北京",
 * city: "北京",
 * county: "XX",
 * isp: "联通",
 * country_id: "CN",
 * area_id: "",
 * region_id: "110000",
 * city_id: "110100",
 * county_id: "xx",
 * isp_id: "100026"
 * }
 * }
 *
 *
 *{
 * code: 0,
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
 * }
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "ip")
public class Ip extends AuditModel {

    private static final long serialVersionUID = -2399769445745389838L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * ip：127.0.0.1
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 国家：中国
     */
    @Column(name = "country")
    private String country = "中国";

    /**
     * 地区：华北
     */
    @Column(name = "area")
    private String area = "华北";

    /**
     * 省份：北京市、山东省
     */
    @Column(name = "region")
    private String region = "北京市";

    /**
     * 城市：北京市（市辖区）、日照市
     */
    @Column(name = "city")
    private String city = "市辖区";

    /**
     * 区：东城区、东港区
     */
    @Column(name = "county")
    private String county = "东城区";

    /**
     * 运营商：电信
     */
    @Column(name = "isp")
    private String isp = "电信";

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "area_id")
    private String areaId;

    @Column(name = "region_id")
    private String regionId;

    @Column(name = "city_id")
    private String cityId;

    @Column(name = "county_id")
    private String countyId;

    @Column(name = "isp_id")
    private String ispId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getIspId() {
        return ispId;
    }

    public void setIspId(String ispId) {
        this.ispId = ispId;
    }


}
