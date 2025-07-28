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

    @Pattern(regexp = "^[0-9]{4}$", message = "결제 비밀번호는 4자리 숫자만 가능합니다.")
    private String walletPin;
}
