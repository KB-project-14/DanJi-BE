package org.danji.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.member.domain.MemberVO;
import org.danji.member.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberJoinDTO {

    private String username;
    private String password;
    private Role role;
    private String name;

    // TODO 현재는 Role.USER만 등록
    public Role getRole() {
        return role != null ? role : Role.ROLE_USER;
    }

    public MemberVO toVO() {
        return MemberVO.builder()
                .username(username)
                .role(getRole())
                .name(name)
                .build();
    }
}
