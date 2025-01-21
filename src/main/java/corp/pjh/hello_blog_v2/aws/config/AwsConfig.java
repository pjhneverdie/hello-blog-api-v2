package corp.pjh.hello_blog_v2.aws.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "aws")
@RequiredArgsConstructor
public class AwsConfig {
    private final String regionName;
    private final String accessKeyId;
    private final String secretAccessKey;
}
