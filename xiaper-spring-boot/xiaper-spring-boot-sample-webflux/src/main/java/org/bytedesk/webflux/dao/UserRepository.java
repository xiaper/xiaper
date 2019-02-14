package org.bytedesk.webflux.dao;

import org.bytedesk.webflux.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author bytedesk.com on 2019/1/7
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String username);

    Mono<Long> deleteByUsername(String username);

}

