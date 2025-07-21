package org.danji.wallet.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.wallet.enums.WalletType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WalletVO {
    private UUID walletId;
    private UUID memberId;
    private UUID localCurrencyId;
    private WalletType walletType;
    private Integer balance;
}
