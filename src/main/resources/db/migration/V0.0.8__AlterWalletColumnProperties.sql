ALTER TABLE local_currency
    MODIFY benefit_type ENUM('CASHBACK', 'BONUS_CHARGE', 'DISCOUNT_CHARGE');

ALTER TABLE wallet
    MODIFY COLUMN local_currency_id BINARY(16) NULL;
