package org.danji.transaction.exception;

import org.danji.global.error.ErrorCode;
import org.danji.global.exception.BusinessException;

public class TransactionException extends BusinessException {
    public TransactionException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}
