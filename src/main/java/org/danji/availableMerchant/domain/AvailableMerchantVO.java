package org.danji.availableMerchant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.global.dto.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableMerchantVO {

    private UUID availableMerchantId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
    private UUID localCurrencyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
