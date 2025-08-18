package org.danji.availableMerchant.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.global.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AvailableMerchantDTO extends BaseDTO {
    private UUID availableMerchantId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
    private UUID localCurrencyId;
    private String localCurrencyName;
    private BigDecimal distance;
    
    public static AvailableMerchantDTO of(AvailableMerchantVO vo) {
        return vo == null ? null : AvailableMerchantDTO.builder()
                .availableMerchantId(vo.getAvailableMerchantId())
                .name(vo.getName())
                .address(vo.getAddress())
                .latitude(vo.getLatitude())
                .longitude(vo.getLongitude())
                .category(vo.getCategory())
                .localCurrencyId(vo.getLocalCurrencyId())
                //VO의 localCurrencyName 값을 DTO 필드에 매핑
                .localCurrencyName(vo.getLocalCurrencyName())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    public AvailableMerchantVO toVo() {
        return AvailableMerchantVO.builder()
                .availableMerchantId(availableMerchantId)
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .category(category)
                .localCurrencyId(localCurrencyId)
                .build();
    }
}


