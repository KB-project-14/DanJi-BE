package org.danji.transaction.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.danji.transaction.enums.Type;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ApiModel(description = "충전, 환불, 환전 요청 DTO")
public class TransferDTO {

    @NotNull(message = "fromWalletId 값은 필수입니다.")
    @ApiModelProperty(value = "보내는 지갑 ID", required = true, example = "e49a1610-6ac9-11f0-bb7d-00919e38b88b")
    private UUID fromWalletId;

    @NotNull(message = "toWalletId 값은 필수입니다.")
    @ApiModelProperty(value = "받는 지갑 ID", required = true, example = "a321a610-6ac9-11f0-bb7d-00919e38c88c")
    private UUID toWalletId;

    @NotNull(message = "Type 값은 필수입니다.")
    @ApiModelProperty(value = "이체 타입 (CHARGE(충전), REFUND(환불), CONVERT(환전), PAYMENT(결제))", required = true, example = "CHARGE")
    private Type type;

    @NotNull(message = "amount 값은 필수입니다.")
    @ApiModelProperty(value = "이체 금액", required = true, example = "10000")
    private Integer amount;

    @ApiModelProperty(value = "추후 기능 확장을 위해 사용하는 필드, 일단은 true로 요청해주세요.", example = "true")
    private boolean transactionLogging;

}
