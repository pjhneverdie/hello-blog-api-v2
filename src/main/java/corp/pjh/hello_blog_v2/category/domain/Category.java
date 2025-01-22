package corp.pjh.hello_blog_v2.category.domain;

import corp.pjh.hello_blog_v2.common.domain.EntityWithTime;
import corp.pjh.hello_blog_v2.common.util.TimeUtil;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(name = "title_parent_id", columnNames = {"title", "parent_id_for_unique"})
})
@NoArgsConstructor
@Getter
public class Category extends EntityWithTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "thumb_url", nullable = false)
    private String thumbUrl;

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
        super(TimeUtil.getLocalDateTimeFormatUTC());

        this.title = title;
        this.thumbUrl = thumbUrl;
        this.parent = parent;
        this.parentIdForUnique = parent == null ? -1 : parent.getId();

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    public void updateCategory(String title, String thumbUrl, Category parent) {
        super.updateFixedAt(TimeUtil.getLocalDateTimeFormatUTC());

        this.title = title;
        this.thumbUrl = thumbUrl;
        this.parent = parent;
        this.parentIdForUnique = parent == null ? -1 : parent.getId();

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    /**
     * 테스트용!!! 테스트에서만 사용
     */
    public Category(Long id, String title, String thumbUrl, Category parent) {
        super(TimeUtil.getLocalDateTimeFormatUTC());

        this.id = id;
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.parent = parent;
        this.parentIdForUnique = parent == null ? -1 : parent.getId();

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }
}