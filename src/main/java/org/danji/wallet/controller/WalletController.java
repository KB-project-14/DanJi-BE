package org.danji.wallet.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.global.common.ApiResponse;
import org.danji.wallet.dto.WalletDTO;
import org.danji.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Slf4j
@Api(
        tags = "지갑",                    // 그룹 이름 (필수)
        description = "지갑 CRUD API",        // 상세 설명
        value = "WalletController"              // 컨트롤러 식별자
)
public class WalletController {

    private final WalletService walletService;

    @PostMapping("")
    ResponseEntity<ApiResponse<WalletDTO>> createWallet(@RequestBody WalletDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(walletService.createWallet(dto)));
    }

}
