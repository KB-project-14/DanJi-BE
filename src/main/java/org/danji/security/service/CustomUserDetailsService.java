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


@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberVO vo = mapper.get(username);
        if (vo == null) {
            throw new MemberException(ErrorCode.USER_NOT_FOUND);
        }
        return new CustomUser(vo);
    }
}
