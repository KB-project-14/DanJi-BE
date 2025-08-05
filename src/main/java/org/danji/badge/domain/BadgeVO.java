package org.danji.badge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.danji.badge.enums.BadgeType;
import org.danji.global.domain.BaseVO;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class BadgeVO extends BaseVO {
    private UUID badgeId;
    private String name;
    private BadgeType badgeType;
    private Long regionId;
    private String comment;
}
