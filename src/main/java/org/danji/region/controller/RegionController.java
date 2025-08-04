package org.danji.region.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.danji.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.region.dto.RegionDTO;
import org.danji.region.dto.RegionFilterDTO;
import org.danji.region.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "지역 관리", description = "지역 조회 API")
public class RegionController {
    private final RegionService service;

    @ApiOperation(value = "지역 목록 조회", notes = "모든 지역의 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RegionDTO>>> getRegionList(RegionFilterDTO filterDTO) {
        log.info("지역 목록 조회 요청");

        List<RegionDTO> regions = service.getRegionList(filterDTO);
        return ResponseEntity.ok(ApiResponse.success(regions));
    }
}
