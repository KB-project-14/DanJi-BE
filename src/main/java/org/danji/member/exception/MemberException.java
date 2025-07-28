package org.danji.member.exception;

import org.danji.global.error.ErrorCode;
import org.danji.global.exception.BusinessException;

public class MemberException extends BusinessException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}
