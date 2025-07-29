package org.danji.member.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.member.domain.MemberVO;
import org.danji.member.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data

public class MemberDTO extends BaseDTO {

    private UUID memberId;
    private String username;
    private Role role;
    private String name;

    public static MemberDTO of(MemberVO m) {
        return MemberDTO.builder()
                .memberId(m.getMemberId())
                .username(m.getUsername())
                .role(m.getRole())
                .name(m.getName())
                .createdAt(m.getCreatedAt())
                .updatedAt(m.getUpdatedAt())
                .build();
    }

    public MemberVO toVO() {
        return MemberVO.builder()
                .username(username)
                .role(role)
                .name(name)
                .build();

    }
}
