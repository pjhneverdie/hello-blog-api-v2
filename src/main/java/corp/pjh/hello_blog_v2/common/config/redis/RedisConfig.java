package corp.pjh.hello_blog_v2.common.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    private final String host;
    private final int port;
    private final String password;

    public RedisConfig(@Value("${redis.host}") String host,
                       @Value("${redis.port}") int port,
                       @Value("${redis.password}") String password
    ) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    @Profile("!testcase")
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(password);

        return new LettuceConnectionFactory(config);
    }

    @Profile("testcase")
    @Bean
    public RedisConnectionFactory redisConnectionFactoryTestCase() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(password);

        return new LettuceConnectionFactory(config);
    }

    @Profile("!testcase")
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Profile("testcase")
    @Bean
    public RedisTemplate<String, Object> redisTemplateTestCase(RedisConnectionFactory redisConnectionFactoryTestCase) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactoryTestCase);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
