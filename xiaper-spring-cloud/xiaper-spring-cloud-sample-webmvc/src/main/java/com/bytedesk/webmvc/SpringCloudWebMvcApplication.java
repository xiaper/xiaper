package com.bytedesk.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *
 * @author bytedesk.com
 */
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 30)
@ComponentScan("org.bytedesk.*")
@ComponentScan("com.bytedesk.webmvc")
public class SpringCloudWebMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudWebMvcApplication.class, args);
	}
}

