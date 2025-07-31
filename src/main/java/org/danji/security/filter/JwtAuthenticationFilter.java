package org.danji.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.security.service.CustomUserDetailsService;
import org.danji.security.util.JwtProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT 토큰을 Header에서 꺼내서 인증 처리를 하는 Filter
@Component
@Log4j2
@RequiredArgsConstructor
// OncePerRequestFilter : 한 요청당 한 번만 동작하는 Filter
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Authorization 헤더명
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // Bearer 토큰 접두사 (끝에 공백 포함)
    public static final String BEARER_PREFIX = "Bearer ";

    // 토큰 검증하기 위한 JWTProcessor
    private final JwtProcessor jwtProcessor;

    // 사용자 정보 가져오기 위한 userDetailsService
    private final CustomUserDetailsService userDetailsService;

    /**
     * JWT 토큰으로부터 Authentication 객체 생성
     */
    private Authentication getAuthentication(String token) {
        // 토큰에서 사용자명 추출
        String username = jwtProcessor.getUsername(token);
        // 사용자 정보 로드
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        // Authentication 객체 생성 및 반환
        return new UsernamePasswordAuthenticationToken(
            principal, null, principal.getAuthorities()
        );
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        // Authorization 헤더에서 토큰 추출
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            // Bearer 접두사 제거하여 순수 토큰 추출
            String token = bearerToken.substring(BEARER_PREFIX.length());

            // 토큰에서 사용자 정보 추출 및 Authentication 객체 구성
            Authentication authentication = getAuthentication(token);
            // SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 요청 전달
        super.doFilter(request, response, filterChain);
    }
}