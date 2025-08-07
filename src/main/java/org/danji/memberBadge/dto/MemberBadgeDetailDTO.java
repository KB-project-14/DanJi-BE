package org.danji.memberBadge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.danji.badge.enums.BadgeType;
import org.danji.global.dto.BaseDTO;
import org.danji.memberBadge.enums.BadgeGrade;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class MemberBadgeDetailDTO extends BaseDTO {

    private UUID memberBadgeId;
    private UUID memberId;
    private UUID badgeId;
    private BadgeGrade badgeGrade;

    private BadgeType badgeType;
    private String comment;
    private Long regionId;
    private String province;
    private String city;
    private String imageUrl;

}
