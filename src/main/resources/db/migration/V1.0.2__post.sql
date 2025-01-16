-- 게시글 테이블 생성 --
-- * DEFAULT 값이 설정된 COLUMN은 아예 파라미터에 포함시키지 말 것!! NULL도 값이기 때문에 DEFAULT 값이 적용되지 않음!!--

CREATE TABLE post
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL UNIQUE,
    content     TEXT         NOT NULL,
    thumb_url   TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    view_count  BIGINT    DEFAULT 0,
    category_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE RESTRICT
);