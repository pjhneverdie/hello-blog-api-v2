package corp.pjh.hello_blog_v2.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import corp.pjh.hello_blog_v2.common.dto.ApiResponse;
import corp.pjh.hello_blog_v2.member.execption.MemberExceptionInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(MemberExceptionInfo.LOGIN_FAILED.httpStatusCode().value());
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.failedResponse(MemberExceptionInfo.LOGIN_FAILED)));
    }
}
