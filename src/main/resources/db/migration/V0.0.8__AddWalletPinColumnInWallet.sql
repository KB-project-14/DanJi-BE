ALTER TABLE wallet
    ADD COLUMN wallet_pin CHAR(4) NOT NULL
        CHECK (wallet_pin REGEXP '^[0-9]{4}$')
        AFTER display_order;
