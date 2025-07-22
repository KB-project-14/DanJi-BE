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
    
    //VO -> DTO 변환
    public static AvailableMerchantDTO of(AvailableMerchantVO vo) {
        return vo == null ? null : AvailableMerchantDTO.builder()
                .name(vo.getName())
                .address(vo.getAddress())
                .latitude(vo.getLatitude())
                .longitude(vo.getLongitude())
                .category(vo.getCategory())
                .localCurrencyId(vo.getLocalCurrencyId())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    //DTO -> VO 변환
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


