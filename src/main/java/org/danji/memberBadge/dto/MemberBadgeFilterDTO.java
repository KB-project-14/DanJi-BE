package org.danji.memberBadge.dto;

import lombok.Builder;
import lombok.Data;
import org.danji.badge.enums.BadgeType;
import org.danji.memberBadge.enums.BadgeGrade;

import java.util.UUID;

@Data
@Builder
public class MemberBadgeFilterDTO {
    private UUID memberId;
    private UUID badgeId;
    private BadgeGrade badgeGrade;
    private BadgeType badgeType;
    private Long regionId;
}
