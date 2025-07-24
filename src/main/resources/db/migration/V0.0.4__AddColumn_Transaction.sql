ALTER TABLE transaction
    ADD COLUMN owner_wallet_id BINARY(16) NOT NULL
        AFTER comment;

ALTER TABLE transaction
    ADD CONSTRAINT fk_transaction_owner_wallet
        FOREIGN KEY (owner_wallet_id)
            REFERENCES wallet(wallet_id);