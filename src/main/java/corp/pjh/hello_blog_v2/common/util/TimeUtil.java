package corp.pjh.hello_blog_v2.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {
    public static LocalDateTime getLocalDateTimeFormatUTC() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                .atZone(ZoneId.of("Asia/Seoul"))
                .toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
    }
}
