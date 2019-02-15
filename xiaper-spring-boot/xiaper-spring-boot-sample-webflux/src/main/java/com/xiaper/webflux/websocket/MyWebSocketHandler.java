package com.xiaper.webflux.websocket;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author bytedesk.com on 2019/1/17
 */
public class MyWebSocketHandler implements WebSocketHandler {


    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return null;

//        Flux<WebSocketMessage> output = webSocketSession.receive()
//                .doOnNext(message -> {
//                    // ...
//                })
//                .concatMap(message -> {
//                    // ...
//                })
//                .map(value -> webSocketSession.textMessage("Echo " + value));
//
//        return webSocketSession.send(output);
    }

}
