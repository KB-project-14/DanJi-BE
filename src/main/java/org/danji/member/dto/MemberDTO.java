package org.danji.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.member.domain.MemberVO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDTO {

    private Long memberId;
    private String username;
    private String password;
    private String role;
    private String name;
    private List<SimpleGrantedAuthority> authList;


    public static MemberDTO of(MemberVO m) {
        return MemberDTO.builder()
                .username(m.getUsername())
                .password(m.getPassword())
                .role(m.getRole())
                .name(m.getName())
                .build();
    }

    public MemberVO toVO() {
        return MemberVO.builder()
                .username(username)
                .password(password)
                .role(role)
                .name(name)
                .build();

    }
}
