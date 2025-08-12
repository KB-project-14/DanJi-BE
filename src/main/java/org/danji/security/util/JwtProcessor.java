package org.danji.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.danji.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

// JWT를 사용할때 필요한 메서드들을 만들어놓기위한 클래스
@Component
@Log4j2
public class JwtProcessor {

    static private final long TOKEN_VALID_MILLISECOND = 1000L * 60 * 60 * 1000;

    private final Key key;

    public JwtProcessor(@Value("${jwt.secret.key}") String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 만들기
    public String generateToken(MemberDTO member) {
        return Jwts.builder()
                .setSubject(member.getMemberId().toString())              // UUID → String
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALID_MILLISECOND))
                .claim("username", member.getUsername())
                .claim("role", member.getRole())
                .signWith(key)
                .compact();
    }


    // 검증
    // Subject -> 사용자 식별자 ( 사용자정보중 고유한값)
//    public String getUserName(String token) {
//
//        // ParSerBuilder를 이용해 JwtParser 객체 생성
//        JwtParser jwtParser = Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build();
//
//        // 토큰 파싱 JWS (JSON Web Signature) (JWT -> 서명된상태 Signed Token) 구조
//        Jws<Claims> jwsClaims = jwtParser.parseClaimsJws(token);
//
//        // Claims (Payload)부분 추출
//        Claims claims = jwsClaims.getBody();
//
//        // Claims에서 Subject 필드 가져오기
//        String username = claims.getSubject();
//
//        return username;
//    }

    /**
     * JWT Subject(username) 추출
     *
     * @param token JWT 토큰
     * @return 사용자명
     * @throws JwtException 토큰 해석 불가 시 예외 발생
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    /**
     * JWT Subject(role) 추출
     *
     * @param token JWT 토큰
     * @return 사용자명
     * @throws JwtException 토큰 해석 불가 시 예외 발생
     */
    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }


    /**
     * JWT 검증 (유효 기간 및 서명 검증)
     *
     * @param token JWT 토큰
     * @return 검증 결과 (true: 유효, false: 무효)
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            // passeClaimsJws -> 서명(Signature) exp(만료일) 검증해준다.
            // 사용자정의 Claim -> 개발자가 따로 검증로직을 만들어주어야함.
            return true;
        } catch (Exception e) {
            log.error("JWT 검증 실패: {}", e.getMessage());
            return false;
        }
    }
}
