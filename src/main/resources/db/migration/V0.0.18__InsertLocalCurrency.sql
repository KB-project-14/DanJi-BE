INSERT INTO local_currency
(local_currency_id, region_id, name, benefit_type, maximum, percentage, created_at, updated_at)
VALUES
    (UUID_TO_BIN(UUID()), 26000, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 27000, '대구로페이', 'DISCOUNT_CHARGE', 500000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 29000, '광주상생카드', 'DISCOUNT_CHARGE', 500000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 31000, '울산페이', 'BONUS_CHARGE', 200000, 7, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 50000, '탐나는전', 'BONUS_CHARGE', 700000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 45000, '군산사랑상품권', 'DISCOUNT_CHARGE', 500000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 45000, '익산다이로움카드', 'BONUS_CHARGE', 500000, 5, NOW(), NOW());
