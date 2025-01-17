package corp.pjh.hello_blog_v2.post.domain;

import corp.pjh.hello_blog_v2.category.domain.Category;

import corp.pjh.hello_blog_v2.common.util.TimeUtil;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
public class Post {
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
        this.title = title;
        this.content = content;
        this.thumbUrl = thumbUrl;
        this.fixedAt = TimeUtil.getLocalDateTimeFormatUTC();
        this.createdAt = this.fixedAt;
        this.viewCount = 0L;
        this.category = category;
    }

    /**
     * 조회수는 redis로 캐싱해서 가끔 한 번씩 따로 업데이트
     */
    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void updatePost(String title, String content, String thumbUrl, Long viewCount, Category category) {
        this.title = title;
        this.content = content;
        this.thumbUrl = thumbUrl;
        this.fixedAt = TimeUtil.getLocalDateTimeFormatUTC();
        this.createdAt = this.fixedAt;
        this.viewCount = viewCount;
        this.category = category;
    }
}