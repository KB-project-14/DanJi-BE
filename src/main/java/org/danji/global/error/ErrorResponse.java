package org.danji.global.error;

import lombok.*;
import org.springframework.validation.FieldError;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String code;
    private String message;
    private String method;
    private List<FieldErrorResponse> errors;

    @Builder
    public ErrorResponse(ErrorCode errorCode, HttpServletRequest request, List<FieldErrorResponse> errors) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.method = request.getMethod();
        this.errors = errors;
    }

    @Builder
    public ErrorResponse(ErrorCode errorCode, HttpServletRequest request) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.method = request.getMethod();
    }

    @Builder
    public ErrorResponse(String errorCode, String errorMessage, HttpServletRequest request) {
        this.code = errorCode;
        this.message = errorMessage;
        this.method = request.getMethod();
    }

    public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request, List<FieldErrorResponse> errors) {
        return new ErrorResponse(errorCode, request, errors);
    }

    public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request) {
        return new ErrorResponse(errorCode, request);
    }

    public static ErrorResponse of(String errorCode, String errorMessage, HttpServletRequest request) {
        return new ErrorResponse(errorCode, errorMessage, request);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FieldErrorResponse {
        private final String field;
        private final String reason;

        public static FieldErrorResponse of(FieldError fieldError) {
            return new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
