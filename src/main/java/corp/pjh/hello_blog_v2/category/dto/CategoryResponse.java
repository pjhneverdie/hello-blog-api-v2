package corp.pjh.hello_blog_v2.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {
    private final Long id;
    private final String title;
    private final String thumbUrl;
    private final LocalDateTime fixedAt;
    private final LocalDateTime createdAt;
    private final Long parentId;
}