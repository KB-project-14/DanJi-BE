package org.danji.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.danji.member.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
public class CustomUser extends User {

    private MemberVO member;

    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(MemberVO vo) {
        super(vo.getUsername(), vo.getPassword(),
                List.of(new SimpleGrantedAuthority(vo.getRole().name())));
        this.member = vo;
    }
}
