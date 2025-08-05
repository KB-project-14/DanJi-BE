package org.danji.localCurrency.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.global.common.ApiResponse;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.dto.LocalCurrencyDetailDTO;
import org.danji.localCurrency.dto.LocalCurrencyFilterDTO;
import org.danji.localCurrency.service.LocalCurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/local-currencies")
@RequiredArgsConstructor
@Slf4j
@Api(
        tags = "지역화폐",                    // 그룹 이름 (필수)
        description = "지역화폐 CRUD API",        // 상세 설명
        value = "LocalCurrencyController"              // 컨트롤러 식별자
)
public class LocalCurrencyController {

    private final LocalCurrencyService localCurrencyService;

    @GetMapping("/{localCurrencyId}")
//    public ResponseEntity<ApiResponse<LocalCurrencyDTO>> getLocalCurrency(@PathVariable("localCurrencyId") UUID localCurrencyId) {
//        LocalCurrencyDTO localCurrency = localCurrencyService.getLocalCurrency(localCurrencyId);
//        return ResponseEntity.ok(ApiResponse.success(localCurrency));
//    }
    public ResponseEntity<ApiResponse<LocalCurrencyDetailDTO>> getLocalCurrencyDetail(@PathVariable("localCurrencyId") UUID localCurrencyId) {
        LocalCurrencyDetailDTO dto = localCurrencyService.getLocalCurrencyDetail(localCurrencyId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<LocalCurrencyDTO>>> getLocalCurrencyList(@ModelAttribute LocalCurrencyFilterDTO filter) {
        List<LocalCurrencyDTO> localCurrencyList = localCurrencyService.getLocalCurrencyList(filter);
        return ResponseEntity.ok(ApiResponse.success(localCurrencyList));
    }

}
