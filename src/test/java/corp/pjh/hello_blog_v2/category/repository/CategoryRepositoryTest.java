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
    void 연관관계_편의_메서드_불필요_테스트() {
        // Given
        Category category1 = new Category("category1_제목", "썸네일 주소", null);

        categoryRepository.save(category1);

        /**
         * category1은 이미 db에 있다고 가정
         */
        em.flush();
        em.clear();

        category1 = em.find(Category.class, category1.getId());

        /**
         * 연관관계 편의 메서드
         * 아직 초기화 되지 않은 프록시 컬렉션에 요소를 추가(category1.children), 요소 필드를 직접적으로 조회하지 않는 이상 조회 쿼리는 안 나감(LAZY)
         */
        Category category2 = new Category("category2_제목", "썸네일 주소", category1);
        Category category3 = new Category("category3_제목", "썸네일 주소", category1);

        category1.getChildren().add(new Category());
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        /**
         * 연관관계 편의 메서드로 초기화되지 않은 프록시 컬렉션에 요소를 추가할 때,
         * 아래 두 가지를 경우를 조심!!!
         *
         * 1. 영속성 콘텍스트에 있는 엔티티가 컬렉션에 추가된 경우(category2, category3), 실제 조회 쿼리가 나가도 덮어쓰지 않음
         * 2. 그냥 아무 오브젝트나 타입만 맞게 해서 넣고(new Category()), 실제 조회 쿼리가 나가도 기존에 넣은 값이 사라지지 않음
         */

        /**
         * 보면 요소 세 개가 들어 있음
         * 조회 쿼리가 나갔지만 주의 사항 2번처럼 new Category()가 유지됐고,
         * 1번은 이 테스트로는 확인할 수 없지만 만약 db에서 세팅해 주는 Default 값이 있었다면 덮어쓰지 않았을 것임!!
         */
        assertEquals(3, category1.getChildren().size());
    }

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