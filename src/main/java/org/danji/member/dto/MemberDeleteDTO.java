package org.danji.member.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class MemberDeleteDTO {
    private String username;
    private String password;
}
