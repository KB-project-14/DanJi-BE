INSERT INTO local_currency
(local_currency_id, region_id, name, benefit_type, maximum, percentage, created_at, updated_at)
VALUES
    (UUID_TO_BIN(UUID()), 11000, '서울사랑상품권', 'DISCOUNT_CHARGE', 700000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 26000, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 27000, '대구행복페이', 'DISCOUNT_CHARGE', 500000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 28000, '인천e음카드', 'CASHBACK', 500000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 29000, '광주상생카드', 'DISCOUNT_CHARGE', 500000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 30000, '온통대전', 'CASHBACK', 500000, 7, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 31000, '울산페이', 'BONUS_CHARGE', 200000, 7, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 36000, '여민전', 'CASHBACK', 300000, 5, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 50000, '탐나는전', 'BONUS_CHARGE', 700000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 45002, '군산사랑카드', 'DISCOUNT_CHARGE', 500000, 10, NOW(), NOW()),
    (UUID_TO_BIN(UUID()), 45003, '익산다e로움', 'BONUS_CHARGE', 500000, 5, NOW(), NOW());
