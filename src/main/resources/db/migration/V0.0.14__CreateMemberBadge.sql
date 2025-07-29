DROP TABLE IF EXISTS member_badge;

# 멤버 뱃지
CREATE TABLE member_badge
(
    member_id         BIGINT     PRIMARY KEY,
    badge_id          BINARY(16),
    created_at        DATETIME                          NOT NULL,
    updated_at        DATETIME                          NOT NULL,
        FOREIGN KEY (badge_id) REFERENCES badge (badge_id)
);