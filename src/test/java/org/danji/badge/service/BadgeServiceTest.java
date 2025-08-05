package org.danji.badge.service;

import lombok.extern.log4j.Log4j2;
import org.danji.badge.dto.BadgeDTO;

import org.danji.badge.dto.BadgeFilterDTO;
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
        // given
        BadgeFilterDTO emptyFilter = BadgeFilterDTO.builder().build();
        List<BadgeDTO> allBadges = badgeService.getBadgeListByFilter(emptyFilter);

        assertFalse(allBadges.isEmpty(), "테스트를 위한 뱃지 데이터가 필요합니다");
        UUID existingBadgeId = allBadges.get(0).getBadgeId();

        // when
        BadgeDTO result = badgeService.get(existingBadgeId);

        // then
        log.info("조회된 뱃지: {}", result);
        assertNotNull(result);
        assertEquals(existingBadgeId, result.getBadgeId());
        assertNotNull(result.getName());
    }

    @Test
    void getBadgeListByFilter() {
        BadgeFilterDTO filter = BadgeFilterDTO.builder().build();

        // when
        List<BadgeDTO> badgeList = badgeService.getBadgeListByFilter(filter);

        // then
        log.info("뱃지 목록 조회 결과: {} 개", badgeList.size());
        assertNotNull(badgeList);

        // 뱃지가 있다면 각 뱃지의 필수 필드 검증
        for (BadgeDTO badge : badgeList) {
            log.info("뱃지 정보: {}", badge);
            assertNotNull(badge.getBadgeId());
            assertNotNull(badge.getName());
            assertNotNull(badge.getComment());
        }
    }

    @Test
    void getBadgeListByFilterWithCondition() {
        // given - 특정 조건 필터링
        BadgeFilterDTO filter = BadgeFilterDTO.builder()
                .badgeType(org.danji.badge.enums.BadgeType.NORMAL) // enum import 필요
                .build();

        // when
        List<BadgeDTO> badgeList = badgeService.getBadgeListByFilter(filter);

        // then
        log.info("필터된 뱃지 목록 조회 결과: {} 개", badgeList.size());
        assertNotNull(badgeList);

        // 필터 조건 검증
        for (BadgeDTO badge : badgeList) {
            log.info("필터된 뱃지 정보: {}", badge);
            assertEquals(org.danji.badge.enums.BadgeType.NORMAL, badge.getBadgeType());
        }
    }
}