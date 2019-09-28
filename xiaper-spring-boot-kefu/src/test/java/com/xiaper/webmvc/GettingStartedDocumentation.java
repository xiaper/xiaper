package com.xiaper.webmvc;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring REST Docs:
 * https://docs.spring.io/spring-restdocs/docs/2.0.3.RELEASE/reference/html5/#getting-started
 *
 * Creating API Documentation with Restdocs：
 * https://spring.io/guides/gs/testing-restdocs/
 *
 * 官方demo：
 * https://github.com/spring-projects/spring-restdocs/tree/v2.0.3.RELEASE/samples/rest-notes-spring-data-rest
 *
 * 生成asciidoc方法：
 * 1. 直接运行 mvn package 即可
 *
 * @author xiaper.io on 2019/1/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GettingStartedDocumentation {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

//    @Autowired
//    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document("{method-name}/{step}/"))
                .build();
    }

    @Test
    public void testHello() throws Exception {
        this.mockMvc.perform(get("/hello"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")))
                .andDo(document("hello"));
    }


}

