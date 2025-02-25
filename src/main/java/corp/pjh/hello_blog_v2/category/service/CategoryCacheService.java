package corp.pjh.hello_blog_v2.category.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.dto.CategoryHierarchy;
import corp.pjh.hello_blog_v2.category.exception.CategoryExceptionInfo;
import corp.pjh.hello_blog_v2.category.repository.CategoryRepository;
import corp.pjh.hello_blog_v2.common.dto.CustomException;
import corp.pjh.hello_blog_v2.redis.dto.LookAsideResult;
import corp.pjh.hello_blog_v2.redis.service.CacheService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryCacheService {
    private final ObjectMapper objectMapper;
    private final CacheService cacheService;
    private final CategoryRepository categoryRepository;

    private static final String CACHED_HIERARCHIES_KEY = "HIERARCHIES";

    public void save(Category category) {
        List<CategoryHierarchy> hierarchies = toHierarchies(lookAsideJsonHierarchies().getData());

        if (category.getParent() == null) {
            hierarchies.add(new CategoryHierarchy(category));
        } else {
            CategoryHierarchy parentLocation = findLocation(
                    category.getParent().getId(),
                    hierarchies
            );

            parentLocation.getChildren().add(new CategoryHierarchy(category));
        }

        try {
            cacheService.set(CACHED_HIERARCHIES_KEY, objectMapper.writeValueAsString(hierarchies));
        } catch (Exception e) {
            throw new CustomException(CategoryExceptionInfo.CACHING_FAILED);
        }
    }

    public void update(Category category) {
        delete(category);
        save(category);
    }

    public void delete(Category category) {
        List<CategoryHierarchy> hierarchies = toHierarchies(lookAsideJsonHierarchies().getData());

        CategoryHierarchy location = findLocation(category.getId(), hierarchies);

        if (location.getParentId() == null) {
            hierarchies.remove(location);
        } else {
            findLocation(location.getParentId(), hierarchies).getChildren().remove(location);
        }

        try {
            cacheService.set(CACHED_HIERARCHIES_KEY, objectMapper.writeValueAsString(hierarchies));
        } catch (Exception e) {
            throw new CustomException(CategoryExceptionInfo.CACHING_FAILED);
        }
    }

    public LookAsideResult lookAsideJsonHierarchies() {
        LookAsideResult lookAsideResult = new LookAsideResult();
        lookAsideResult.setData(
                Optional.ofNullable(cacheService.get(CACHED_HIERARCHIES_KEY))
                        .orElseGet(() -> {
                                    lookAsideResult.setFromDB(true);

                                    List<Category> categories = categoryRepository.findAll();

                                    List<CategoryHierarchy> hierarchies = makeHierarchies(categories);

                                    try {
                                        cacheService.set(
                                                CACHED_HIERARCHIES_KEY,
                                                objectMapper.writeValueAsString(hierarchies));
                                    } catch (Exception e) {
                                        throw new CustomException(CategoryExceptionInfo.CACHING_FAILED);
                                    }

                                    return cacheService.get(CACHED_HIERARCHIES_KEY);
                                }
                        )
        );

        return lookAsideResult;
    }

    private List<CategoryHierarchy> makeHierarchies(List<Category> categories) {
        return categories.stream()
                .filter(category -> category.getParent() == null)
                .map(CategoryHierarchy::new)
                .collect(Collectors.toList());
    }

    public List<CategoryHierarchy> toHierarchies(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new CustomException(CategoryExceptionInfo.UNREADABLE_CACHE);
        }
    }

    public CategoryHierarchy findLocation(long id, List<CategoryHierarchy> hierarchies) {
        for (CategoryHierarchy hierarchy : hierarchies) {
            if (id == hierarchy.getId()) {
                return hierarchy;
            } else {
                CategoryHierarchy location = findLocation(id, hierarchy.getChildren());

                if (location != null) {
                    return location;
                }
            }
        }

        return null;
    }
}
