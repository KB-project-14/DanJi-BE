package org.scoula.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    //Security내에서 회원정보를 담을 객체는 User객체임.
    //우리의 회원정보는 MemberVO에 있음.
    //MemberVO --> User객체에 매핑시켜주어야함.

    private MemberVO member;

    //생성자 2개를 만들어줌.
    public CustomUser(MemberVO memberVO) {
        super(memberVO.getUsername(), memberVO.getPassword(), memberVO.getAuthList());
        this.member = memberVO;
    }

    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
