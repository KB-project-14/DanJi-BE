package org.danji.localCurrency.dto;

import lombok.Data;
import org.danji.localCurrency.enums.BenefitType;

import java.util.UUID;

@Data
public class LocalCurrencyDetailDTO {
    private UUID localCurrencyId;
    private Long regionId;
    private String name;
    private BenefitType benefitType;
    private Integer maximum;
    private Integer percentage;
    private String imageUrl;
}
