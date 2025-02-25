package corp.pjh.hello_blog_v2.category.repository;

import corp.pjh.hello_blog_v2.category.domain.Category;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final EntityManager em;

    public void save(Category category) {
        em.persist(category);
    }

    public void delete(Category category) {
        em.remove(category);
    }

    public Optional<Category> findById(long id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    public List<Category> findAll() {
        String query = "SELECT c FROM Category c LEFT JOIN FETCH c.parent LEFT JOIN FETCH c.children";

        return em.createQuery(query, Category.class).getResultList();
    }
}
