package org.danji.availableMerchant.exception;

import org.danji.global.error.ErrorCode;
import org.danji.global.exception.BusinessException;

public class AvailableMerchantException extends BusinessException {
    public AvailableMerchantException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
    }

}
