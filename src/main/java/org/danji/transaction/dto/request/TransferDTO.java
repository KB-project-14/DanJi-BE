package org.danji.transaction.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.transaction.annotation.MultipleOfHundred;

import javax.validation.constraints.Min;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class TransferDTO extends BaseDTO {
    private UUID fromWalletId;
    private UUID toWalletId;

    @Min(value = 10000, message = "최소 충전 금액은 10,000원입니다.")
    @MultipleOfHundred
    private Integer amount;

}
