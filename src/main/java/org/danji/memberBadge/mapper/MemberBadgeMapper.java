package org.danji.memberBadge.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.memberBadge.domain.MemberBadgeVO;
import org.danji.memberBadge.dto.MemberBadgeDetailDTO;
import org.danji.memberBadge.dto.MemberBadgeFilterDTO;
import org.danji.memberBadge.enums.BadgeGrade;

import java.util.List;
import java.util.UUID;

public interface MemberBadgeMapper {

    MemberBadgeDetailDTO findById(UUID memberBadgeId);

    void insert(MemberBadgeVO memberBadge);

    MemberBadgeVO findByMemberIdAndBadgeIdAndBadgeGrade(@Param("memberId") UUID memberId, @Param("badgeId") UUID badgeId, @Param("badgeGrade")BadgeGrade badgeGrade);

    List<MemberBadgeDetailDTO> findByFilter(MemberBadgeFilterDTO filter);
}
