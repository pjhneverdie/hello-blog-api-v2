package corp.pjh.hello_blog_v2.category.dto;

import corp.pjh.hello_blog_v2.category.domain.Category;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryHierarchy {
    private final long id;
    private final String title;
    private final String thumbUrl;
    private final LocalDateTime fixedAt;
    private final LocalDateTime createdAt;
    private final Long parentId;
    private final List<CategoryHierarchy> children;

    public CategoryHierarchy(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.thumbUrl = category.getThumbUrl();
        this.fixedAt = category.getFixedAt();
        this.createdAt = category.getCreatedAt();
        this.parentId = category.getParent() == null ? null : category.getParent().getId();
        this.children = new ArrayList<>();
    }
}
