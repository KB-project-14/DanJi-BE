INSERT INTO file
    (file_id, original_name, stored_name, file_path, content_type, file_size, created_at, updated_at)
VALUES
      (UUID_TO_BIN(UUID()), 'busan.jpg', '동백전.jpg', '/static/images/busan.jpg', 'jpg', 18007, NOW(), NOW()),
      (UUID_TO_BIN(UUID()), 'daegu.jpg', '대구로페이.jpg', '/static/images/daegu.jpg', 'jpg', 25875, NOW(), NOW()),
      (UUID_TO_BIN(UUID()), 'gunsan.jpg', '군산사랑상품권.jpg', '/static/images/gunsan.jpg', 'jpg', 65580, NOW(), NOW()),
      (UUID_TO_BIN(UUID()), 'gwangju.jpg', '광주상생카드.jpg', '/static/images/gwangju.jpg', 'jpg', 19205, NOW(), NOW()),
      (UUID_TO_BIN(UUID()), 'iksan.jpg', '익산다이로움카드.jpg', '/static/images/iksan.jpg', 'jpg', 39744, NOW(), NOW()),
      (UUID_TO_BIN(UUID()), 'jeju.jpg', '탐나는전.jpg', '/static/images/jeju.jpg', 'jpg', 19673, NOW(), NOW()),
      (UUID_TO_BIN(UUID()), 'ulsan.jpg', '울산페이.jpg', '/static/images/ulsan.jpg', 'jpg', 18398, NOW(), NOW());

INSERT INTO file_reference
(file_reference_id, file_id, reference_type, reference_id, usage_type, created_at, updated_at)
VALUES
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'busan.jpg'), 'local_currency', (SELECT local_currency_id FROM local_currency WHERE name = '동백전'), 'card_image', NOW(), NOW()),
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'daegu.jpg'), 'local_currency', (SELECT local_currency_id FROM local_currency WHERE name = '대구로페이'), 'card_image', NOW(), NOW()),
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'gunsan.jpg'), 'local_currency', (SELECT local_currency_id FROM local_currency WHERE name = '군산사랑상품권'), 'card_image', NOW(), NOW()),
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'gwangju.jpg'), 'local_currency', (SELECT local_currency_id FROM local_currency WHERE name = '광주상생카드'), 'card_image', NOW(), NOW()),
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'iksan.jpg'), 'local_currency', (SELECT local_currency_id FROM local_currency WHERE name = '익산다이로움카드'), 'card_image', NOW(), NOW()),
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'jeju.jpg'), 'local_currency', (SELECT local_currency_id FROM local_currency WHERE name = '탐나는전'), 'card_image', NOW(), NOW()),
    (UUID_TO_BIN(UUID()), (SELECT file_id FROM file WHERE original_name = 'ulsan.jpg'), 'local_currency', (SELECT local_currency_id FROM local_currency WHERE name = '울산페이'), 'card_image', NOW(), NOW());