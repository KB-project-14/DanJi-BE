package org.danji.localCurrency.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.enums.BenefitType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LocalCurrencyDTO extends BaseDTO {
    private UUID localCurrencyId;
    private Long regionId;
    private String name;
    private BenefitType benefitType;
    private Integer maximum;
    private Integer percentage;

    private String province;
    private String city;
    private String imageUrl;

    public static LocalCurrencyDTO of(LocalCurrencyVO vo) {
        return vo == null ? null : LocalCurrencyDTO.builder()
                .localCurrencyId(vo.getLocalCurrencyId())
                .regionId(vo.getRegionId())
                .name(vo.getName())
                .benefitType(vo.getBenefitType())
                .maximum(vo.getMaximum())
                .percentage(vo.getPercentage())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    public LocalCurrencyVO toVo() {
        return LocalCurrencyVO.builder()
                .localCurrencyId(localCurrencyId)
                .regionId(regionId)
                .name(name)
                .benefitType(benefitType)
                .maximum(maximum)
                .percentage(percentage)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
