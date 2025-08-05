package org.danji.badge.mapper;

import org.danji.badge.domain.BadgeVO;
import org.danji.badge.dto.BadgeFilterDTO;

import java.util.List;
import java.util.UUID;

public interface BadgeMapper {
    BadgeVO findById(UUID badgeId);

    List<BadgeVO> findByFilter(BadgeFilterDTO dto);

}
