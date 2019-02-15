package io.xiaper.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件映射实体类
 * 备注：
 *  1.@ConfigurationProperties：将application.properties配置文件中的
 *    符合规则的配置参数映射到实体类中
 *  2.preffix，该属性配置了读取参数的前缀
 *    根据实体属性对应配置文件内的配置为:bytedesk.version, 配置文件中不提供时则使用默认值
 *
 * @author xiaper.io
 */
@ConfigurationProperties(prefix = "bytedesk")
public class ByteDeskProperties {

    /**
     * 相当于 application.properties 里面配置
     *
     * bytedesk.version
     */
    private String version = "2.1.2";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }



}