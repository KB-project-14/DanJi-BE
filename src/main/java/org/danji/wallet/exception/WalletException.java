package org.danji.wallet.exception;

import org.danji.global.error.ErrorCode;
import org.danji.global.exception.BusinessException;

public class WalletException extends BusinessException {
    public WalletException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
    }

}
