package com.xiaper.webmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * OAuth2资源服务器
 *
 * HttpMethod.OPTIONS,
 *
 * @author xiaper.io
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 允许所有网站frame嵌入
		http.headers().frameOptions().disable();
		http.cors().and().authorizeRequests().antMatchers("**", "/**").permitAll();
	}
}
