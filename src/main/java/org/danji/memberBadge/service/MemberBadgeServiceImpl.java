package org.danji.memberBadge.service;

import lombok.RequiredArgsConstructor;
import org.danji.global.error.ErrorCode;
import org.danji.memberBadge.domain.MemberBadgeVO;
import org.danji.memberBadge.dto.MemberBadgeCreateDTO;
import org.danji.memberBadge.dto.MemberBadgeDetailDTO;
import org.danji.memberBadge.dto.MemberBadgeFilterDTO;
import org.danji.memberBadge.enums.BadgeGrade;
import org.danji.memberBadge.exception.MemberBadgeException;
import org.danji.memberBadge.mapper.MemberBadgeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberBadgeServiceImpl implements MemberBadgeService {

    private final MemberBadgeMapper mapper;

    @Override
    @Transactional
    public MemberBadgeDetailDTO createMemberBadge(MemberBadgeCreateDTO createDTO) {
        //validateMemberBadge(createDTO.getMemberId(), createDTO.getBadgeId());
        MemberBadgeVO vo = createDTO.toVo();

        UUID memberBadgeId = UUID.randomUUID();
        vo.setMemberBadgeId(memberBadgeId);

        mapper.insert(vo);
        return getMemberBadge(memberBadgeId);
    }

    @Override
    public MemberBadgeDetailDTO getMemberBadge(UUID memberBadgeId) {
        MemberBadgeDetailDTO result = mapper.findById(memberBadgeId);
        if (result == null) {
            throw new MemberBadgeException(ErrorCode.MEMBER_BADGE_NOT_FOUND);
        }
        return result;
    }

    @Override
    public List<MemberBadgeDetailDTO> getMemberBadgeList(MemberBadgeFilterDTO filter) {
        return mapper.findByFilter(filter);
    }


    public Boolean validateMemberBadge(UUID memberId, UUID badgeId, BadgeGrade badgeGrade) {
        MemberBadgeVO vo = mapper.findByMemberIdAndBadgeIdAndBadgeGrade(memberId, badgeId, badgeGrade);
        if (vo != null) {
            return false;
        }
        return true;
    }
}
