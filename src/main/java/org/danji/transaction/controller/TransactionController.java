package org.danji.transaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.global.common.ApiResponse;
import org.danji.security.account.domain.CustomUser;
import org.danji.transaction.dto.request.PaymentDTO;
import org.danji.transaction.dto.request.TransactionFilterDTO;
import org.danji.transaction.dto.request.TransferDTO;
import org.danji.transaction.dto.response.TransactionAggregateDTO;
import org.danji.transaction.dto.response.TransactionDTO;
import org.danji.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

    @ApiOperation(
            value = "충전, 환불, 환전 API",
            notes = "보내는 지갑(fromWalletId)에서 받는 지갑(toWalletId)으로 지정한 금액을 이체합니다.\n이체 타입(Type)은 CHARGE, REFUND, CONVERT, PAYMENT 등이 가능합니다."
    )
    @PostMapping("/wallets/transfer")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> LocalCurrencyRecharge(@RequestBody TransferDTO transferDTO) {
        //System.out.println(user.getMember().getMemberId());
        List<TransactionDTO> result = transactionService.handleTransfer(transferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }
    @ApiOperation(
            value = "결제 API",
            notes = "결제 타입(LOCAL_CURRENCY, GENERAL) 에 따라 결제를 진행합니다."
    )
    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> LocalCurrencyPayment(@RequestBody PaymentDTO paymentDTO) {
        Long startTime = System.nanoTime();
        List<TransactionDTO> result = transactionService.handlePayment(paymentDTO);
        Long endTime = System.nanoTime();
        System.out.println("checkTime: " + (endTime - startTime));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }

    @ApiOperation(
            value = "결제내역 반환 API",
            notes = "지정한 기간(startDate ~ lastDate) 동안의 결제 내역을\n" +
                    "Direction(INCOME/EXPENSE)과 SortOrder(ASC/DESC) 조건으로 필터링하여 반환합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "walletId", value = "지갑 ID", required = true,
                    dataType = "string", paramType = "path", example = "e49a1610-6ac9-11f0-bb7d-00919e38b88b"),
            @ApiImplicitParam(name = "startDate", value = "시작 날짜(yyyy-MM-dd)", required = true,
                    dataType = "string", paramType = "query", example = "2025-07-15"),
            @ApiImplicitParam(name = "lastDate", value = "종료 날짜(yyyy-MM-dd)", required = true,
                    dataType = "string", paramType = "query", example = "2025-07-30"),
            @ApiImplicitParam(name = "direction", value = "거래 방향 (INCOME: 입금, EXPENSE: 출금). 전체 조회 시 생략 또는 null",
                    required = false, dataType = "string", paramType = "query",
                    allowableValues = "INCOME,EXPENSE", example = "INCOME"),
            @ApiImplicitParam(name = "sortOrder", value = "정렬 순서 (DESC: 최신순, ASC: 오래된순)",
                    required = true, dataType = "string", paramType = "query",
                    allowableValues = "ASC,DESC", example = "DESC")
    })
    @GetMapping("/wallets/{walletId}/transactions")
    public ResponseEntity<ApiResponse<TransactionAggregateDTO>> getTransactions(@PathVariable UUID walletId, @ApiIgnore @ModelAttribute TransactionFilterDTO transactionFilterDTO) {
        transactionFilterDTO.setWalletId(walletId);
        TransactionAggregateDTO result = transactionService.getTransactionAggregate(transactionFilterDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(result));
    }

    //서버 배포 시, 헬스 체크를 위한 api
    @GetMapping("/health")
    public String home() {
        return "ok";
    }
}
