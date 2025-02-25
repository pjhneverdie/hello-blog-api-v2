package corp.pjh.hello_blog_v2.config;

import corp.pjh.hello_blog_v2.security.handler.LoginFailureHandler;
import corp.pjh.hello_blog_v2.security.handler.LoginSuccessHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 시큐리티를 사용하는 통합 테스트 시 임포트(AuthenticationEntryPoint만 필요한 경우 EX) AutoConfigureMockMvc)
 */
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityTestConfig {
    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPointHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.formLogin((auth) -> auth
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/member/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
        );

        http.authorizeHttpRequests((auth) -> auth.requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll());

        http.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(authenticationEntryPointHandler));

        return http.build();
    }
}