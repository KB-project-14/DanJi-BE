package org.danji.region.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.global.dto.BaseDTO;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RegionVO extends BaseVO {
    private Long regionId;
    private String province;
    private String city;
}
