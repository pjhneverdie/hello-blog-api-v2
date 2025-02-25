package corp.pjh.hello_blog_v2.category.service;

import corp.pjh.hello_blog_v2.category.domain.Category;
import corp.pjh.hello_blog_v2.category.dto.CategoryHierarchy;
import corp.pjh.hello_blog_v2.category.exception.CategoryExceptionInfo;
import corp.pjh.hello_blog_v2.common.dto.CustomException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryValidationService {
    private final CategoryCacheService categoryCacheService;

    /**
     * 부모가 없을 때
     * 같은 레벨에서의 카테고리명 중복 검사
     */
    public void checkSameAsCompanyTitle(String title) {
        List<CategoryHierarchy> hierarchies = categoryCacheService
                .toHierarchies(categoryCacheService.lookAsideJsonHierarchies().getData());

        boolean isDuplicated = hierarchies.stream().anyMatch((root) -> root.getTitle().equals(title));

        if (isDuplicated) {
            throw new CustomException(CategoryExceptionInfo.SAME_AS_COMPANY_TITLE);
        }
    }

    /**
     * 부모가 있을 때
     * 같은 레벨에서의 카테고리명 중복 검사
     */
    public void checkSameAsCompanyTitle(Category parent, String title) {
        List<CategoryHierarchy> hierarchies = categoryCacheService
                .toHierarchies(categoryCacheService.lookAsideJsonHierarchies().getData());

        boolean isDuplicated = categoryCacheService.findLocation(parent.getId(), hierarchies)
                .getChildren()
                .stream()
                .anyMatch((child) -> child.getTitle().equals(title));

        if (isDuplicated) {
            throw new CustomException(CategoryExceptionInfo.SAME_AS_COMPANY_TITLE);
        }
    }

    /**
     * 부모 <-> 자식 간의 카테고리명 중복 검사
     */
    public void checkSameAsParentTitle(Category parent, String title) {
        if (title.equals(parent.getTitle()))
            throw new CustomException(CategoryExceptionInfo.SAME_AS_PARENT_TITLE);
    }

    /**
     * parentId 수정 시 순환 참조 방지 검사
     */
    public void checkInValidHierarchy(Long updatedParentId, Long id) {
        List<CategoryHierarchy> hierarchies = categoryCacheService
                .toHierarchies(categoryCacheService.lookAsideJsonHierarchies().getData());

        CategoryHierarchy location = categoryCacheService.findLocation(id, hierarchies);

        List<Long> childrenIds = new ArrayList<>();
        collectIds(location, childrenIds);

        // 한 번 부모는 영원한 부모!!
        if (!childrenIds.stream().filter(updatedParentId::equals).toList().isEmpty())
            throw new CustomException(CategoryExceptionInfo.BROKEN_HIERARCHY);
    }

    /**
     * 하위 카테고리가 있으면 하위 카테고리 먼저 삭제해야 함
     */
    public void checkChildrenExist(Long id) {
        List<CategoryHierarchy> hierarchies = categoryCacheService
                .toHierarchies(categoryCacheService.lookAsideJsonHierarchies().getData());

        CategoryHierarchy hierarchy = categoryCacheService.findLocation(id, hierarchies);

        hierarchy.getChildren().forEach((child) -> {
            throw new CustomException(CategoryExceptionInfo.PARENT_NEVER_DIE);
        });
    }

    private void collectIds(CategoryHierarchy hierarchy, List<Long> ids) {
        for (CategoryHierarchy child : hierarchy.getChildren()) {
            ids.add(child.getId());
            collectIds(child, ids);
        }
    }
}