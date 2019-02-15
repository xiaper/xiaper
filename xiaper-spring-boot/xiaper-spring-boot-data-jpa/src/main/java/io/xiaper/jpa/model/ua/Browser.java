package io.xiaper.jpa.model.ua;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Browser{

	@JsonProperty("major")
	private String major;

	@JsonProperty("name")
	private String name;

	@JsonProperty("version")
	private String version;

	public void setMajor(String major){
		this.major = major;
	}

	public String getMajor(){
		return major;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}

	@Override
 	public String toString(){
		return 
			"Browser{" + 
			"major = '" + major + '\'' + 
			",name = '" + name + '\'' + 
			",version = '" + version + '\'' + 
			"}";
		}
}