package org.danji.transaction.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.danji.transaction.enums.PaymentType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ApiModel(description = "결제 요청 DTO")
public class PaymentDTO {

    @NotNull(message = "LocalWalletId 값은 필수입니다.")
    @ApiModelProperty(value = "지역화폐 지갑 ID", required = true, example = "e49a1610-6ac9-11f0-bb7d-00919e38b88b")
    private UUID localWalletId;

    @NotNull(message = "availableMerchantId 값은 필수입니다.")
    @ApiModelProperty(value = "가맹점 ID", required = true, example = "e49a1610-6ac9-11f0-bb7d-00919e38b88b")
    private UUID availableMerchantId;

    @NotNull(message = "결제 수단 타입은 필수입니다.")
    @ApiModelProperty(value = "결제 타입 (LOCAL_CURRENCY(지역화폐), GENERAL(현금지갑))", required = true, example = "LOCAL_CURRENCY")
    private PaymentType type;

    @NotNull(message = "결제 금액은 필수입니다.")
    @ApiModelProperty(value = "가맹점 요청 금액", required = true, example = "10000")
    private Integer merchantAmount;

    @ApiModelProperty(value = "사용자 입력 금액, 일반결제로 요청 시 null 값이 됨", required = false, example = "10000")
    private Integer inputAmount;

    @NotNull(message = "결제 비밀번호는 필수 입니다.")
    @ApiModelProperty(value = "결제 비밀번호", required = true, example = "1234")
    private String walletPin;
}
