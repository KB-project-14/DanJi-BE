package org.danji.wallet.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletOrderUpdateDTO {
    private UUID walletId;
    private int displayOrder;
}
