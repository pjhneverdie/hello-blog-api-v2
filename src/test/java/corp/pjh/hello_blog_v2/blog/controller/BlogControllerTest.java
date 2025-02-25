package corp.pjh.hello_blog_v2.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import corp.pjh.hello_blog_v2.blog.config.BlogConfig;
import corp.pjh.hello_blog_v2.common.dto.ApiResponse;
import corp.pjh.hello_blog_v2.config.SecurityTestConfig;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Import(SecurityTestConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BlogConfig blogConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 블로그_기본_컨피그_읽기_테스트() throws Exception {
        mockMvc.perform(get("/config")).andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.successResponse(blogConfig))));
    }
}