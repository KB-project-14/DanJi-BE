package org.danji.memberBadge.service;

import org.danji.memberBadge.dto.MemberBadgeCreateDTO;
import org.danji.memberBadge.dto.MemberBadgeDTO;
import org.danji.memberBadge.dto.MemberBadgeDetailDTO;
import org.danji.memberBadge.dto.MemberBadgeFilterDTO;
import org.danji.memberBadge.enums.BadgeGrade;

import java.util.List;
import java.util.UUID;

public interface MemberBadgeService {

    MemberBadgeDTO createMemberBadge(MemberBadgeCreateDTO createDTO);

    MemberBadgeDetailDTO getMemberBadge(UUID memberBadgeId);

    List<MemberBadgeDetailDTO> getMemberBadgeList(MemberBadgeFilterDTO filter);

    Boolean validateMemberBadge(UUID memberId, UUID badgeId, BadgeGrade badgeGrade);
}
