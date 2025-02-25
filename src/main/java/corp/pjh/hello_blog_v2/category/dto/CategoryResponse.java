package corp.pjh.hello_blog_v2.category.dto;

import corp.pjh.hello_blog_v2.category.domain.Category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {
    private final Long id;
    private final String title;
    private final String thumbUrl;
    private final LocalDateTime fixedAt;
    private final LocalDateTime createdAt;
    private final Long parentId;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.thumbUrl = category.getThumbUrl();
        this.fixedAt = category.getFixedAt();
        this.createdAt = category.getCreatedAt();
        this.parentId = Optional.ofNullable(category.getParent()).map(Category::getId).orElse(null);
    }
}