package org.danji.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    NOT_VALID(HttpStatus.BAD_REQUEST, "GLB-01", "유효성 검사에 실패했습니다"),
    REQUEST_BODY_NOT_VALID(HttpStatus.BAD_REQUEST, "GLB-02", "요청 본문이 유효하지 않습니다"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "GLB-03", "요청한 리소스를 찾을 수 없습니다"),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "GLB-04", "요청 파라미터가 누락되었습니다"),

    //wallet
    WALLET_NOT_FOUND(HttpStatus.NOT_FOUND, "WL-01", "지갑을 찾을 수 없습니다"),
    WALLET_BALANCE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "WL-02", "지갑의 잔액이 부족합니다"),

    //localCurrency
    LOCAL_CURRENCY_NOT_FOUND(HttpStatus.NOT_FOUND, "LC-01", "지갑을 찾을 수 없습니다"),

    //transaction
    TRANSACTION_SAVE_FAILED_MAIN(HttpStatus.INTERNAL_SERVER_ERROR, "TX-01", "메인 지갑 거래 내역 저장에 실패했습니다."),
    TRANSACTION_SAVE_FAILED_LOCAL(HttpStatus.INTERNAL_SERVER_ERROR, "TX-01", "지역화폐 지갑 거래 내역 저장에 실패했습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
