package corp.pjh.hello_blog_v2.category.service;

import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.dto.CreateCategoryRequest;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        Category parent = createCategoryRequest.getParent_id() != null ?
                categoryRepository.findById(createCategoryRequest.getParent_id()).get() :
                null;

        // thumbImageFile 업로드

        Category category = new Category(createCategoryRequest.getTitle(), "", parent);

        categoryRepository.save(category);
    }
}
