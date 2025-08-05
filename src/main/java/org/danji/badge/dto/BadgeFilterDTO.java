package org.danji.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.badge.enums.BadgeType;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadgeFilterDTO {
    private UUID regionId;
    private BadgeType badgeType;
}
