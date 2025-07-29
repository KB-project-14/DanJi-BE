package org.danji.transaction.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.global.common.ApiResponse;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.request.TransactionFilterDTO;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController                    // REST API 컨트롤러 선언 (@Controller + @ResponseBody)
@RequestMapping("/api")   // 기본 URL 매핑
@RequiredArgsConstructor           // final 필드 생성자 자동 생성
@Slf4j                             // 로깅 기능
@Api(
        tags = "트랜잭션 관리",                    // 그룹 이름 (필수)
        description = "트랜잭션 CRUD API",        // 상세 설명
        value = "TransactionController"         // 컨트롤러 식별자
)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/wallets/transfer")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> LocalCurrencyRecharge(@RequestBody TransferDTO transferDTO) {
        List<TransactionDTO> result = transactionService.handleTransfer(transferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }

    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> LocalCurrencyPayment(@RequestBody PaymentDTO paymentDTO) {
        List<TransactionDTO> result = transactionService.handlePayment(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }

    @GetMapping("/wallets/{walletId}/transactions")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactions(@PathVariable UUID walletId, @ModelAttribute TransactionFilterDTO transactionFilterDTO) {
        List<TransactionDTO> result = transactionService.getTransactionsByWalletId(walletId, transactionFilterDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(result));
    }
}
