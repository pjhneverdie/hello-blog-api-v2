package corp.pjh.hello_blog_v2.category.domain;

import corp.pjh.hello_blog_v2.common.util.TimeUtil;

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
     * update 쿼리 X,
     * 값 세팅할 때 UTC 시간을 양식만 LocalDateTime으로 바꿔서 세팅하기!!!
     */
    @Column(name = "fixed_at", nullable = false, updatable = false)
    private LocalDateTime fixedAt;

    /**
     * 값 세팅할 때 UTC 시간을 양식만 LocalDateTime으로 바꿔서 세팅하기!!!
     */
    @Column(name = "created_at", nullable = false)
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

    public Category(String title, String thumbUrl, Category parent) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.fixedAt = TimeUtil.getLocalDateTimeFormatUTC();
        this.createdAt = this.fixedAt;
        this.parent = parent;
        this.parentIdForUnique = parent == null ? -1 : parent.getId();

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    public void updateCategory(String title, String thumbUrl, Category parent) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.fixedAt = TimeUtil.getLocalDateTimeFormatUTC();
        this.parent = parent;
        this.parentIdForUnique = parent == null ? -1 : parent.getId();

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }
}
