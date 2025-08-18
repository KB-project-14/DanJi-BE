package org.danji.badge.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.badge.domain.BadgeVO;
import org.danji.badge.enums.BadgeType;
import org.danji.global.dto.BaseDTO;
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

    public static BadgeDTO of(@NonNull BadgeVO b) {
        return BadgeDTO.builder()
                .name(b.getName())
                .badgeId(b.getBadgeId())
                .badgeType(b.getBadgeType())
                .comment(b.getComment())
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
