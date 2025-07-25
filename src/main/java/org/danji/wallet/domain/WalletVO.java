package org.danji.wallet.domain;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.wallet.enums.WalletType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class WalletVO extends BaseVO {
    private UUID walletId;
    private UUID memberId;
    private UUID localCurrencyId;
    private WalletType walletType;
    private Integer balance;
    private int displayOrder;
}
