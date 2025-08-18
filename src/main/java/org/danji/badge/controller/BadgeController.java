package org.danji.badge.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.badge.dto.BadgeDTO;
import org.danji.badge.dto.BadgeFilterDTO;
import org.danji.badge.service.BadgeService;
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
        tags = "뱃지",
        description = "뱃지 CRUD API",
        value = "BadgeController"
)
public class BadgeController {
    private final BadgeService badgeService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<BadgeDTO>>> getBadgeList(
            @ModelAttribute BadgeFilterDTO filter
            ) {
        List<BadgeDTO> badgeList = badgeService.getBadgeListByFilter(filter);
        return ResponseEntity.ok(ApiResponse.success(badgeList));
    }

    @GetMapping("/{badgeId}")
    public ResponseEntity<ApiResponse<BadgeDTO>> get(@PathVariable UUID badgeId) {
        return ResponseEntity.ok(ApiResponse.success(badgeService.get(badgeId)));
    }

}