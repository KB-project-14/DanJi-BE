package org.danji.memberBadge.dto;

import lombok.*;
import org.danji.memberBadge.domain.MemberBadgeVO;
import org.danji.memberBadge.enums.BadgeGrade;


import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberBadgeDTO{

    private UUID memberBadgeId;
    private UUID memberId;
    private UUID badgeId;
    private BadgeGrade badgeGrade;

    public static MemberBadgeDTO of(MemberBadgeVO vo) {
        return vo == null ? null : MemberBadgeDTO.builder()
                .memberBadgeId(vo.getMemberBadgeId())
                .memberId(vo.getMemberId())
                .badgeId(vo.getBadgeId())
                .badgeGrade(vo.getBadgeGrade())
                .build();

    }
}
