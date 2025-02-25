package corp.pjh.hello_blog_v2.security.config;

import corp.pjh.hello_blog_v2.security.handler.LoginFailureHandler;
import corp.pjh.hello_blog_v2.security.handler.LoginSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Profile("!testcase")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPointHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(@Value("${spring.security.debug:false}") boolean webSecurityDebug) {
        return (web) -> web.debug(webSecurityDebug);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors((cors) -> {
            cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();

                    corsConfiguration.setAllowedOrigins(corsConfig.getAllowedOrigins());
                    corsConfiguration.setAllowedMethods(corsConfig.getAllowedMethods());
                    corsConfiguration.setAllowedHeaders(corsConfig.getAllowedHeaders());
                    corsConfiguration.setAllowCredentials(corsConfig.isAllowCredentials());

                    return corsConfiguration;
                }
            });
        });

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
