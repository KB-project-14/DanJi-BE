package org.scoula.exception;

import java.util.NoSuchElementException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Order(2)
public class ApiExceptionAdvice {

    // NoSuchElementException.class가 발생한 경우
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<String> handleIllegalArgumentException(NoSuchElementException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .header("Content-Type", "text/plain;charset=UTF-8")
            .body("해당 ID의 요소가 없습니다.");
    }

    // 500 에러
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .header("Content-Type", "text/plain;charset=UTF-8")
            .body("==> " + e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class) //404
    protected ResponseEntity<String> handleException2(Exception e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .header("Content-Type", "text/plain;charset=UTF-8")
            .body("요청한 주소는 찾을 수 없음.");
    }
}