package org.danji.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.member.domain.MemberVO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDTO {

    private Long memberId;
    private String username;
    private String password;
    private String role;
    private String name;
    private List<SimpleGrantedAuthority> authList;

    public MemberVO toVO() {
        return MemberVO.builder()
                .username(username)
                .password(password)
                .role(role)
                .name(name)
                .build();

    }
}