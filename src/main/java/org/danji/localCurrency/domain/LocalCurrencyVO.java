package org.danji.localCurrency.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.localCurrency.enums.BenefitType;

import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.localCurrency.enums.BenefitType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class LocalCurrencyVO extends BaseVO {
    private UUID localCurrencyId;
    private Long regionId;
    private String name;
    private BenefitType benefitType;
    private Integer maximum;
    private Integer percentage;
}
