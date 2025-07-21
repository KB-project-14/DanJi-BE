package org.danji.availableMerchant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableMerchantVO {

    private Long availableMerchantId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String category;
    private Long localCurrencyId;

}
