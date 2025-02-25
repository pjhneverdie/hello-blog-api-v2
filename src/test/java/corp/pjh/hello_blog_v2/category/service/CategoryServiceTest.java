package corp.pjh.hello_blog_v2.category.service;

import corp.pjh.hello_blog_v2.aws.service.S3UploadService;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private S3UploadService s3UploadService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryCacheService categoryCacheService;

    @Test
    public void 카테고리_생성_썸네일_업로드_테스트() {
    }

    @Test
    public void 루트_카테고리_생성_썸네일_업로드_테스트() {
    }

    @Test
    public void 카테고리_생성_썸네일url이_이미_있는_경우_테스트() {
    }

    @Test
    public void 루트_카테고리_생성_썸네일url이_이미_있는_경우_테스트() {
    }
}