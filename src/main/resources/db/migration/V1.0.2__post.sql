-- 게시글 테이블 생성 --

CREATE TABLE post
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL UNIQUE,
    content     TEXT         NOT NULL,
    thumb_url   TEXT,
    fixed_at    DATETIME     NOT NULL,
    created_at  DATETIME     NOT NULL,
    view_count  BIGINT       NOT NULL DEFAULT 0,
    category_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE RESTRICT
);