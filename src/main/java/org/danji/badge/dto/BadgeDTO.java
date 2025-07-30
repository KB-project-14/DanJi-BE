package org.danji.badge.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.badge.domain.BadgeVO;
import org.danji.badge.enums.BadgeType;
import org.danji.global.dto.BaseDTO;
import org.danji.member.domain.MemberVO;
import org.danji.member.dto.MemberDTO;
import org.danji.member.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data

public class BadgeDTO extends BaseDTO {

    private UUID badgeId;
    private String name;
    private BadgeType badgeType;
    private String comment;

    public static BadgeDTO of(BadgeVO m) {
        return BadgeDTO.builder()
                .name(m.getName())
                .badgeId(m.getBadgeId())
                .badgeType(m.getBadgeType())
                .comment(m.getComment())
                .build();
    }

    public BadgeVO toVO() {
        return BadgeVO.builder()
                .name(name)
                .badgeId(badgeId)
                .badgeType(badgeType)
                .comment(comment)
                .build();
    }
}
