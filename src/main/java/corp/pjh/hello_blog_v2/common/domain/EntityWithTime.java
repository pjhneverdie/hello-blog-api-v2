package corp.pjh.hello_blog_v2.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
public class EntityWithTime {
    /**
     * 값 세팅할 때 UTC 시간을 양식만 LocalDateTime으로 바꿔서 세팅하기!!!
     */
    @Column(name = "fixed_at", nullable = false)
    private LocalDateTime fixedAt;

    /**
     * update 쿼리 X,
     */
    @Column(name = "created_at", nullable = false,
            updatable = false)
    private LocalDateTime createdAt;

    public EntityWithTime(LocalDateTime fixedAt) {
        this.fixedAt = fixedAt;
        this.createdAt = fixedAt;
    }

    protected void updateFixedAt(LocalDateTime fixedAt) {
        this.fixedAt = fixedAt;
    }
}
