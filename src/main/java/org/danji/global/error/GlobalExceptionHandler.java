package org.danji.global.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.global.common.ApiResponse;
import org.danji.global.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.danji.global.error.ErrorResponse.FieldErrorResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        List<FieldErrorResponse> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldErrorResponse::of).toList();
        List<String> errors = fieldErrors.stream().map(FieldErrorResponse::getField).toList();

        log.error("[MethodArgumentNotValidException] : {}", errors);
        log.error("[MethodArgumentNotValidException] 발생 지점 : {} | {} ", httpRequest.getMethod(),
                httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_VALID, httpRequest, fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(errorResponse));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        log.error("[HttpMessageNotReadableException] : {}", ex.getMessage());
        log.error("[HttpMessageNotReadableException] 발생 지점 : {} | {} ", httpRequest.getMethod(),
                httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.REQUEST_BODY_NOT_VALID, httpRequest);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(errorResponse));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        log.error("[NoResourceFoundException] : {}", ex.getMessage());
        log.error("[NoResourceFoundException] 발생 지점 : {} | {} ", httpRequest.getMethod(), httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND, httpRequest);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(errorResponse));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        log.error("[MissingServletRequestParameterException] : {}", ex.getMessage());
        log.error("[MissingServletRequestParameterException] 발생 지점 : {} | {} ", httpRequest.getMethod(),
                httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER, httpRequest);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(errorResponse));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex,
                                                             HttpServletRequest httpRequest) {

        log.error("[BusinessException] : {}", ex.getErrorMessage());
        log.error("[BusinessException 발생 에러코드] : {}", ex.getErrorCode());
        log.error("[BusinessException] 발생 지점 : {} | {} ", httpRequest.getMethod(),
                httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ex.getErrorCode(), ex.getErrorMessage(), httpRequest);

        return ResponseEntity.status(ex.getStatus()).body(ApiResponse.failure(errorResponse));
    }


}
