package corp.pjh.hello_blog_v2.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CreateCategoryRequest {
    private final String title;

    private final String thumbUrl;

    private final MultipartFile thumbImageFile;

    private final Long parentId;
}
