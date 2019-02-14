package org.bytedesk.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * FIXME: 添加ComponentScan为防止引入HelloService等bean,IDEA中提示could not autowire no beans of, 有待优化
 *
 * @author xiaper.io
 */
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}

