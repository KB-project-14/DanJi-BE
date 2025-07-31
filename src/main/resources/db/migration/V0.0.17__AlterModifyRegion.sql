ALTER TABLE region
    MODIFY city VARCHAR(50) NULL;

INSERT INTO region (region_id, province, city, created_at, updated_at)
VALUES (11000, '서울특별시', NULL, NOW(), NOW()),
       (26000, '부산광역시', NULL, NOW(), NOW()),
       (27000, '대구광역시', NULL, NOW(), NOW()),
       (28000, '인천광역시', NULL, NOW(), NOW()),
       (29000, '광주광역시', NULL, NOW(), NOW()),
       (30000, '대전광역시', NULL, NOW(), NOW()),
       (31000, '울산광역시', NULL, NOW(), NOW()),
       (36000, '세종특별자치시', NULL, NOW(), NOW()),
       (41000, '경기도', NULL, NOW(), NOW()),
       (42000, '강원도', NULL, NOW(), NOW()),
       (43000, '충청북도', NULL, NOW(), NOW()),
       (44000, '충청남도', NULL, NOW(), NOW()),
       (45000, '전라북도', NULL, NOW(), NOW()),
       (46000, '전라남도', NULL, NOW(), NOW()),
       (47000, '경상북도', NULL, NOW(), NOW()),
       (48000, '경상남도', NULL, NOW(), NOW()),
       (50000, '제주특별자치도', NULL, NOW(), NOW());