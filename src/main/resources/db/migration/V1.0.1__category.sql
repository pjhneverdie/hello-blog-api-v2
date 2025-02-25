-- 카테고리 테이블 생성 --

CREATE TABLE category
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    title      VARCHAR(255) NOT NULL,
    thumb_url  TEXT,
    fixed_at   DATETIME     NOT NULL,
    created_at DATETIME     NOT NULL,
    parent_id  BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE RESTRICT
);