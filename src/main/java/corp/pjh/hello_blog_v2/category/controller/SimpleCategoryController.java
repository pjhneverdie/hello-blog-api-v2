package corp.pjh.hello_blog_v2.category.controller;

import corp.pjh.hello_blog_v2.category.service.CategoryService;
import corp.pjh.hello_blog_v2.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class SimpleCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getHierarchies() {
        String hierarchies = categoryService.getHierarchies();

        return ResponseEntity.ok(ApiResponse.successResponse(hierarchies));
    }
}
