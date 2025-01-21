package corp.pjh.hello_blog_v2.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CreateCategoryRequest {
    private final String title;

    private MultipartFile multipartFile;

    private Long parent_id;
}
