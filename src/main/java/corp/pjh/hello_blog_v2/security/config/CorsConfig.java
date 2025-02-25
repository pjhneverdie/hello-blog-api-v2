package corp.pjh.hello_blog_v2.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("!testcase")
@Getter
@ConfigurationProperties(prefix = "cors")
@RequiredArgsConstructor
public class CorsConfig {
    private final List<String> allowedOrigins;
    private final List<String> allowedMethods;
    private final List<String> allowedHeaders;
    private final boolean allowCredentials;
}

