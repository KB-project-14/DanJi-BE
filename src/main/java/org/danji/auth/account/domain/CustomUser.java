package org.danji.auth.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.danji.member.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomUser extends User {
    //Security내에서 회원정보를 담을 객체는 User객체임.
    //우리의 회원정보는 MemberVO에 있음.
    //MemberVO --> User객체에 매핑시켜주어야함.

    private MemberVO member;

    //생성자 2개를 만들어줌.
    public CustomUser(MemberVO memberVO) {
        super(
                memberVO.getUsername(),
                memberVO.getPassword(),
                List.of(new SimpleGrantedAuthority(memberVO.getRole()))  // 🔧 수정된 부분
        );
        this.member = memberVO;
    }



    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
