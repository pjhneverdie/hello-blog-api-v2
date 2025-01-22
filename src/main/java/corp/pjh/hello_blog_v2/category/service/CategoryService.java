package corp.pjh.hello_blog_v2.category.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.dto.CategoryHierarchy;
import corp.pjh.hello_blog_v2.category.dto.CategoryResponse;
import corp.pjh.hello_blog_v2.category.dto.CreateCategoryRequest;
import corp.pjh.hello_blog_v2.category.exception.CategoryExceptionInfo;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;
import corp.pjh.hello_blog_v2.aws.service.S3UploadService;
import corp.pjh.hello_blog_v2.common.dto.CustomException;
import corp.pjh.hello_blog_v2.common.util.TimeUtil;

import corp.pjh.hello_blog_v2.redis.service.CacheService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ObjectMapper objectMapper;
    private final CacheService cacheService;
    private final S3UploadService s3UploadService;
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        String title = createCategoryRequest.getTitle();

        Category parent = Optional
                .ofNullable(createCategoryRequest.getParentId())
                .flatMap(categoryRepository::findById)
                .orElse(null);

        String thumbUrl = Optional
                .ofNullable(createCategoryRequest.getThumbUrl())
                .orElseGet(() -> {
                    MultipartFile thumbImageFile = createCategoryRequest.getThumbImageFile();
                    String key = S3UploadService.DEFAULT_UPLOAD_PATH + thumbImageFile.getOriginalFilename() + "-" + TimeUtil.getLocalDateTimeFormatUTC();

                    try {
                        return s3UploadService.putFile(thumbImageFile, key);
                    } catch (IOException e) {
                        throw new CustomException(CategoryExceptionInfo.THUMBNAIL_UPLOAD_FAILED);
                    }
                });

        Category category = new Category(
                title,
                thumbUrl,
                parent
        ); // 카테고리 생성

        categoryRepository.save(category); // 카테고리 저장

        cacheCategory(category); // 카테고리 캐싱

        return new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getThumbUrl(),
                category.getFixedAt(),
                category.getCreatedAt(),
                createCategoryRequest.getParentId()
        );
    }

    private void cacheCategory(Category category) {
        String jsonHierarchies = cacheService.get("hierarchies");

        try {
            List<CategoryHierarchy> objectHierarchies = Optional.ofNullable(objectMapper.readValue(jsonHierarchies, new TypeReference<List<CategoryHierarchy>>() {
            })).orElse(new ArrayList<>());

            /**
             * 부모 카테고리가 없다 == 최상위 카테고리, 첫 번째 깊이에 추가하면 끝!
             */
            if (category.getParent() == null) {
                objectHierarchies.add(new CategoryHierarchy(category));
            } else {
                /**
                 * 부모 카테고리가 있다 == 하위 카테고리, DFS로 부모 찾아서 그 밑에 추가하면 끝!
                 */
                CategoryHierarchy parentLocation = findParentLocation(category.getParent().getId(), objectHierarchies);

                parentLocation.getChildren().add(new CategoryHierarchy(category));
            }

            cacheService.set("hierarchies", objectMapper.writeValueAsString(objectHierarchies));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CategoryHierarchy findParentLocation(long parentId, List<CategoryHierarchy> objectHierarchies) {
        for (CategoryHierarchy objectHierarchy : objectHierarchies) {
            if (parentId == objectHierarchy.getId()) {
                return objectHierarchy;
            } else {
                findParentLocation(parentId, objectHierarchy.getChildren());
            }
        }
    }
}