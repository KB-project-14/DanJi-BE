package org.danji.transaction.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.transaction.annotation.MultipleOfHundred;
import org.danji.transaction.enums.Type;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransferDTO {
    @NotNull(message = "fromWalletId 값은 필수입니다.")
    private UUID fromWalletId;

    @NotNull(message = "toWalletId 값은 필수입니다.")
    private UUID toWalletId;

    @NotNull(message = "Type 값은 필수입니다.")
    private Type type;

    @Min(value = 10000, message = "최소 충전 금액은 10,000원입니다.")
    @MultipleOfHundred
    private Integer amount;

    private boolean transactionLogging;

}
