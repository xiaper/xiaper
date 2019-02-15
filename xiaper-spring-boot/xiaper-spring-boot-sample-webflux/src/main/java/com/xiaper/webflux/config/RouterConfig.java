package com.xiaper.webflux.config;

import com.xiaper.webflux.handler.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author bytedesk.com on 2019/1/7
 */
@Configuration
public class RouterConfig {

    @Autowired
    private TimeHandler timeHandler;

    /**
     * http://localhost:8019/time
     * http://localhost:8019/date
     * http://localhost:8019/times
     *
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        return route(GET("/time"), timeHandler::getTime)
                .andRoute(GET("/date"), timeHandler::getDate)
                .andRoute(GET("/times"), timeHandler::sendTimePerSec);
    }

}
