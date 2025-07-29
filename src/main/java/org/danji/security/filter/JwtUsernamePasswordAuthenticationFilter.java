package org.danji.security.filter;


import lombok.extern.slf4j.Slf4j;
import org.danji.member.dto.LoginDTO;
import org.danji.security.handler.LoginFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // 필터 생성자
    public JwtUsernamePasswordAuthenticationFilter(
        AuthenticationManager authenticationManager,    // SecurityConfig 생성 후 Bean 등록
      LoginFailureHandler loginFailureHandler) {

        // 부모 필터가 내부적으로 인증 처리를 할 수 있도록 설정해놓은 AuthenticationManager를 넘겨줌
        super(authenticationManager);
        setFilterProcessesUrl("/api/auth/login");                    // POST 로그인 요청 URL
       setAuthenticationFailureHandler(loginFailureHandler);        // 실패 핸들러 등록
    }


    // 로그인 요청 URL이 왔을 때 로그인 작업 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        // 1. 요청 BODY의 JSON에서 username, password 추출
        LoginDTO login = LoginDTO.of(request);

        // 2. 인증 토큰(UsernamePasswordAuthenticationToken) 구성
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        // 3. AuthenticationManager에게 인증 요청
        return getAuthenticationManager().authenticate(authenticationToken);
    }

}
