package org.danji.member.dto;

import org.danji.member.enums.Role;

import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class MemberDeleteDTO {
    private String username;
    private String password;
}
