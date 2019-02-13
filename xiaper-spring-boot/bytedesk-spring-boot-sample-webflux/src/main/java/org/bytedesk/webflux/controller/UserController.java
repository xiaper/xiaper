package org.bytedesk.webflux.controller;

import org.bytedesk.webflux.domain.User;
import org.bytedesk.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author bytedesk.com on 2019/1/7
 *
 * Spring WebFlux快速上手——响应式Spring的道法术器：
 * https://blog.csdn.net/get_set/article/details/79480233
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Mono<User> save(User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return this.userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping("")
    public Flux<User> findAll() {
        return this.userService.findAll();
    }

}
