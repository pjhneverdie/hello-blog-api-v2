package corp.pjh.hello_blog_v2.category.domain;

import corp.pjh.hello_blog_v2.common.domain.EntityWithTime;
import corp.pjh.hello_blog_v2.common.util.TimeUtil;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
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

    public Category(String title, String thumbUrl, Category parent) {
        super(TimeUtil.getUTCLocalDatetime());

        this.title = title;
        this.thumbUrl = thumbUrl;
        this.parent = parent;

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    public void updateCategory(String title, String thumbUrl, Category parent) {
        super.updateFixedAt(TimeUtil.getUTCLocalDatetime());

        this.title = title;
        this.thumbUrl = thumbUrl;
        this.parent = parent;

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }
}