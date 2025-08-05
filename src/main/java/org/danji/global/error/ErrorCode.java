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
    DUPLICATED_LOCAL_CURRENCY_WALLET(HttpStatus.CONFLICT, "WL-03", "해당 지역화폐 지갑이 존재합니다"),
    UNAUTHORIZED_WALLET_ACCESS(HttpStatus.FORBIDDEN, "WL-04",
            "요청한 지갑은 해당 사용자의 소유가 아닙니다."),
    CAN_NOT_DELETE_WALLET_BALANCE_NOT_EMPTY(HttpStatus.BAD_REQUEST, "WL-05", "지갑 잔액이 0원이 아니므로 삭제할 수 없습니다."),
    STRATEGY_NOT_FOUND(HttpStatus.NOT_FOUND, "WL-06", "해당 결제 조건에 맞는 전략이 존재하지 않습니다."),
    INVALID_PAYMENT_PASSWORD(HttpStatus.BAD_REQUEST, "WL-07", "결제 비밀번호가 일치하지 않습니다"),
    //localCurrency
    LOCAL_CURRENCY_NOT_FOUND(HttpStatus.NOT_FOUND, "LC-01", "지갑을 찾을 수 없습니다"),
    LOCAL_WALLET_EXCEEDS_MONTHLY_MAX(HttpStatus.BAD_REQUEST, "LC-02", "지역화폐 월 최대 금액을 초과한 금액입니다."),
    //transaction
    TRANSACTION_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "TX-01", "거래 내역 저장에 실패했습니다."),

    // member
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "MB-01", "사용자를 찾을 수 없습니다."),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "MB-02", "이미 존재하는 사용자명입니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MB-03", "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "MB-04", "권한이 없습니다."),

    //availableMerchant
    AVAILABLE_MERCHANT_NOT_FOUND(HttpStatus.NOT_FOUND, "AM-01", "가맹점을 찾을 수 없습니다."),
    INPUT_AMOUNT_EXCEEDS_MERCHANT_AMOUNT(HttpStatus.BAD_REQUEST, "AM-02", "입력한 금액이 가맹점 요청 금액을 초과했습니다."),

    //badge
    BADGE_NOT_FOUND(HttpStatus.NOT_FOUND, "BD-01", "뱃지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
