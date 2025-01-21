package corp.pjh.hello_blog_v2.category.service;

import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.dto.CreateCategoryRequest;
import corp.pjh.hello_blog_v2.category.exception.CategoryExceptionInfo;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;
import corp.pjh.hello_blog_v2.aws.service.S3UploadService;
import corp.pjh.hello_blog_v2.common.dto.CustomException;
import corp.pjh.hello_blog_v2.common.util.TimeUtil;

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
    private final S3UploadService s3UploadService;
    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        Category parent = Optional
                .ofNullable(createCategoryRequest.getParentId())
                .flatMap(categoryRepository::findById)
                .orElse(null);

        String thumbUrl = Optional
                .ofNullable(createCategoryRequest.getThumbUrl())
                .orElseGet(() -> {
                    MultipartFile file = createCategoryRequest.getThumbImageFile();
                    String key = file.getOriginalFilename() + TimeUtil.getLocalDateTimeFormatUTC();

                    try {
                        return s3UploadService.putFile(file, key);
                    } catch (IOException e) {
                        throw new CustomException(CategoryExceptionInfo.THUMBNAIL_UPLOAD_FAILED);
                    }
                });

        categoryRepository.save(new Category(createCategoryRequest.getTitle(), thumbUrl, parent));
    }
}