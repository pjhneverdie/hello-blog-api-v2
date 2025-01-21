package corp.pjh.hello_blog_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class HelloBlogV2Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloBlogV2Application.class, args);
    }

}
