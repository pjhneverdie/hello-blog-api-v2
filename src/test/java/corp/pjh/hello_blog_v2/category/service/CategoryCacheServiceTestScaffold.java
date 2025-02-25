package corp.pjh.hello_blog_v2.category.service;

import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class CategoryCacheServiceTestScaffold {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    Category root1;
    Category root1_child1;
    Category root1_child2;
    Category root1_child2_child1;
    Category root1_child2_child2;

    @BeforeEach
    public void init() {
        redisTemplate.keys("*").stream().forEach(k -> {
            redisTemplate.delete(k);
        });

        root1 = new Category(
                "root1",
                "thumbUrl",
                null
        );

        root1_child1 = new Category(
                "root1_child1",
                "thumbUrl",
                root1
        );

        root1_child2 = new Category(
                "root1_child2",
                "thumbUrl",
                root1
        );

        root1_child2_child1 = new Category(
                "root1_child2_child1",
                "thumbUrl",
                root1_child2
        );

        root1_child2_child2 = new Category(
                "root1_child2_child2",
                "thumbUrl",
                root1_child2
        );

        categoryRepository.save(root1);
        categoryRepository.save(root1_child1);
        categoryRepository.save(root1_child2);
        categoryRepository.save(root1_child2_child1);
        categoryRepository.save(root1_child2_child2);
    }

    @AfterEach
    public void clean() {
        categoryRepository.delete(root1);
        categoryRepository.delete(root1_child1);
        categoryRepository.delete(root1_child2);
        categoryRepository.delete(root1_child2_child1);
        categoryRepository.delete(root1_child2_child2);

        redisTemplate.keys("*").stream().forEach(k -> {
            redisTemplate.delete(k);
        });
    }
}
