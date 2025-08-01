DROP TABLE IF EXISTS badge;

# 뱃지
CREATE TABLE badge
(
    badge_id          BINARY(16) PRIMARY KEY,
    name              VARCHAR(25)                       NOT NULL,
    badge_type        ENUM ('NORMAL', 'SPECIAL') NOT NULL,
    region_id         BIGINT                     NOT NULL,
    comment           VARCHAR(255)                      NOT NULL,
    created_at        DATETIME                          NOT NULL,
    updated_at        DATETIME                          NOT NULL,
    FOREIGN KEY (region_id) REFERENCES region (region_id)
);