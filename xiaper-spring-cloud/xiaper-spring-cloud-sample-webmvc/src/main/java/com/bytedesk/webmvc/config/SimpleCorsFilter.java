package com.bytedesk.webmvc.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Cors Spring Boot 官方文档:
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
 *
 * https://stackoverflow.com/questions/43114750/header-in-the-response-must-not-be-the-wildcard-when-the-requests-credentia
 * 解决：
 * The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
 * Origin 'http://localhost:4200' is therefore not allowed access.
 *
 * 解决403，No 'Access-Control-Allow-Origin' header 问题
 * response.setHeader("Access-Control-Allow-Credentials",  "true");
 *
 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#headers-frame-options
 *
 * @author bytedesk.com
 */
@Component
public class SimpleCorsFilter implements Filter {


    private List<String> allowedOrigins;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Access-Control-Allow-Origin
        String origin = request.getHeader("Origin");

        // FIXME: 上线之后，需要严格控制访问来源，只有在后台绑定的域名才允许访问
        // response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");

        // FIXME：测试阶段允许任何网站访问
        response.setHeader("Access-Control-Allow-Origin", origin);

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept");
        // FIXME: 不起作用？允许所有网站frame嵌入
        // response.setHeader("X-Frame-Options", "ALLOW-FROM " + origin);

//        chain.doFilter(req, res);
        chain.doFilter(request, response);
    }


    /**
     * TODO: 从数据库或缓存中读取已经绑定的域名
     *
     * 注意：http(s):// 和 端口号必须同时填写，缺一不可
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {

        // allowedOrigins = Arrays.asList("http://vip.kefudashi.cn:8000");
    }

    @Override
    public void destroy() {}

}

