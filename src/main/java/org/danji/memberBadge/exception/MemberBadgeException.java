package org.danji.memberBadge.exception;

import org.danji.global.error.ErrorCode;
import org.danji.global.exception.BusinessException;

public class MemberBadgeException extends BusinessException {
    public MemberBadgeException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}
