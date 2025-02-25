package corp.pjh.hello_blog_v2.category.repository;

import corp.pjh.hello_blog_v2.category.domain.Category;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 카테고리_생성_테스트() {
        // Given
        Category category = new Category("제목", "썸네일 주소", null);

        // When
        categoryRepository.save(category);

        // Then
        Optional<Category> saved = categoryRepository.findById(category.getId());

        assertEquals(saved.get(), category);
    }

    @Test
    void 카테고리_업데이트_테스트() {
        // Given
        Category category1 = new Category("category1_제목", "썸네일 주소", null);

        categoryRepository.save(category1);

        Category category2 = new Category("category2_제목", "썸네일 주소", category1);
        Category category3 = new Category("category3_제목", "썸네일 주소", category1);

        categoryRepository.save(category2);
        categoryRepository.save(category3);

        em.flush();

        // When
        category3.updateCategory("category3_제목_업데이트", "썸네일 주소", null);

        em.flush();

        // Then
        Optional<Category> updated = categoryRepository.findById(category3.getId());

        assertEquals(updated.get(), category3);
    }


    @Test
    void 모든_카테고리_모든_필드_페치_조인() {
        // Given
        Category category1 = new Category("category1_제목", "썸네일 주소", null);
        categoryRepository.save(category1);

        Category category2 = new Category("category2_제목", "썸네일 주소", category1);
        Category category3 = new Category("category3_제목", "썸네일 주소", category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        Category category4 = new Category("category4_제목", "썸네일 주소", category3);
        categoryRepository.save(category4);

        em.clear();

        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertEquals(4, categories.size());
        assertEquals(2, categories.stream().filter((category -> category.getId().equals(category1.getId()))).findAny().get().getChildren().size());
        assertEquals(1, categories.stream()
                .filter((category -> category.getId().equals(category1.getId())))
                .findFirst().get().getChildren().stream().filter((category -> category.getId().equals(category3.getId()))).findAny().get().getChildren().size());
    }
}