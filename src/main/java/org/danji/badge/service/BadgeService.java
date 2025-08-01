package org.danji.badge.service;

import org.danji.badge.dto.BadgeDTO;

import java.util.List;
import java.util.UUID;

public interface BadgeService {
    BadgeDTO get(UUID badgeId);

    List<BadgeDTO> getBadgeList();
}
