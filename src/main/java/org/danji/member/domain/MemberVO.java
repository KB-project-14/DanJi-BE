package org.danji.member.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;
import org.danji.member.enums.Role;
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
    private String paymentPin;

}
