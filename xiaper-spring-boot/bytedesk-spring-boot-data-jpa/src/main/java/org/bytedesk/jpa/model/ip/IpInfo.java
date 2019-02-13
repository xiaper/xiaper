package org.bytedesk.jpa.model.ip;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class IpInfo{

	@JsonProperty("code")
	private int code;

	@JsonProperty("data")
	private Data data;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"IpInfo{" + 
			"code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}