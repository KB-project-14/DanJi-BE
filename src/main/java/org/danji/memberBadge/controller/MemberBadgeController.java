package org.danji.memberBadge.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.danji.global.common.ApiResponse;
import org.danji.memberBadge.dto.MemberBadgeDetailDTO;
import org.danji.memberBadge.dto.MemberBadgeFilterDTO;
import org.danji.memberBadge.service.MemberBadgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/MemberBadges")
@RequiredArgsConstructor
@Api(
        tags = "맴버-뱃지",                    // 그룹 이름 (필수)
        description = "맴버-뱃지 조회 API",        // 상세 설명
        value = "MemberBadgeController"              // 컨트롤러 식별자
)
public class MemberBadgeController {

    private final MemberBadgeService memberBadgeService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<MemberBadgeDetailDTO>>> getMemberBadgeList(@ModelAttribute MemberBadgeFilterDTO filter) {
        List<MemberBadgeDetailDTO> memberBadgeList = memberBadgeService.getMemberBadgeList(filter);
        return ResponseEntity.ok(ApiResponse.success(memberBadgeList));
    }

    @GetMapping("/{memberBadgeId}")
    public ResponseEntity<ApiResponse<MemberBadgeDetailDTO>> getMemberBadge(@PathVariable UUID memberBadgeId) {
        MemberBadgeDetailDTO memberBadge = memberBadgeService.getMemberBadge(memberBadgeId);
        return ResponseEntity.ok(ApiResponse.success(memberBadge));
    }
}
