package io.xiaper.rest.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author bytedesk.com on 2019/2/13
 */
@RestController
public class HelloController {


    /**
     * 测试
     *
     * @return
     */
    @GetMapping("/hello")
    public Map<String, Object> greeting() {
        return Collections.singletonMap("message", "Hello World");
    }

}
