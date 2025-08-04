package org.danji.badge.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.badge.dto.BadgeDTO;
import org.danji.badge.dto.BadgeFilterDTO;
import org.danji.badge.service.BadgeService;
import org.danji.badge.enums.BadgeType;
import org.danji.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
@Slf4j
@Api(
        tags = "뱃지",                    // 그룹 이름 (필수)
        description = "뱃지 CRUD API",        // 상세 설명
        value = "BadgeController"              // 컨트롤러 식별자
)
public class BadgeController {
    private final BadgeService badgeService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<BadgeDTO>>> getBadgeList(
            @RequestParam(required = false) BadgeType badgeType
            ) {
        BadgeFilterDTO filter = BadgeFilterDTO.builder()
                .badgeType(badgeType)
                .build();

        List<BadgeDTO> badgeList = badgeService.getBadgeListByFilter(filter);
        return ResponseEntity.ok(ApiResponse.success(badgeList));
    }

    @GetMapping("/{badgeId}")
    public ResponseEntity<ApiResponse<BadgeDTO>> get(@PathVariable UUID badgeId) {
        return ResponseEntity.ok(ApiResponse.success(badgeService.get(badgeId)));
    }

}