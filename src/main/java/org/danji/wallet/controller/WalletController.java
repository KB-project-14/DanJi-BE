package org.danji.wallet.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.global.common.ApiResponse;
import org.danji.wallet.dto.WalletDTO;
import org.danji.wallet.dto.WalletFilterDTO;
import org.danji.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ApiResponse<WalletDTO>> createWallet(@RequestBody WalletDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(walletService.createWallet(dto)));
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<ApiResponse<WalletDTO>> getWallet(@PathVariable UUID walletId) {
        return ResponseEntity.ok(ApiResponse.success(walletService.getWallet(walletId)));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<WalletDTO>>> getWalletList(@ModelAttribute WalletFilterDTO filter) {
        List<WalletDTO> walletList = walletService.getWalletList(filter);
        return ResponseEntity.ok(ApiResponse.success(walletList));
    }
}
