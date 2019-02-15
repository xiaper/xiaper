package io.xiaper.jpa.model.ua;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Cpu{

	@JsonProperty("architecture")
	private String architecture;

	public String getArchitecture() {
		return architecture;
	}

	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}

	@Override
 	public String toString(){
		return 
			"Cpu{" +
			"architecture = '" + architecture + '\'' +
			"}";
		}
}