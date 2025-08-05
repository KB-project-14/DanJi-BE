package org.danji.badge.exception;

import org.danji.global.error.ErrorCode;
import org.danji.global.exception.BusinessException;

public class BadgeException extends BusinessException {
    public BadgeException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}