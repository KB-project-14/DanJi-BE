INSERT INTO badge (badge_id, name, badge_type, region_id, comment) VALUES

-- 독도
(UUID_TO_BIN(UUID()), '독도 수호의 날', 'SPECIAL', 47023,'동해의 푸른 파도 위, 묵묵히 자리를 지키는 우리 땅, 독도입니다.');

INSERT INTO file
(file_id, original_name, stored_name, file_path, content_type, file_size, created_at, updated_at)
VALUES
    (UUID_TO_BIN(UUID()), 'dokdospecial.webp', '독도스페셜뱃지.webp', '/static/images/badge/dokdospecial.webp', 'webp', 260290, NOW(), NOW());

INSERT INTO file_reference
(file_reference_id, file_id, reference_type, reference_id, usage_type, created_at, updated_at)
VALUES
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'dokdospecial.webp'), 'badge', (SELECT badge_id FROM badge WHERE name = '독도 수호의 날'), 'badge_image_SPECIAL', NOW(), NOW());