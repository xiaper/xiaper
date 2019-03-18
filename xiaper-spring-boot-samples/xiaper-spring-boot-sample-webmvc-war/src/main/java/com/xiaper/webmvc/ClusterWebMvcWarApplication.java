package com.xiaper.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.WebApplicationInitializer;

/**
 *
 * @author xiaper.io
 */
@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@ComponentScan("io.xiaper.*")
@ComponentScan("com.xiaper.webmvc")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 30)
/**
 * 切换到war启动: Tomcat
 *
 * mvn clean package -Dmaven.test.skip=true
 *
 * @author xiaper.io
 */
//public class ClusterWebMvcWarApplication extends SpringBootServletInitializer {
//
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(ClusterWebMvcApplication.class);
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(ClusterWebMvcWarApplication.class, args);
//	}
//}

/**
 * 打包war部署到Weblogic
 * mvn clean package -Dmaven.test.skip=true
 * @author xiaper.io
 */
public class ClusterWebMvcWarApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClusterWebMvcWarApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ClusterWebMvcWarApplication.class, args);
	}
}

