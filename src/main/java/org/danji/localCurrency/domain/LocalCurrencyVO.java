package org.danji.localCurrency.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.localCurrency.enums.BenefitType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LocalCurrencyVO {
    private UUID localCurrencyId;
    private UUID regionId;
    private String name;
    private BenefitType benefitType;
    private Integer Maximum;
    private Integer percentage;
}
