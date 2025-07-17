package org.danji.localCurrency.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.localCurrency.enums.benefitType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LocalCurrencyVO {
    private Long localCurrencyId;
    private Long regionId;
    private String name;
    private benefitType benefitType;
    private Integer Maximum;
    private Integer percentage;
}
