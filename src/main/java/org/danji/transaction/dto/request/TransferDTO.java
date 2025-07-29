package org.danji.transaction.dto.request;

import lombok.*;
import org.danji.transaction.enums.Type;

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

    @NotNull(message = "amount 값은 필수입니다.")
    private Integer amount;

    private boolean transactionLogging;

}
