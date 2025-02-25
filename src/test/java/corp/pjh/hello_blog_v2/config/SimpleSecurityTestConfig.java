package corp.pjh.hello_blog_v2.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import corp.pjh.hello_blog_v2.security.handler.AuthenticationEntryPointHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 컨트롤러 유닛 테스트 시 임포트(AuthenticationEntryPoint만 필요한 경우 EX) WebMvcTest)
 */
@EnableWebSecurity(debug = true)
public class SimpleSecurityTestConfig {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AuthenticationEntryPoint authenticationEntryPointHandler =
            new AuthenticationEntryPointHandler(objectMapper);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth.requestMatchers("/admin/**").authenticated().anyRequest().permitAll());

        http.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(authenticationEntryPointHandler));

        return http.build();
    }
}