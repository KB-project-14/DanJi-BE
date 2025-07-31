package org.danji.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.member.domain.MemberVO;
import org.danji.member.enums.Role;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberUpdateDTO {

    private String username;
    private String password;
    private String name;
}

