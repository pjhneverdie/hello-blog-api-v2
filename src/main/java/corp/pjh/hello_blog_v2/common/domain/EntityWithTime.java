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
