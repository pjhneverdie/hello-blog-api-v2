package corp.pjh.hello_blog_v2.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {
    private final static ZoneId utcZoneId = ZoneId.of("UTC");
    private final static ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");

    public static LocalDateTime getUTCLocalDatetime() {
        return LocalDateTime.now(seoulZoneId)
                .atZone(seoulZoneId)
                .toInstant()
                .atZone(utcZoneId)
                .toLocalDateTime();
    }
}
