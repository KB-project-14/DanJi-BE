package org.danji.transaction.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.auth.account.domain.MemberVO;
import org.danji.global.common.ApiResponse;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.enums.TransferType;
import org.danji.transaction.service.TransactionService;
import org.danji.transaction.service.TransactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> LocalCurrencyRecharge(@RequestBody TransferDTO transferDTO) {
        List<TransactionDTO> result = transactionService.handleTransfer(transferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }
}
