package corp.pjh.hello_blog_v2.post.domain;

import corp.pjh.hello_blog_v2.category.domain.Category;

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
     * DEFAULT로 설정, insert * update 쿼리 X
     * TIMESTAMP라 UTC로 저장 되는데, 일단 LocalDateTime으로 그대로 받고 DTO에서 한국 시간으로 변경하기!
     */
    @Column(name = "created_at",
            nullable = true,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false)
    private LocalDateTime createdAt;

    /**
     * DEFAULT로 설정, insert쿼리 X
     */
    @Column(name = "view_count",
            nullable = true,
            insertable = false)
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Post(String title, String content, String thumbUrl, Category category) {
        this.title = title;
        this.content = content;
        this.thumbUrl = thumbUrl;
        this.category = category;
    }

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void updatePost(String title, String content, String thumbUrl, Category category) {
        this.title = title;
        this.content = content;
        this.thumbUrl = thumbUrl;
        this.category = category;
    }
}
