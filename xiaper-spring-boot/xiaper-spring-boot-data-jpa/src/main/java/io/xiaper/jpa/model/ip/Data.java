package io.xiaper.jpa.model.ip;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@JsonProperty("area")
	private String area;

	@JsonProperty("country")
	private String country;

	@JsonProperty("isp_id")
	private String ispId;

	@JsonProperty("city")
	private String city;

	@JsonProperty("ip")
	private String ip;

	@JsonProperty("isp")
	private String isp;

	@JsonProperty("county")
	private String county;

	@JsonProperty("region_id")
	private String regionId;

	@JsonProperty("area_id")
	private String areaId;

	@JsonProperty("county_id")
	private String countyId;

	@JsonProperty("region")
	private String region;

	@JsonProperty("country_id")
	private String countryId;

	@JsonProperty("city_id")
	private String cityId;

	public void setArea(String area){
		this.area = area;
	}

	public String getArea(){
		return area;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setIspId(String ispId){
		this.ispId = ispId;
	}

	public String getIspId(){
		return ispId;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return ip;
	}

	public void setIsp(String isp){
		this.isp = isp;
	}

	public String getIsp(){
		return isp;
	}

	public void setCounty(String county){
		this.county = county;
	}

	public String getCounty(){
		return county;
	}

	public void setRegionId(String regionId){
		this.regionId = regionId;
	}

	public String getRegionId(){
		return regionId;
	}

	public void setAreaId(String areaId){
		this.areaId = areaId;
	}

	public String getAreaId(){
		return areaId;
	}

	public void setCountyId(String countyId){
		this.countyId = countyId;
	}

	public String getCountyId(){
		return countyId;
	}

	public void setRegion(String region){
		this.region = region;
	}

	public String getRegion(){
		return region;
	}

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return cityId;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"area = '" + area + '\'' + 
			",country = '" + country + '\'' + 
			",isp_id = '" + ispId + '\'' + 
			",city = '" + city + '\'' + 
			",ip = '" + ip + '\'' + 
			",isp = '" + isp + '\'' + 
			",county = '" + county + '\'' + 
			",region_id = '" + regionId + '\'' + 
			",area_id = '" + areaId + '\'' + 
			",county_id = '" + countyId + '\'' + 
			",region = '" + region + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",city_id = '" + cityId + '\'' + 
			"}";
		}
}