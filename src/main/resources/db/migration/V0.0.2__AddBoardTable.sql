CREATE TABLE tbl_board
(
    no          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200) NOT NULL,
    content     TEXT         NOT NULL,
    writer      VARCHAR(100) NOT NULL,
    reg_date    datetime(6),
    update_date datetime(6),
    created_at  datetime(6),
    updated_at  datetime(6)
);

CREATE TABLE tbl_board_attachment
(
    no           BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 첨부파일 고유 번호
    bno          BIGINT       NOT NULL,              -- 게시글 번호 (외래 키)
    size         BIGINT       NOT NULL,              -- 파일 크기 (byte)
    content_type VARCHAR(255) NOT NULL,              -- MIME 타입 (ex: image/png)
    path         VARCHAR(500) NOT NULL,              -- 저장 경로
    filename     VARCHAR(255) NOT NULL,              -- 파일명
    reg_date     DATETIME, -- 등록일

    CONSTRAINT fk_board_attachment_board FOREIGN KEY (bno)
        REFERENCES tbl_board (no) ON DELETE CASCADE
);