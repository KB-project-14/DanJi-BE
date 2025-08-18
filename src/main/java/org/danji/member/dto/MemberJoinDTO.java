package org.danji.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.member.domain.MemberVO;
import org.danji.member.enums.Role;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberJoinDTO {

    private String username;
    private String password;
    private Role role;
    private String name;

    @Pattern(regexp = "^[0-9]{4}$", message = "결제 비밀번호는 4자리 숫자만 가능합니다.")
    private String paymentPin;

    public Role getRole() {
        return role != null ? role : Role.ROLE_USER;
    }

    public MemberVO toVO() {
        return MemberVO.builder()
                .username(username)
                .role(getRole())
                .name(name)
                .paymentPin(paymentPin)
                .build();
    }
}
