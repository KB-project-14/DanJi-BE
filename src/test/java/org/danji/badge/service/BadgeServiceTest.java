package org.danji.badge.service;

import lombok.extern.log4j.Log4j2;
import org.danji.badge.dto.BadgeDTO;

import org.danji.global.config.RootConfig;
import org.danji.security.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j2
@Transactional
class BadgeServiceTest {

    @Autowired
    private BadgeService badgeService;

    @Test
    void get() {
        // given - 실제 존재하는 뱃지 ID를 사용하거나, 테스트 데이터를 미리 준비

        List<BadgeDTO> allBadges = badgeService.getBadgeList();
        assertFalse(allBadges.isEmpty(), "테스트를 위한 뱃지 데이터가 필요합니다");
        UUID existingBadgeId = allBadges.get(0).getBadgeId();

        // when
        BadgeDTO result = badgeService.get(existingBadgeId);

        // then
        log.info("조회된 뱃지: {}", result);
        assertNotNull(result);
        assertEquals(existingBadgeId, result.getBadgeId());
        assertNotNull(result.getName());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void getBadgeList() {
        // when
        List<BadgeDTO> badgeList = badgeService.getBadgeList();

        // then
        log.info("뱃지 목록 조회 결과: {} 개", badgeList.size());
        assertNotNull(badgeList);

        // 뱃지가 있다면 각 뱃지의 필수 필드 검증
        for (BadgeDTO badge : badgeList) {
            log.info("뱃지 정보: {}", badge);
            assertNotNull(badge.getBadgeId());
            assertNotNull(badge.getName());
            assertNotNull(badge.getCreatedAt());
        }
    }
}