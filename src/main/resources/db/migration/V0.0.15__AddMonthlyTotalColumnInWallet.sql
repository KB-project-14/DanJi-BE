ALTER TABLE wallet
    ADD COLUMN monthly_total INT NOT NULL DEFAULT 0
        AFTER display_order;