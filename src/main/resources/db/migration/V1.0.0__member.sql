-- 멤버 테이블 생성 --

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(50)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);