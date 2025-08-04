package org.danji.badge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.badge.domain.BadgeVO;
import org.danji.badge.dto.BadgeDTO;
import org.danji.badge.dto.BadgeFilterDTO;
import org.danji.global.error.ErrorCode;
import org.danji.badge.exception.BadgeException;
import org.danji.badge.mapper.BadgeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor

public class BadgeServiceImpl implements BadgeService {
    private final BadgeMapper mapper;

    @Override
    public BadgeDTO get(UUID badgeId){
        log.info("뱃지 조회 요청: badgeId = {}", badgeId);

        BadgeVO vo = mapper.findById(badgeId);
        if (vo == null) {
            throw new BadgeException(ErrorCode.BADGE_NOT_FOUND);
        }

        return BadgeDTO.of(vo);    }

    @Override
    public List<BadgeDTO> getBadgeListByFilter(BadgeFilterDTO filter) {
        if (filter == null) {
            filter = BadgeFilterDTO.builder().build();
        }

        log.info("뱃지 필터 조회 요청: filter = {}", filter);

        List<BadgeVO> badgeList = mapper.findByFilter(filter);

        return badgeList.stream()
                .map(BadgeDTO::of)
                .collect(Collectors.toList());
    }
}
