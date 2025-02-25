package corp.pjh.hello_blog_v2.blog.controller;

import corp.pjh.hello_blog_v2.blog.config.BlogConfig;
import corp.pjh.hello_blog_v2.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogConfig blogConfig;

    @GetMapping("/config")
    public ResponseEntity<ApiResponse<BlogConfig>> blogConfig() {
        return ResponseEntity.ok(ApiResponse.successResponse(blogConfig));
    }
}
