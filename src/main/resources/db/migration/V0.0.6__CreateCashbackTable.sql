CREATE TABLE cashback
(
    cashback_id       BINARY(16)  PRIMARY KEY,
    wallet_id         BINARY(16)  NOT NULL,
    amount            INT         NOT NULL,
    cashback_date	  DATETIME	  NOT NULL,
    `status`  		  ENUM('PENDING', 'COMPLETED')  NOT NULL,
    created_at        DATETIME    NOT NULL,
    updated_at        DATETIME    NOT NULL,
    FOREIGN KEY (wallet_id) REFERENCES wallet (wallet_id)
);