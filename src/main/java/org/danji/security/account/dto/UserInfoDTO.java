package org.danji.security.account.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.member.domain.MemberVO;
import org.danji.member.enums.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String username;
    private Role role;


    /**
     * MemberVO에서 UserInfoDTO로 변환하는 팩토리 메서드
     * @param member
     * @return 변환된 UserInfoDTO
     */
    public static UserInfoDTO of(MemberVO member) {
        return new UserInfoDTO(
            member.getUsername(),
            member.getRole()
        );
    }
}