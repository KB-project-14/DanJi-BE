DROP TABLE IF EXISTS badge;

# 뱃지
CREATE TABLE badge
(
    badge_id          BINARY(16) PRIMARY KEY,
    name              VARCHAR(25)                       NOT NULL,
    badge_type        ENUM ('NORMAL', 'SPECIAL') NOT NULL,
    region_id         BIGINT                     NOT NULL,
    created_at        DATETIME                          NOT NULL,
    updated_at        DATETIME                          NOT NULL,
    comment           VARCHAR(255)                      NOT NULL
    FOREIGN KEY (region_id) REFERENCES region (region_id)
);

INSERT INTO badge (badge_id, name, badge_type, region_id, created_at, updated_at, comment) VALUES

-- 서울특별시
(UUID_TO_BIN(UUID()), '서울특별시', 'NORMAL', 11000, NOW(), NOW(),
 '도심 속 한강 바람을 타고, 네 마음도 잠시 쉬어가요.'),
(UUID_TO_BIN(UUID()), '불꽃축제의 밤', 'SPECIAL', 11000, NOW(), NOW(),
 '밤하늘을 가득 메운 불꽃처럼, 너와의 순간도 반짝입니다.'),

-- 부산광역시
(UUID_TO_BIN(UUID()), '반짝반짝 광안대교', 'NORMAL', 26000, NOW(), NOW(),
 '파도 소리를 머금은 대교 위, 부산의 밤이 너를 반깁니다.'),
(UUID_TO_BIN(UUID()), '부산 불꽃축제', 'SPECIAL', 26000, NOW(), NOW(),
 '광안리 바다 위에 터지는 불꽃, 그 설렘을 담아왔어요.'),

-- 대구광역시
(UUID_TO_BIN(UUID()), '수줍은 사과', 'NORMAL', 27000, NOW(), NOW(),
 '빨갛게 물든 사과처럼, 달콤하고 수줍은 마음을 전해요.'),
(UUID_TO_BIN(UUID()), '치맥페스티벌', 'SPECIAL', 27000, NOW(), NOW(),
 '한여름 밤, 치킨과 맥주로 가득 찬 축제의 열기를 느껴봐요.'),

-- 인천광역시
(UUID_TO_BIN(UUID()), '떴다떴다 비행기', 'NORMAL', 28000, NOW(), NOW(),
 '푸른 바다 위를 나는 비행기, 설렘 가득한 인천의 시작점이에요.'),
(UUID_TO_BIN(UUID()), '인천 펜타포트', 'SPECIAL', 28000, NOW(), NOW(),
 '락의 리듬과 바닷바람이 만나는, 여름의 뜨거운 무대입니다.'),

-- 광주광역시
(UUID_TO_BIN(UUID()), '빛고을 반짝이', 'NORMAL', 29000, NOW(), NOW(),
 '햇살이 머문 도시, 빛으로 가득 찬 광주의 하루를 전해요.'),
(UUID_TO_BIN(UUID()), '광주 비엔날레', 'SPECIAL', 29000, NOW(), NOW(),
 '예술의 숨결이 흐르는 거리, 창작의 불빛이 타오릅니다.'),

-- 대전광역시
(UUID_TO_BIN(UUID()), '공공칠빵', 'NORMAL', 30000, NOW(), NOW(),
 '칠판 위 분필가루처럼, 대전의 추억도 하얗게 남아요.'),
(UUID_TO_BIN(UUID()), '대전 사이언스 페스타', 'SPECIAL', 30000, NOW(), NOW(),
 '별빛과 과학이 만나는 축제, 아이 같은 호기심을 깨워요.'),

-- 울산광역시
(UUID_TO_BIN(UUID()), '고래의 콧바람', 'NORMAL', 31000, NOW(), NOW(),
 '푸른 바다를 가르는 고래의 숨결처럼 시원한 울산이에요.'),
(UUID_TO_BIN(UUID()), '울산 고래축제', 'SPECIAL', 31000, NOW(), NOW(),
 '고래가 뛰노는 바다 위, 환호와 물보라가 가득합니다.'),

-- 세종특별자치시
(UUID_TO_BIN(UUID()), '세종대왕의 붓끝', 'NORMAL', 36000, NOW(), NOW(),
 '한 글자 한 글자, 마음을 담아 세종의 숨결을 전합니다.'),
