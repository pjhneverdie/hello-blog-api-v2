package corp.pjh.hello_blog_v2.post.domain;

import corp.pjh.hello_blog_v2.category.domain.Category;

import corp.pjh.hello_blog_v2.common.domain.EntityWithTime;
import corp.pjh.hello_blog_v2.common.util.TimeUtil;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
public class Post extends EntityWithTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Lob
    @Column(name = "thumb_url", nullable = false)
    private String thumbUrl;

    /**
     * DEFAULT로 설정, insert 쿼리 X
     */
    @Column(name = "view_count",
            nullable = false,
            insertable = false)
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Post(String title, String content, String thumbUrl, Category category) {
        super(TimeUtil.getUTCLocalDatetime());

        this.title = title;
        this.content = content;
        this.thumbUrl = thumbUrl;
        this.viewCount = 0L;
        this.category = category;
    }

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void updatePost(String title, String content, String thumbUrl, Long viewCount, Category category) {
        super.updateFixedAt(TimeUtil.getUTCLocalDatetime());

        this.title = title;
        this.content = content;
        this.thumbUrl = thumbUrl;
        this.viewCount = viewCount;
        this.category = category;
    }
}