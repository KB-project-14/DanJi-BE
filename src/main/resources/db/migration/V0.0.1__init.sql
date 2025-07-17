# 지역 테이블
CREATE TABLE region
(
    region_id  BINARY(16) PRIMARY KEY,
    province   VARCHAR(50) NOT NULL,
    city       VARCHAR(50) NOT NULL,
    created_at DATETIME    NOT NULL,
    updated_at DATETIME    NOT NULL
);

# 지역화폐
CREATE TABLE local_currency
(
    local_currency_id BINARY(16) PRIMARY KEY,
    region_id         BINARY(16)                    NOT NULL,
    name              VARCHAR(25)                   NOT NULL,
    benefit_type      ENUM ('CASHBACK', ' BONUS_CHARGE',  'DISCOUNT_CHARGE') NOT NULL,
    maximum           BIGINT                        NOT NULL,
    percentage        BIGINT                        NOT NULL,
    created_at        DATETIME                      NOT NULL,
    updated_at        DATETIME                      NOT NULL,
    FOREIGN KEY (region_id) REFERENCES region (region_id)
);

# 회원
CREATE TABLE member
(
    member_id  BINARY(16) PRIMARY KEY,
    username   VARCHAR(25)            NOT NULL,
    password   VARCHAR(255)           NOT NULL,
    role       ENUM ('USER', 'ADMIN') NOT NULL,
    name       VARCHAR(10)            NOT NULL,
    created_at DATETIME               NOT NULL,
    updated_at DATETIME               NOT NULL
);

# 지갑
CREATE TABLE wallet
(
    wallet_id         BINARY(16) PRIMARY KEY,
    member_id         BINARY(16)             NOT NULL,
    local_currency_id BINARY(16)             NOT NULL,
    wallet_type       ENUM ('CASH', 'LOCAL') NOT NULL,
    balance           INT                    NOT NULL,
    created_at        DATETIME               NOT NULL,
    updated_at        DATETIME               NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id),
    FOREIGN KEY (local_currency_id) REFERENCES local_currency (local_currency_id)
);

# 거래 내역
CREATE TABLE transaction
(
    transaction_id BINARY(16) PRIMARY KEY,
    from_wallet_id BINARY(16),
    to_wallet_id   BINARY(16),
    before_balance INT                                             NOT NULL,
    amount         INT                                             NOT NULL,
    after_balance  INT                                             NOT NULL,
    direction      ENUM ('INCOME', 'EXPENSE')                      NOT NULL,
    type           ENUM ('CHARGE', 'REFUND', 'CONVERT', 'PAYMENT') NOT NULL,
    comment        VARCHAR(255),
    created_at     DATETIME                                        NOT NULL,
    updated_at     DATETIME                                        NOT NULL,
    FOREIGN KEY (from_wallet_id) REFERENCES wallet (wallet_id),
    FOREIGN KEY (to_wallet_id) REFERENCES wallet (wallet_id)
);

# 사용 가능한 가맹점
CREATE TABLE available_merchant
(
    available_merchant_id BINARY(16) PRIMARY KEY,
    name                  VARCHAR(80)    NOT NULL,
    address               VARCHAR(255)   NOT NULL,
    latitude              DECIMAL(10, 6) NOT NULL,
    longitude             DECIMAL(10, 6) NOT NULL,
    category              VARCHAR(50)    NOT NULL,
    local_currency_id     BINARY(16)     NOT NULL,
    created_at            DATETIME       NOT NULL,
    updated_at            DATETIME       NOT NULL,
    FOREIGN KEY (local_currency_id) REFERENCES local_currency (local_currency_id)
);

# 뱃지
CREATE TABLE badge
(
    badge_id          BINARY(16) PRIMARY KEY,
    name              VARCHAR(25)                      NOT NULL,
    badge_type        ENUM ('BRONZE', 'SILVER', 'GOLD') NOT NULL,
    local_currency_id BINARY(16)                       NOT NULL,
    created_at        DATETIME                         NOT NULL,
    updated_at        DATETIME                         NOT NULL,
    FOREIGN KEY (local_currency_id) REFERENCES local_currency (local_currency_id)
);

# 파일
CREATE TABLE file
(
    file_id       BINARY(16) PRIMARY KEY,
    original_name VARCHAR(255) NOT NULL,
    stored_name   VARCHAR(255) NOT NULL,
    file_path     VARCHAR(255) NOT NULL,
    content_type  VARCHAR(100) NOT NULL,
    file_size     BIGINT       NOT NULL,
    created_at    DATETIME     NOT NULL,
    updated_at    DATETIME     NOT NULL
);

# 파일 참조 테이블
CREATE TABLE file_reference
(
    file_reference_id BINARY(16) PRIMARY KEY,
    file_id           BINARY(16)  NOT NULL,
    reference_type    VARCHAR(50) NOT NULL,
    reference_id      BINARY(16)  NOT NULL,
    usage_type        VARCHAR(50) NOT NULL,
    created_at        DATETIME    NOT NULL,
    updated_at        DATETIME    NOT NULL,
    FOREIGN KEY (file_id) REFERENCES file (file_id)
);
