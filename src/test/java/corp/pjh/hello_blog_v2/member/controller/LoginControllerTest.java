package corp.pjh.hello_blog_v2.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import corp.pjh.hello_blog_v2.common.dto.ApiResponse;
import corp.pjh.hello_blog_v2.config.SecurityTestConfig;
import corp.pjh.hello_blog_v2.member.execption.MemberExceptionInfo;
import corp.pjh.hello_blog_v2.security.config.AccountConfig;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Import(SecurityTestConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountConfig accountConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void successHandler_테스트() throws Exception {
        String email = accountConfig.getEmail();
        String password = accountConfig.getPassword();

        mockMvc.perform(formLogin("/member/login")
                        .user("email", email)
                        .password(password))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.successVoidResponse())));
    }

    @Test
    void failureHandler_테스트() throws Exception {
        String email = "test@test.com";
        String password = "testpassword";

        mockMvc.perform(formLogin("/member/login")
                        .user("email", email)
                        .password(password))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.failedResponse(MemberExceptionInfo.LOGIN_FAILED))));
    }

    @Test
    void authenticationEntryPointHandler_테스트() throws Exception {
        mockMvc.perform(post("/admin/category"))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.failedResponse(MemberExceptionInfo.UNAUTHORIZED))));
    }
}