(UUID_TO_BIN(UUID()), '세종 문화축제', 'SPECIAL', 36000, NOW(), NOW(),
 '역사와 미래가 공존하는 무대, 빛나는 세종의 밤이에요.'),

-- 경기도
(UUID_TO_BIN(UUID()), '별빛 수원화성', 'NORMAL', 41000, NOW(), NOW(),
 '성벽 위 반짝이는 별빛처럼, 경기도의 밤이 포근합니다.'),
(UUID_TO_BIN(UUID()), '수원화성문화제', 'SPECIAL', 41000, NOW(), NOW(),
 '행궁의 불빛 속 행렬, 시간 여행 같은 축제가 펼쳐져요.'),

-- 강원도
(UUID_TO_BIN(UUID()), '행운의 감자', 'NORMAL', 42000, NOW(), NOW(),
 '소박한 감자 속 포근함, 강원의 따뜻함을 담았어요.'),
(UUID_TO_BIN(UUID()), '강릉 단오제', 'SPECIAL', 42000, NOW(), NOW(),
 '푸른 하늘 아래 풍년을 기원하는 흥겨운 단오의 춤사위.'),

-- 충청북도
(UUID_TO_BIN(UUID()), '퐁퐁 포도알', 'NORMAL', 43000, NOW(), NOW(),
 '달콤하게 톡 터지는 포도알, 청정한 충북의 맛이에요.'),
(UUID_TO_BIN(UUID()), '청원 생명축제', 'SPECIAL', 43000, NOW(), NOW(),
 '풍요로운 생명의 노래가 울려 퍼지는 축제의 들판입니다.'),

-- 충청남도
(UUID_TO_BIN(UUID()), '행복한 딸기요정', 'NORMAL', 44000, NOW(), NOW(),
 '빨갛게 익은 딸기처럼 달콤한 행복을 전해요.'),
(UUID_TO_BIN(UUID()), '논산 딸기축제', 'SPECIAL', 44000, NOW(), NOW(),
 '딸기향 가득한 봄날, 달콤한 미소가 피어납니다.'),

-- 전라북도
(UUID_TO_BIN(UUID()), '비벼비벼 행운밥', 'NORMAL', 45000, NOW(), NOW(),
 '다 같이 비벼 먹는 즐거움, 전북의 따뜻한 정을 담았어요.'),
(UUID_TO_BIN(UUID()), '전주 한지문화제', 'SPECIAL', 45000, NOW(), NOW(),
 '고운 한지에 스며드는 색처럼, 전주의 멋을 물들입니다.'),

-- 전라남도
(UUID_TO_BIN(UUID()), '영광스런 굴비', 'NORMAL', 46000, NOW(), NOW(),
 '바닷바람에 말린 굴비처럼 깊은 전남의 맛과 향을 전해요.'),
(UUID_TO_BIN(UUID()), '보성 녹차대축제', 'SPECIAL', 46000, NOW(), NOW(),
 '푸른 차밭 위로 불어오는 바람, 싱그러운 축제가 시작돼요.'),

-- 경상북도
(UUID_TO_BIN(UUID()), '되게 행복한 대게', 'NORMAL', 47000, NOW(), NOW(),
 '집게발 가득 행복을 안고, 경북의 푸른 바다에서 건너왔어요.'),
(UUID_TO_BIN(UUID()), '안동 탈춤페스티벌', 'SPECIAL', 47000, NOW(), NOW(),
 '탈 속에 담긴 웃음과 풍자, 전통의 흥이 넘쳐흐릅니다.'),

-- 경상남도
(UUID_TO_BIN(UUID()), '반짝반짝 행운밤', 'NORMAL', 48000, NOW(), NOW(),
 '밤알처럼 단단한 행운이 가득, 경남의 품에서 전해요.'),
(UUID_TO_BIN(UUID()), '진주 남강유등축제', 'SPECIAL', 48000, NOW(), NOW(),
 '강 위를 흐르는 수천 개의 등불, 소원을 품고 흘러갑니다.'),

-- 제주특별자치도
(UUID_TO_BIN(UUID()), '돌하르방의 청혼', 'NORMAL', 50000, NOW(), NOW(),
 '당신의 진심이 닿았습니다. 제주의 돌하르방이 꽃을 들고 황금빛 마음으로 고백합니다.'),
(UUID_TO_BIN(UUID()), '제주 들불축제', 'SPECIAL', 50000, NOW(), NOW(),
 '한라산 자락의 불빛 물결, 들판을 달리는 뜨거운 축제입니다.');
