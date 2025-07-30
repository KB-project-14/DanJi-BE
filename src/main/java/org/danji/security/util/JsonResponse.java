package org.danji.security.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/*
Spring Security 필터나 예외 핸들러에서 JSON 응답을 일관되게 보내기 위해 사용하는 유틸
 */
public class JsonResponse {

    // 에러 응답 처리
    public static void sendError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        Writer out = response.getWriter();
        out.write(message);
        out.flush();
    }
}