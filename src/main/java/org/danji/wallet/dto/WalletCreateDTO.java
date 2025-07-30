package org.danji.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.wallet.enums.WalletType;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletCreateDTO {
    private UUID localCurrencyId;
    private WalletType walletType;
}
