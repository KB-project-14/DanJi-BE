package org.danji.memberBadge.dto;

import lombok.Builder;
import lombok.Data;
import org.danji.memberBadge.enums.BadgeGrade;
import org.danji.memberBadge.domain.MemberBadgeVO;

import java.util.UUID;

@Data
@Builder
public class MemberBadgeCreateDTO {
    private UUID memberId;
    private UUID badgeId;
    private BadgeGrade badgeGrade;

    public MemberBadgeVO toVo() {
        return MemberBadgeVO.builder()
                .memberId(memberId)
                .badgeId(badgeId)
                .badgeGrade(badgeGrade)
                .build();
    }
}
