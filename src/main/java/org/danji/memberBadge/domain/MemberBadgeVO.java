package org.danji.memberBadge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.memberBadge.enums.BadgeGrade;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class MemberBadgeVO extends BaseVO {

    private UUID memberBadgeId;
    private UUID memberId;
    private UUID badgeId;
    private BadgeGrade badgeGrade;
}
