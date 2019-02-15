package io.xiaper.jpa.model.ua;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class UserAgent{

	@JsonProperty("ua")
	private String ua;

	@JsonProperty("os")
	private Os os;

	@JsonProperty("engine")
	private Engine engine;

	@JsonProperty("browser")
	private Browser browser;

	@JsonProperty("cpu")
	private Cpu cpu;

	@JsonProperty("device")
	private Device device;

	public void setOs(Os os){
		this.os = os;
	}

	public Os getOs(){
		return os;
	}

	public void setEngine(Engine engine){
		this.engine = engine;
	}

	public Engine getEngine(){
		return engine;
	}

	public void setBrowser(Browser browser){
		this.browser = browser;
	}

	public Browser getBrowser(){
		return browser;
	}

	public void setCpu(Cpu cpu){
		this.cpu = cpu;
	}

	public Cpu getCpu(){
		return cpu;
	}

	public void setUa(String ua){
		this.ua = ua;
	}

	public String getUa(){
		return ua;
	}

	public void setDevice(Device device){
		this.device = device;
	}

	public Device getDevice(){
		return device;
	}

	@Override
 	public String toString(){
		return 
			"UserAgent{" + 
			"os = '" + os + '\'' + 
			",engine = '" + engine + '\'' + 
			",browser = '" + browser + '\'' + 
			",cpu = '" + cpu + '\'' + 
			",ua = '" + ua + '\'' +
			",device = '" + device + '\'' + 
			"}";
		}
}