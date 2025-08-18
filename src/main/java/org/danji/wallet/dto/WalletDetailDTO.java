package org.danji.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.localCurrency.enums.BenefitType;
import org.danji.wallet.enums.WalletType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class WalletDetailDTO extends BaseDTO {
    private UUID walletId;
    private UUID memberId;
    private UUID localCurrencyId;
    private WalletType walletType;
    private Integer balance;
    private int displayOrder;

    private String localCurrencyName;
    private BenefitType benefitType;
    private Integer maximum;
    private Integer percentage;

    private Long regionId;
    private String province;
    private String city;

    private String backgroundImageUrl;

}
