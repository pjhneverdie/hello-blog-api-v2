package corp.pjh.hello_blog_v2.category.controller;

import corp.pjh.hello_blog_v2.category.dto.*;
import corp.pjh.hello_blog_v2.category.service.CategoryService;
import corp.pjh.hello_blog_v2.common.dto.ApiResponse;
import corp.pjh.hello_blog_v2.common.dto.CustomException;
import corp.pjh.hello_blog_v2.common.dto.ValidationFailedExceptionInfo;
import corp.pjh.hello_blog_v2.common.web.validation.FileTypeConstraint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestPart CreateCategoryForm createCategoryForm,
            @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile
    ) {
        if (createCategoryForm.getThumbUrl() == null && thumbImageFile == null)
            throw new CustomException(new ValidationFailedExceptionInfo("썸네일을 설정해 주세요."));

        CreateCategoryRequest createCategoryRequest =
                new CreateCategoryRequest(
                        createCategoryForm.getTitle(),
                        createCategoryForm.getThumbUrl(),
                        thumbImageFile,
                        createCategoryForm.getParentId()
                );

        return ResponseEntity.ok(ApiResponse.successResponse(categoryService.create(createCategoryRequest)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @Valid @RequestPart UpdateCategoryForm updateCategoryForm,
            @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile
    ) {
        if (updateCategoryForm.getThumbUrl() == null && thumbImageFile == null)
            throw new CustomException(new ValidationFailedExceptionInfo("썸네일을 설정해 주세요."));

        UpdateCategoryRequest updateCategoryRequest =
                new UpdateCategoryRequest(
                        updateCategoryForm.getId(),
                        updateCategoryForm.getTitle(),
                        updateCategoryForm.getThumbUrl(),
                        thumbImageFile,
                        updateCategoryForm.getParentId()
                );

        return ResponseEntity.ok(ApiResponse.successResponse(categoryService.update(updateCategoryRequest)));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@NotNull @RequestParam Long id) {
        categoryService.delete(id);

        return ResponseEntity.ok(ApiResponse.successVoidResponse());
    }
}