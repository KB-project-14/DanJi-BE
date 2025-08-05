ALTER TABLE local_currency
    MODIFY benefit_type ENUM('CASHBACK', 'INCENTIVE', 'DISCOUNT_CHARGE', 'DISCOUNT');

UPDATE local_currency
    set benefit_type = 'INCENTIVE' WHERE local_currency_id = UNHEX('3EE5B175-7197-11F0-9C63-00919E38B88B');

UPDATE local_currency
set benefit_type = 'INCENTIVE' WHERE local_currency_id = UNHEX('3EE5B6BC-7197-11F0-9C63-00919E38B88B');

UPDATE local_currency
set benefit_type = 'INCENTIVE' WHERE local_currency_id = UNHEX('3EE5B23A-7197-11F0-9C63-00919E38B88B');

