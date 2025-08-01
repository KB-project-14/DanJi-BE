package org.danji.badge.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.badge.dto.BadgeDTO;
import org.danji.badge.service.BadgeService;
import org.danji.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse<List<BadgeDTO>>> getBadgeList() {
        List<BadgeDTO> badgeList = badgeService.getBadgeList();
        return ResponseEntity.ok(ApiResponse.success(badgeList));
    }

    @GetMapping("/{badgeId}")
    public ResponseEntity<ApiResponse<BadgeDTO>> get(@PathVariable UUID badgeId) {
        return ResponseEntity.ok(ApiResponse.success(badgeService.get(badgeId)));
    }

}