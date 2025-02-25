package corp.pjh.hello_blog_v2.category.service;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCacheServiceTest extends CategoryCacheServiceTestScaffold {
    @Autowired
    CategoryCacheService categoryCacheService;

    @Test
    void 카테고리_캐싱_테스트() {
        categoryCacheService.save(root1);

        System.out.println(categoryCacheService.lookAsideJsonHierarchies().getData());
    }
}