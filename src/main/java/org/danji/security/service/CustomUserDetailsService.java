package org.danji.security.service;

import lombok.RequiredArgsConstructor;
import org.danji.global.error.ErrorCode;
import org.danji.member.domain.MemberVO;
import org.danji.member.exception.MemberException;
import org.danji.member.mapper.MemberMapper;
import org.danji.security.account.domain.CustomUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/*
 * 1. 사용자가 로그인 (id/password 입력)
 * 2. 시큐리티 내부에서 UsernamePasswordAuthenticationFilter 실행
 * 3. AuthenticationManager -> AuthenticationProvider 호출
 * 4. Provider가 loadUserByUsername() 메서드 호출 -> userDetail에 유저정보(인증정보) 저장
 * 5. Security가 UserDetails에서 꺼낸 아이디랑 비밀번호를 비교해 로그인 성공여부 판단
 * */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper mapper;

    // Security로 로그인할때 form에 입력한 ID값을 이용해 사용자 정보를 가져오는 것
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberVO vo = mapper.get(username);
        if (vo == null) {
            throw new MemberException(ErrorCode.USER_NOT_FOUND);
        }
        return new CustomUser(vo);
    }
}
