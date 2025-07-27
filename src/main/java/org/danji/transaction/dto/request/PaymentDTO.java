package org.danji.transaction.dto.request;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.transaction.enums.PaymentType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaymentDTO {

    @NotNull(message = "LocalWalletId 값은 필수입니다.")
    private UUID LocalWalletId;

    @NotNull(message = "availableMerchantId 값은 필수입니다.")
    private UUID availableMerchantId;

    @NotNull(message = "결제 수단 타입은 필수입니다.")
    private PaymentType type;

    @NotNull(message = "결제 금액은 필수입니다.")
    private Integer amount;
}
