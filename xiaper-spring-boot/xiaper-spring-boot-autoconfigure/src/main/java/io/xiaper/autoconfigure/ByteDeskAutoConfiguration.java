package io.xiaper.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Creating Your Own Auto-configuration:
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html
 *
 * 自定义自动配置类
 * 注解详解：
 *  1. @Configuration：说明该类是配置类，等价于xml中的beans
 *  2. @EnableConfigurationProperties 开启属性注入
 *
 *
 * @author xiaper.io
 */
@Configuration
@EnableConfigurationProperties(ByteDeskProperties.class)
@ConditionalOnProperty(prefix = "bytedesk", value = "enabled", matchIfMissing = true)
public class ByteDeskAutoConfiguration {

	@Autowired
	private ByteDeskProperties byteDeskProperties;

	/**
	 * 根据条件判断不存在HelloService时初始化新bean到SpringIoc
	 * 参考 https://www.jianshu.com/p/188065e1137b
	 * @return
	 */
//	@Bean
//	@ConditionalOnBean(HelloService.class)
//	public HelloService helloService() {
//		HelloService helloService = new HelloService();
//		helloService.setContent(byteDeskProperties.getVersion());
//		return helloService;
//	}
//
//
//	@Bean
//	@ConditionalOnMissingBean(ByteDeskConfig.class)
//	public ByteDeskConfig byteDeskConfig() {
//		ByteDeskConfig byteDeskConfig = new ByteDeskConfig();
//		byteDeskConfig.setVersion(byteDeskProperties.getVersion());
//		return byteDeskConfig;
//	}







}
