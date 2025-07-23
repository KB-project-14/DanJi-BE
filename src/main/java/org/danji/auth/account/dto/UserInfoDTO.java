package org.danji.auth.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.member.domain.MemberVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    String username;
    String email;
    List<String> roles;

    public UserInfoDTO(String username, String name, String role, String password, Long memberId) {
    }

    public static UserInfoDTO of(MemberVO member) {
        return new UserInfoDTO(
                member.getUsername(),
                member.getName(), 
                member.getRole(),
                member.getPassword(),
                member.getMemberId()
        );
    }
}