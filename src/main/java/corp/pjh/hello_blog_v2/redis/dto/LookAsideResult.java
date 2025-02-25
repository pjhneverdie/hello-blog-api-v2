package corp.pjh.hello_blog_v2.redis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LookAsideResult {
    private boolean fromDB = false;
    private String data;
}
