package corp.pjh.hello_blog_v2.category.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(name = "title_parent_id", columnNames = {"title", "parent_id_for_unique"})
})
@NoArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "thumb_url", nullable = false)
    private String thumbUrl;

    /**
     * DEFAULT로 설정, insert * update 쿼리 X
     * TIMESTAMP라 UTC로 저장 되는데, 일단 LocalDateTime으로 그대로 받고 DTO에서 한국 시간으로 변경하기!
     */
    @Column(name = "created_at",
            nullable = true,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    /**
     * GENERATED COLUMN, insert * update 쿼리 X
     */
    @Column(name = "parent_id_for_unique",
            columnDefinition = "BIGINT GENERATED ALWAYS AS (COALESCE(parent_id, -1)) STORED",
            insertable = false,
            updatable = false)
    private Long parentIdForUnique;

    /**
     * 값을 직접 넣으면 안 되는 필드를 제외한 생성자
     */
    public Category(String title, String thumbUrl, Category parent) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.parent = parent;

        this.parentIdForUnique = parent == null ? -1 : parent.getId();
    }

    public void updateCategory(String title, String thumbUrl, Category parent) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.parent = parent;

        this.parentIdForUnique = parent == null ? -1 : parent.getId();
    }
}
