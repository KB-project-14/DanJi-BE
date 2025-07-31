ALTER TABLE wallet
    DROP COLUMN wallet_pin;

ALTER TABLE member
    ADD COLUMN payment_pin CHAR(4) AFTER name;


UPDATE member
SET payment_pin = '0000'
WHERE payment_pin IS NULL;

ALTER TABLE member
    MODIFY COLUMN payment_pin CHAR(4) NOT NULL,
    ADD CONSTRAINT chk_member_payment_pin
        CHECK (payment_pin REGEXP '^[0-9]{4}$');