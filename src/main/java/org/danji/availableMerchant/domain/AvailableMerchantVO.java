package org.danji.availableMerchant.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.global.dto.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AvailableMerchantVO extends BaseVO {

    private UUID availableMerchantId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
    private UUID localCurrencyId;

    //JOIN을 통해 가져올 지역화폐 이름을 담을 필드
    private String localCurrencyName;
}
