package org.danji.localCurrency.dto;

import lombok.Builder;
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

    //이미지 파일
    private String imageUrl;
}
