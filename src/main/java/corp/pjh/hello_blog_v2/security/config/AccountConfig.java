package corp.pjh.hello_blog_v2.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.flyway.placeholders")
@RequiredArgsConstructor
public class AccountConfig {
    private final String email;
    private final String password;
}
