package org.danji.badge.dto;

import lombok.Builder;
import lombok.Data;
import org.danji.badge.enums.BadgeType;

import java.util.UUID;

@Data
@Builder
public class BadgeFilterDTO {
    private UUID badgeId;
    private String name;
    private BadgeType badgeType;
    private String comment;
}
