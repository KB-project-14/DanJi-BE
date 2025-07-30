ALTER TABLE wallet
    CHANGE COLUMN monthly_total total_payment INT NOT NULL DEFAULT 0;