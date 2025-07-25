package org.danji.wallet.dto;

import lombok.Builder;
import lombok.Data;
import org.danji.wallet.enums.WalletType;

import java.util.UUID;

@Data
@Builder
public class WalletFilterDTO {
    private UUID memberId;
    private WalletType walletType;

}
