package corp.pjh.hello_blog_v2.category.service;

import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.dto.CategoryResponse;
import corp.pjh.hello_blog_v2.category.dto.CreateCategoryRequest;
import corp.pjh.hello_blog_v2.category.dto.UpdateCategoryRequest;
import corp.pjh.hello_blog_v2.category.exception.CategoryExceptionInfo;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;
import corp.pjh.hello_blog_v2.aws.service.S3UploadService;
import corp.pjh.hello_blog_v2.blog.config.BlogConfig;
import corp.pjh.hello_blog_v2.common.dto.CustomException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final BlogConfig blogConfig;
    private final S3UploadService s3UploadService;
    private final CategoryRepository categoryRepository;
    private final CategoryCacheService categoryCacheService;
    private final CategoryValidationService categoryValidationService;

    public CategoryResponse create(CreateCategoryRequest createCategoryRequest) {
        String title = createCategoryRequest.getTitle();

        Category parent = Optional.ofNullable(createCategoryRequest.getParentId())
                .flatMap(categoryRepository::findById)
                .orElse(null);

        if (parent == null) {
            categoryValidationService.checkSameAsCompanyTitle(title); // 부모가 없다 == 최상위 레벨에서의 카테고리명 중복 검사
        } else {
            // 부모 자식 간 카테고리명 중복 검사
            categoryValidationService.checkSameAsParentTitle(parent, title);

            categoryValidationService.checkSameAsCompanyTitle(parent, title); // 같은 레벨에서의 카테고리명 중복 검사
        }

        String thumbUrl = Optional
                .ofNullable(createCategoryRequest.getThumbUrl())
                .orElseGet(() -> uploadThumbImageFile(createCategoryRequest.getThumbImageFile()));

        Category category = new Category(
                title,
                thumbUrl,
                parent
        );

        categoryRepository.save(category);

        categoryCacheService.save(category);

        return new CategoryResponse(category);
    }

    public String getHierarchies() {
        return categoryCacheService.lookAsideJsonHierarchies().getData();
    }

    public CategoryResponse update(UpdateCategoryRequest updateCategoryRequest) {
        Category origin = categoryRepository.findById(updateCategoryRequest.getId()).orElseThrow();
        String originThumbUrl = origin.getThumbUrl();

        String updatedTitle = updateCategoryRequest.getTitle();

        String updatedThumbUrl = Optional
                .ofNullable(updateCategoryRequest.getThumbUrl())
                .orElseGet(() -> uploadThumbImageFile(updateCategoryRequest.getThumbImageFile()));

        Category updatedParent = Optional
                .ofNullable(updateCategoryRequest.getParentId())
                .flatMap(categoryRepository::findById)
                .orElse(null);

        if (updatedParent == null) {
            if (updateCategoryRequest.getParentId() != null) {
                throw new CustomException(CategoryExceptionInfo.NO_SUCH_PARENT);
            }

            if (!origin.getTitle().equals(updatedTitle)) {
                categoryValidationService.checkSameAsCompanyTitle(updatedTitle); // 부모가 없다 == 최상위 레벨에서의 카테고리명 중복 검사
            }
        } else {
            if (!origin.getTitle().equals(updatedTitle)) {
                // 부모 자식 간 카테고리명 중복 검사
                categoryValidationService.checkSameAsParentTitle(updatedParent, updatedTitle);

                categoryValidationService.checkSameAsCompanyTitle(updatedParent, updatedTitle); // 같은 레벨에서의 카테고리명 중복 검사
            }

            categoryValidationService.checkInValidHierarchy(updatedParent.getId(), origin.getId()); // 계층 구조 안전 검사
        }

        origin.updateCategory(
                updatedTitle,
                updatedThumbUrl,
                updatedParent
        );

        deleteThumbImageFile(originThumbUrl); // 기존 썸네일 삭제!

        categoryCacheService.update(origin);

        return new CategoryResponse(origin);
    }

    public void delete(long id) {
        Category category = categoryRepository.findById(id).orElseThrow();

        categoryValidationService.checkChildrenExist(id);

        categoryRepository.delete(category);
        categoryCacheService.delete(category);
        deleteThumbImageFile(category.getThumbUrl());
    }

    private String uploadThumbImageFile(MultipartFile thumbImageFile) {
        try {
            return s3UploadService.putFile(thumbImageFile, s3UploadService.createKey(thumbImageFile));
        } catch (IOException e) {
            throw new CustomException(CategoryExceptionInfo.THUMBNAIL_UPLOAD_FAILED);
        }
    }

    private void deleteThumbImageFile(String originThumbUrl) {
        if (!blogConfig.getDefaultThumbUrls().contains(originThumbUrl)) {
            try {
                s3UploadService.deleteFile(s3UploadService.extractKeyFromUrl(originThumbUrl));
            } catch (IOException e) {
                throw new CustomException(CategoryExceptionInfo.THUMBNAIL_DELETE_FAILED);
            }
        }
    }
}