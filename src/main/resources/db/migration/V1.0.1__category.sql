-- 카테고리 테이블 생성 --
-- * GENERATED COLUMN은 직접 INSERT 쿼리를 날리면 안 됨!!--
-- * DEFAULT 값이 설정된 COLUMN은 아예 파라미터에 포함시키지 말 것!! NULL도 값이기 때문에 DEFAULT 값이 적용되지 않음!!--

CREATE TABLE category
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    title                VARCHAR(255) NOT NULL,
    thumb_url            TEXT,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    parent_id            BIGINT,
    parent_id_for_unique BIGINT GENERATED ALWAYS AS (COALESCE(parent_id, -1)) STORED,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE RESTRICT,
    CONSTRAINT title_parent_id UNIQUE (title, parent_id_for_unique)
);