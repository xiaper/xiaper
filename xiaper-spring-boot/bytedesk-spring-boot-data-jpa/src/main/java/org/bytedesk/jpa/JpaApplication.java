package org.bytedesk.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * FIXME: 添加ComponentScan为防止引入HelloService等bean,IDEA中提示could not autowire no beans of, 有待优化
 *
 * @author bytedesk.org
 */
@EnableJpaRepositories
@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}

