package org.danji.region.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionVO {
    private Long regionId;
    private String province;
    private String city;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
