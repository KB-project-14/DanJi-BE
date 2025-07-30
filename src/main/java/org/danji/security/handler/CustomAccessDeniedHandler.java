package org.danji.security.handler;

import lombok.extern.log4j.Log4j2;
import org.danji.security.util.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
// 권한 문제 있을 때 응답할 형태를 지정하는 핸들러
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        JsonResponse.sendError(
            response,
            HttpStatus.FORBIDDEN,
            "권한이 부족합니다."
        );
    }
}
