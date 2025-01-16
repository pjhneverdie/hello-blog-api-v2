package corp.pjh.hello_blog_v2.category.repository;

import corp.pjh.hello_blog_v2.category.domain.Category;

import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

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
    void 카테고리_저장_테스트() {
        // Given
        Category category = new Category("제목", "썸네일 주소", null);

        // When
        categoryRepository.save(category);

        // Then
        Optional<Category> saved = categoryRepository.findById(category.getId());

        assertEquals(saved.get(), category);
    }

    /**
     * 같은 레벨에서 카테고리 제목은 중복될 수 없음
     */
    @Test
    void 카테고리_저장_실패_테스트() {
        // Given
        Category category1 = new Category("제목", "썸네일 주소", null);
        Category category2 = new Category("제목", "썸네일 주소", null);

        categoryRepository.save(category1);

        // When, Then
        /**
         * ?? 왜 이건 ConstraintViolationException이 아닌지 모르겠음
         */
        assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(category2));
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

    /**
     * 같은 레벨에서 카테고리 제목은 중복될 수 없음
     */
    @Test
    void 카테고리_업데이트_실패_테스트() {
        // Given
        Category category1 = new Category("category1_제목", "썸네일 주소", null);

        categoryRepository.save(category1);

        Category category2 = new Category("category2_제목", "썸네일 주소", category1);
        Category category3 = new Category("category3_제목", "썸네일 주소", category1);

        categoryRepository.save(category2);
        categoryRepository.save(category3);

        em.flush();

        // When
        category3.updateCategory("category1_제목", "썸네일 주소", null);

        // Then
        assertThrows(ConstraintViolationException.class, () -> em.flush());
    }

}