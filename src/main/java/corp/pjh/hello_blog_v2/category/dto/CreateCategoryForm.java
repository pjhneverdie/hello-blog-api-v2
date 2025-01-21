package corp.pjh.hello_blog_v2.category.dto;

import corp.pjh.hello_blog_v2.common.web.custom_constraint.ThumbUrlConstraint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.constraints.Length;

@RequiredArgsConstructor
@Getter
public class CreateCategoryForm {
    @NotNull(message = "제목을 입력해 주세요.")
    @NotBlank(message = "제목은 공백 제외 제목은 1자 이상 255자 이하로 입력해 주세요.")
    @Length(min = 1, max = 255, message = "제목은 1자 이상 255자 이하로 입력해 주세요.")
    private final String title;

    @ThumbUrlConstraint
    private final String thumbUrl;

    private final Long parentId;
}