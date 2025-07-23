package org.danji.member.domain;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class MemberVO {

    private Long memberId;
    private String username;
    private String password;
    private String role;
    private String name;
    private List<SimpleGrantedAuthority> authList;

}
