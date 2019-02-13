package org.bytedesk.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author bytedesk.com on 2019/1/7
 */
@RestController
public class HelloController {

    /**
     * http://localhost:8019/hello
     *
     * @return
     */
    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Welcome to reactive world ~");
    }

}
