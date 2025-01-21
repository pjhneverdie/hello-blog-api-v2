package corp.pjh.hello_blog_v2.category.controller;

import corp.pjh.hello_blog_v2.category.dto.CreateCategoryForm;
import corp.pjh.hello_blog_v2.category.dto.CreateCategoryRequest;
import corp.pjh.hello_blog_v2.category.service.CategoryService;
import corp.pjh.hello_blog_v2.common.dto.ApiResponse;
import corp.pjh.hello_blog_v2.common.web.custom_constraint.FileTypeConstraint;

import jakarta.validation.Valid;

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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createCategory(
            @Valid @RequestPart CreateCategoryForm createCategoryForm,
            @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile
    )  {
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest(createCategoryForm.getTitle(), createCategoryForm.getThumbUrl(), thumbImageFile, createCategoryForm.getParentId());

        categoryService.createCategory(createCategoryRequest);

        return ResponseEntity.ok(ApiResponse.successVoidResponse());
    }
}