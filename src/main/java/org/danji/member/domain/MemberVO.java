package org.danji.member.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.member.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder

public class MemberVO extends BaseVO {

    private UUID memberId;
    private String username;
    private String password;
    private Role role;
    private String name;

}
