DROP TABLE IF EXISTS member_badge;

# 멤버 뱃지
CREATE TABLE member_badge
(
    member_badge_id         BINARY(16)     PRIMARY KEY,
    member_id               BINARY(16)                  NOT NULL,
    badge_id                BINARY(16)                  NOT NULL,
    badge_grade             ENUM('BRONZE', 'SILVER', 'GOLD', 'SPECIAL')     NOT NULL,
    created_at        DATETIME                          NOT NULL,
    updated_at        DATETIME                          NOT NULL,
        FOREIGN KEY (badge_id) REFERENCES badge (badge_id),
        FOREIGN KEY (member_id) REFERENCES member (member_id)
);