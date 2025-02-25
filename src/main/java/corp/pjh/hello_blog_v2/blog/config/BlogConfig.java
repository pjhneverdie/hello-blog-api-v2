package corp.pjh.hello_blog_v2.blog.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "blog.config")
@RequiredArgsConstructor
public class BlogConfig {
    @NestedConfigurationProperty
    private final Profile profile;

    @Getter
    @RequiredArgsConstructor
    public static class Profile {
        private final String thumbUrl;
        private final String lastName;
        private final String givenName;
        private final String dateOfBirth;
        private final String githubProfile;
        private final String linkedinProfile;
    }

    private final List<String> defaultThumbUrls;
}
