package org.danji.wallet.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.wallet.enums.WalletType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WalletVO {
    private Long walletId;
    private Long memberId;
    private Long localCurrencyId;
    private WalletType walletType;
    private Integer balance;
}
