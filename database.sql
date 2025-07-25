use danji;

INSERT INTO local_currency
VALUES (UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 26001, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 26002, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26003, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26004, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26005, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26006, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26007, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26008, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26009, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26010, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26011, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26012, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26013, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26014, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26015, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW()),
       (UUID_TO_BIN(UUID()), 26016, '동백전', 'CASHBACK', 500000, 5, NOW(), NOW());


INSERT INTO available_merchant
VALUES
    (UUID_TO_BIN(UUID()),'아이즈광고기획', '부산광역시 사상구 사상로 467(모라동)', 0.0, 0.0, '기타', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'피자나라치킨공주', '부산광역시 사상구 사상로 468-11층 (모라동)', 0.0, 0.0, '음식점', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'어가스시', '부산광역시 사상구 사상로 468-1어가스시 (모라동)', 0.0, 0.0, '기타', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'멀티25시슈퍼', '부산광역시 사상구 사상로 46924시마트 1층 (모라동)', 0.0, 0.0, '슈퍼/마트', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'꼬꼬치킨', '부산광역시 사상구 사상로 471-1(모라동)', 0.0, 0.0, '음식점', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'이루다식당', '부산광역시 사상구 사상로 471-1(모라동)', 0.0, 0.0, '기타', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'남도푸드', '부산광역시 사상구 사상로 471-1(모라동)', 0.0, 0.0, '음식점', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'빌존아지트', '부산광역시 사상구 사상로 471-13층 (모라동, 캐롱샷)', 0.0, 0.0, '기타', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'동백베이커리', '부산광역시 사상구 사상로 475-141층 (모라동)', 0.0, 0.0, '카페/베이커리', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW()),
    (UUID_TO_BIN(UUID()),'동백커피', '부산광역시 사상구 사상로 475-142층 (모라동)', 0.0, 0.0, '음식점', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), NOW(), NOW());

SELECT BIN_TO_UUID(local_currency_id) FROM local_currency;
