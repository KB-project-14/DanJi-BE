package org.danji.region.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.region.domain.RegionVO;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RegionDTO extends BaseDTO {
    private Long regionId;
    private String province;
    private String city;

    //VO -> DTO 변환
    public static RegionDTO of(RegionVO vo) {
        return vo == null ? null : RegionDTO.builder()
                .regionId(vo.getRegionId())
                .province(vo.getProvince())
                .city(vo.getCity())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    //DTO -> VO 변환
    public RegionVO toVo() {
        return RegionVO.builder()
                .regionId(regionId)
                .province(province)
                .city(city)
                .build();
    }
}
