package org.danji.memberBadge.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.memberBadge.domain.MemberBadgeVO;
import org.danji.memberBadge.dto.MemberBadgeDetailDTO;
import org.danji.memberBadge.dto.MemberBadgeFilterDTO;

import java.util.List;
import java.util.UUID;

public interface MemberBadgeMapper {

    MemberBadgeDetailDTO findById(UUID memberBadgeId);

    void insert(MemberBadgeVO memberBadge);

    MemberBadgeVO findByMemberIdAndBadgeId(@Param("memberId") UUID memberId, @Param("badgeId") UUID badgeId);

    List<MemberBadgeDetailDTO> findByFilter(MemberBadgeFilterDTO filter);
}
