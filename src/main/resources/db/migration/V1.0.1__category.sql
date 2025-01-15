-- 카테고리 테이블 생성 --

CREATE TABLE category
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    title                VARCHAR(255) NOT NULL,
    thumb_url            TEXT,
    parent_id            BIGINT,
    parent_id_for_unique BIGINT GENERATED ALWAYS AS (COALESCE(parent_id, -1)) STORED,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE RESTRICT,
    CONSTRAINT title_parent_id UNIQUE (title, parent_id_for_unique)
);