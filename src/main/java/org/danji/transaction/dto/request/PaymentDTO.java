package org.danji.transaction.dto.request;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.transaction.enums.PaymentType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaymentDTO {

    private UUID LocalWalletId;
    private UUID availableMerchantId;
    private PaymentType type;
    private Integer amount;
}
