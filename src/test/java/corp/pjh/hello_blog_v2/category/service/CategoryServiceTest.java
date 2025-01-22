package corp.pjh.hello_blog_v2.category.service;

import corp.pjh.hello_blog_v2.aws.service.S3UploadService;
import corp.pjh.hello_blog_v2.aws.service.S3UploadServiceTest;
import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.dto.CategoryResponse;
import corp.pjh.hello_blog_v2.category.dto.CreateCategoryRequest;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private S3UploadService s3UploadService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void 부모_없는_카테고리_생성_썸네일_업로드_테스트() throws Exception {
        MultipartFile thumbImageFile = S3UploadServiceTest.createMultipartFile(
                "test-file.txt",
                "This is a test file for S3 upload.",
                "text/plain"
        );
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("title", null, thumbImageFile, null);

        /**
         *         Category parent = Optional
         *                 .ofNullable(createCategoryRequest.getParentId())
         *                 .flatMap(categoryRepository::findById)
         *                 .orElse(null);
         */
        Category parent = null;

        /**
         *         String thumbUrl = Optional
         *                 .ofNullable(createCategoryRequest.getThumbUrl())
         *                 .orElseGet(() -> {
         *                     MultipartFile thumbImageFile = createCategoryRequest.getThumbImageFile();
         *                     String key = S3UploadService.DEFAULT_UPLOAD_PATH + thumbImageFile.getOriginalFilename() + "-" + TimeUtil.getLocalDateTimeFormatUTC();
         *
         *                     try {
         *                         return s3UploadService.putFile(thumbImageFile, key);
         *                     } catch (IOException e) {
         *                         throw new CustomException(CategoryExceptionInfo.THUMBNAIL_UPLOAD_FAILED);
         *                     }
         *                 });
         *
         */
        String key = S3UploadService.DEFAULT_UPLOAD_PATH + thumbImageFile.getOriginalFilename() + "-";
        when(s3UploadService.putFile(any(MultipartFile.class), startsWith(key))).thenReturn("thumbUrl");
        String thumbUrl = "thumbUrl";

        /**
         *         Category category = new Category(createCategoryRequest.getTitle(), thumbUrl, parent);
         *
         *         categoryRepository.save(category);
         *
         */
        Category category = new Category(null, createCategoryRequest.getTitle(), thumbUrl, parent);
        doNothing().when(categoryRepository).save(any(Category.class));

        CategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);

        /**
         *         return new CategoryResponse(
         *                 category.getId(),
         *                 category.getTitle(),
         *                 category.getThumbUrl(),
         *                 category.getFixedAt(),
         *                 category.getCreatedAt(),
         *                 createCategoryRequest.getParentId()
         *         );
         */
        CategoryResponse expectedResponse = new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getThumbUrl(),
                category.getFixedAt(),
                category.getCreatedAt(),
                createCategoryRequest.getParentId()
        );

        assertEquals(expectedResponse.getId(), categoryResponse.getId());
        assertEquals(expectedResponse.getTitle(), categoryResponse.getTitle());
        assertEquals(expectedResponse.getThumbUrl(), categoryResponse.getThumbUrl());
        assertEquals(createCategoryRequest.getParentId(), categoryResponse.getParentId());
    }

    @Test
    public void 부모_있는_카테고리_생성_썸네일_업로드_테스트() throws Exception {
        MultipartFile thumbImageFile = S3UploadServiceTest.createMultipartFile(
                "test-file.txt",
                "This is a test file for S3 upload.",
                "text/plain"
        );
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("title", null, thumbImageFile, 1L);

        /**
         *         Category parent = Optional
         *                 .ofNullable(createCategoryRequest.getParentId())
         *                 .flatMap(categoryRepository::findById)
         *                 .orElse(null);
         */
        Category parent = new Category(createCategoryRequest.getParentId(), "title", "thumbUrl", null);
        when(categoryRepository.findById(createCategoryRequest.getParentId())).thenReturn(Optional.of(parent));

        /**
         *         String thumbUrl = Optional
         *                 .ofNullable(createCategoryRequest.getThumbUrl())
         *                 .orElseGet(() -> {
         *                     MultipartFile thumbImageFile = createCategoryRequest.getThumbImageFile();
         *                     String key = S3UploadService.DEFAULT_UPLOAD_PATH + thumbImageFile.getOriginalFilename() + "-" + TimeUtil.getLocalDateTimeFormatUTC();
         *
         *                     try {
         *                         return s3UploadService.putFile(thumbImageFile, key);
         *                     } catch (IOException e) {
         *                         throw new CustomException(CategoryExceptionInfo.THUMBNAIL_UPLOAD_FAILED);
         *                     }
         *                 });
         *
         */
        String key = S3UploadService.DEFAULT_UPLOAD_PATH + thumbImageFile.getOriginalFilename() + "-";
        when(s3UploadService.putFile(any(MultipartFile.class), startsWith(key))).thenReturn("thumbUrl");
        String thumbUrl = "thumbUrl";

        /**
         *         Category category = new Category(createCategoryRequest.getTitle(), thumbUrl, parent);
         *
         *         categoryRepository.save(category);
         *
         */
        Category category = new Category(null, createCategoryRequest.getTitle(), thumbUrl, parent);
        doNothing().when(categoryRepository).save(any(Category.class));

        CategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);

        /**
         *         return new CategoryResponse(
         *                 category.getId(),
         *                 category.getTitle(),
         *                 category.getThumbUrl(),
         *                 category.getFixedAt(),
         *                 category.getCreatedAt(),
         *                 createCategoryRequest.getParentId()
         *         );
         */
        CategoryResponse expectedResponse = new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getThumbUrl(),
                category.getFixedAt(),
                category.getCreatedAt(),
                createCategoryRequest.getParentId()
        );

        assertEquals(expectedResponse.getId(), categoryResponse.getId());
        assertEquals(expectedResponse.getTitle(), categoryResponse.getTitle());
        assertEquals(expectedResponse.getThumbUrl(), categoryResponse.getThumbUrl());
        assertEquals(createCategoryRequest.getParentId(), categoryResponse.getParentId());
    }

    @Test
    public void 부모_없는_카테고리_생성_썸네일url이_이미_있는_경우_테스트() {
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("title", "thumbUrl", null, null);

        /**
         *         Category parent = Optional
         *                 .ofNullable(createCategoryRequest.getParentId())
         *                 .flatMap(categoryRepository::findById)
         *                 .orElse(null);
         */
        Category parent = null;

        /**
         *         String thumbUrl = Optional
         *                 .ofNullable(createCategoryRequest.getThumbUrl())
         *                 .orElseGet(() -> {
         *                     MultipartFile thumbImageFile = createCategoryRequest.getThumbImageFile();
         *                     String key = S3UploadService.DEFAULT_UPLOAD_PATH + thumbImageFile.getOriginalFilename() + "-" + TimeUtil.getLocalDateTimeFormatUTC();
         *
         *                     try {
         *                         return s3UploadService.putFile(thumbImageFile, key);
         *                     } catch (IOException e) {
         *                         throw new CustomException(CategoryExceptionInfo.THUMBNAIL_UPLOAD_FAILED);
         *                     }
         *                 });
         *
         */
        String thumbUrl = createCategoryRequest.getThumbUrl();


        /**
         *         Category category = new Category(createCategoryRequest.getTitle(), thumbUrl, parent);
         *
         *         categoryRepository.save(category);
         *
         */
        Category category = new Category(null, createCategoryRequest.getTitle(), thumbUrl, parent);
        doNothing().when(categoryRepository).save(any(Category.class));

        CategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);

        /**
         *         return new CategoryResponse(
         *                 category.getId(),
         *                 category.getTitle(),
         *                 category.getThumbUrl(),
         *                 category.getFixedAt(),
         *                 category.getCreatedAt(),
         *                 createCategoryRequest.getParentId()
         *         );
         */
        CategoryResponse expectedResponse = new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getThumbUrl(),
                category.getFixedAt(),
                category.getCreatedAt(),
                createCategoryRequest.getParentId()
        );

        assertEquals(expectedResponse.getId(), categoryResponse.getId());
        assertEquals(expectedResponse.getTitle(), categoryResponse.getTitle());
        assertEquals(expectedResponse.getThumbUrl(), categoryResponse.getThumbUrl());
        assertEquals(createCategoryRequest.getParentId(), categoryResponse.getParentId());
    }

    @Test
    public void 부모_있는_카테고리_생성_썸네일url이_이미_있는_경우_테스트() {
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("title", "thumbUrl", null, 1L);

        /**Î
         *         Category parent = Optional
         *                 .ofNullable(createCategoryRequest.getParentId())
         *                 .flatMap(categoryRepository::findById)
         *                 .orElse(null);
         */
        Category parent = new Category(createCategoryRequest.getParentId(), "title", "thumbUrl", null);
        when(categoryRepository.findById(createCategoryRequest.getParentId())).thenReturn(Optional.of(parent));

        /**
         *         String thumbUrl = Optional
         *                 .ofNullable(createCategoryRequest.getThumbUrl())
         *                 .orElseGet(() -> {
         *                     MultipartFile thumbImageFile = createCategoryRequest.getThumbImageFile();
         *                     String key = S3UploadService.DEFAULT_UPLOAD_PATH + thumbImageFile.getOriginalFilename() + "-" + TimeUtil.getLocalDateTimeFormatUTC();
         *
         *                     try {
         *                         return s3UploadService.putFile(thumbImageFile, key);
         *                     } catch (IOException e) {
         *                         throw new CustomException(CategoryExceptionInfo.THUMBNAIL_UPLOAD_FAILED);
         *                     }
         *                 });
         *
         */
        String thumbUrl = createCategoryRequest.getThumbUrl();


        /**
         *         Category category = new Category(createCategoryRequest.getTitle(), thumbUrl, parent);
         *
         *         categoryRepository.save(category);
         *
         */
        Category category = new Category(null, createCategoryRequest.getTitle(), thumbUrl, parent);
        doNothing().when(categoryRepository).save(any(Category.class));

        CategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);

        /**
         *         return new CategoryResponse(
         *                 category.getId(),
         *                 category.getTitle(),
         *                 category.getThumbUrl(),
         *                 category.getFixedAt(),
         *                 category.getCreatedAt(),
         *                 createCategoryRequest.getParentId()
         *         );
         */
        CategoryResponse expectedResponse = new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getThumbUrl(),
                category.getFixedAt(),
                category.getCreatedAt(),
                createCategoryRequest.getParentId()
        );

        assertEquals(expectedResponse.getId(), categoryResponse.getId());
        assertEquals(expectedResponse.getTitle(), categoryResponse.getTitle());
        assertEquals(expectedResponse.getThumbUrl(), categoryResponse.getThumbUrl());
        assertEquals(createCategoryRequest.getParentId(), categoryResponse.getParentId());
    }
}