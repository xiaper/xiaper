package org.bytedesk.jpa.model.ua;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Device{


	@JsonProperty("model")
	private String model;

	@JsonProperty("type")
	private String type;

	@JsonProperty("vendor")
	private String vendor;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
 	public String toString(){
		return 
			"Device{" +
			"model = '" + model + '\'' +
			", type = '" + type + '\'' +
			", vendor = '" + vendor + '\'' +
			"}";
		}
}