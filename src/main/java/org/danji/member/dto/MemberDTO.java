package org.danji.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.danji.global.dto.BaseDTO;
import org.danji.member.domain.MemberVO;
import org.danji.member.enums.Role;

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

    private String accessToken;

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

}
