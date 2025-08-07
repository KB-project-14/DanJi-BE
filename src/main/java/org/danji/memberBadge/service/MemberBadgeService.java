package org.danji.memberBadge.service;

import org.danji.memberBadge.dto.MemberBadgeCreateDTO;
import org.danji.memberBadge.dto.MemberBadgeDetailDTO;
import org.danji.memberBadge.dto.MemberBadgeFilterDTO;

import java.util.List;
import java.util.UUID;

public interface MemberBadgeService {

    MemberBadgeDetailDTO createMemberBadge(MemberBadgeCreateDTO createDTO);

    MemberBadgeDetailDTO getMemberBadge(UUID memberBadgeId);

    List<MemberBadgeDetailDTO> getMemberBadgeList(MemberBadgeFilterDTO filter);
}
