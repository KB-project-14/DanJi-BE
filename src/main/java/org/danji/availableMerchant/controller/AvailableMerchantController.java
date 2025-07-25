package org.danji.availableMerchant.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;
import org.danji.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.availableMerchant.service.AvailableMerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/available-merchants")
@RequiredArgsConstructor
@Slf4j
@Api(
tags = "가맹점 관리", description = "가맹점 CRUD API"
)
public class AvailableMerchantController {
    private final AvailableMerchantService service;

    @ApiOperation(value = "가맹점 등록", notes = "공공데이터 API에서 가맹점을 가져와 저장합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> importMerchants(
    ) {
        //가맹점 생성 후 결과 반환
        service.importFromPublicAPI();
        return ResponseEntity.ok(ApiResponse.success("공공 API에서 가맹점 데이터 수집 완료"));
    }

    @ApiOperation(value = "가맹점 필터링 조회", notes = "다양한 조건으로 가맹점을 검색합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AvailableMerchantDTO>>> findMerchantsByFilter(
            @ModelAttribute MerchantFilterDTO filterDTO
            ) {
        List<AvailableMerchantDTO> result = service.findByFilter(filterDTO);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
