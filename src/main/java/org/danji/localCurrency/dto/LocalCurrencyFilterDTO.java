package org.danji.localCurrency.dto;

import lombok.Builder;
import lombok.Data;
import org.danji.localCurrency.enums.BenefitType;

import java.util.UUID;

@Data
@Builder
public class LocalCurrencyFilterDTO {
    private UUID localCurrencyId;
    private Long regionId;
    private BenefitType benefitType;
    private String province;
    private String city;

}
