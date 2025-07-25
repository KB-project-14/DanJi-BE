package org.danji.availableMerchant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantFilterDTO {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
    private String localCurrencyName;
}
