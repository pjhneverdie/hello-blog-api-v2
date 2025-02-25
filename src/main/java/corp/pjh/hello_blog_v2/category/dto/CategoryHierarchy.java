package corp.pjh.hello_blog_v2.category.dto;

import corp.pjh.hello_blog_v2.category.domain.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class CategoryHierarchy {
    private Long id;
    private String title;
    private String thumbUrl;
    private LocalDateTime fixedAt;
    private LocalDateTime createdAt;
    private Long parentId;
    private List<CategoryHierarchy> children = new ArrayList<>();

    public CategoryHierarchy(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.thumbUrl = category.getThumbUrl();
        this.fixedAt = category.getFixedAt();
        this.createdAt = category.getCreatedAt();
        this.parentId = Optional.ofNullable(category.getParent()).map(Category::getId).orElse(null);

        for (Category child : category.getChildren()) {
            this.children.add(new CategoryHierarchy(child));
        }
    }
}
