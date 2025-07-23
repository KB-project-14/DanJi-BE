package org.danji.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.auth.account.domain.CustomUser;
import org.danji.auth.account.mapper.UserDetailsMapper;
import org.danji.member.domain.MemberVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor //생성자를 자동으로 주입해줌.
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberVO memberVO = userDetailsMapper.get(username);
        return new CustomUser(memberVO);
    }
}
