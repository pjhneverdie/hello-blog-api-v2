package corp.pjh.hello_blog_v2.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Getter
public class UpdateCategoryRequest {
    private final Long id;

    private final String title;

    private final String thumbUrl;

    private final MultipartFile thumbImageFile;

    private final Long parentId;
}
