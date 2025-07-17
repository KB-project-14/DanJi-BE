package org.danji.localCurrency.exception;

import org.danji.global.error.ErrorCode;
import org.danji.global.exception.BusinessException;

public class LocalCurrencyException extends BusinessException {
    public LocalCurrencyException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}
