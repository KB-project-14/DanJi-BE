package org.danji.availableMerchant.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import org.danji.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.service.AvailableMerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/available-merchants")
@RequiredArgsConstructor
@Slf4j
@Api(
tags = "가맹점 관리", description = "가맹점 CRUD API"
)
public class AvailableMerchantController {
    private final AvailableMerchantService service;

    @ApiOperation(value = "가맹점 등록", notes = "새로운 가맹점을 등록하는 API")

    @PostMapping
    public ResponseEntity<ApiResponse<AvailableMerchantDTO>> create(
            @ApiParam(value = "등록할 가맹점 정보", required = true)
            @RequestBody AvailableMerchantDTO dto
    ) {
        log.info("가맹점 등록 요청: ", dto);

        //가맹점 생성 후 결과 반환
        AvailableMerchantDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created));
    }
}
