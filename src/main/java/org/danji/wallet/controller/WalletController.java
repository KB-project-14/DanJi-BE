package org.danji.wallet.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.common.utils.AuthUtils;
import org.danji.global.common.ApiResponse;
import org.danji.wallet.dto.*;
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
        tags = "지갑",
        description = "지갑 CRUD API",
        value = "WalletController"
)
public class WalletController {

    private final WalletService walletService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<WalletDTO>> createWallet(@RequestBody WalletCreateDTO dto) {
        UUID memberId = AuthUtils.getMemberId();
        dto.setMemberId(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(walletService.createWallet(dto)));
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<ApiResponse<WalletDetailDTO>> getWallet(@PathVariable UUID walletId) {
        return ResponseEntity.ok(ApiResponse.success(walletService.getWallet(walletId)));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<WalletDetailDTO>>> getWalletList(@ModelAttribute WalletFilterDTO filter) {
        List<WalletDetailDTO> walletList = walletService.getWalletWithCurrency(filter);
        return ResponseEntity.ok(ApiResponse.success(walletList));
    }

    @PatchMapping("/order")
    public ResponseEntity<ApiResponse<List<WalletDTO>>> updateWalletOrder(
            @RequestBody List<WalletOrderUpdateDTO> walletOrderList) {

        List<WalletDTO> walletList = walletService.updateWalletOrder(walletOrderList);
        return ResponseEntity.ok(ApiResponse.success(walletList));
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable UUID walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity.noContent().build();
    }
}
