CREATE TABLE member_badge
(
    member_badge_id BINARY(16) PRIMARY KEY,
    member_id       BINARY(16) NOT NULL,
    badge_id        BINARY(16) NOT NULL,
    badge_grade     ENUM ('BRONZE', 'SILVER', 'GOLD', 'SPECIAL') NOT NULL,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,

    FOREIGN KEY (member_id) REFERENCES member (member_id),
    FOREIGN KEY (badge_id) REFERENCES badge (badge_id)
);