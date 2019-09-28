package com.xiaper.webmvc.config;

import io.xiaper.mq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * CORS-跨域:
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
 *
 * https://spring.io/guides/topicals/spring-security-architecture/
 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/
 * http://www.baeldung.com/spring-security-expressions-basic
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/el-access.html
 *
 * @author xiaper.io
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 认证：Authentication
     * 身份验证管理生成器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * 授权：Authorization
     * HTTP请求安全处理
     *
     * csrf:
     * https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html
     *
     * 初步理解Spring Security并实践:
     * https://www.jianshu.com/p/e6655328b211
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 通过authorizeRequests()方法来开始请求权限配置。
        // 允许所有网站frame嵌入
        http.headers().frameOptions().disable();
        http.cors()
                .and().authorizeRequests().antMatchers("**","/**").permitAll();
    }

    /**
     * 授权：Authorization
     * WEB安全
     *
     * HttpMethod.OPTIONS 非常重要，解决下面跨域Cors问题：
     *  401 (Unauthorized)
     *  Response for preflight does not have HTTP ok status.
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        // 设置Spring Security不拦截/resources/static/目录下的静态资源
        web.ignoring()
                .antMatchers( HttpMethod.OPTIONS, "**", "/**", "/stomp/**" )
                .antMatchers("/error/**", "/403", "/favicon.ico", "/css/**", "/js/**", "/fonts/**", "/img/**");
    }


    /**
     * 需要配置这个支持password模式
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
