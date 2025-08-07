package org.danji.memberBadge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.danji.badge.enums.BadgeType;
import org.danji.memberBadge.enums.BadgeGrade;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class MemberBadgeFilterDTO {

    @NotNull(message = "memberId 값은 필수입니다.")
    @ApiModelProperty(value = "member UUID", required = true, example = "00000000-0000-0000-0000-000000000000")
    private UUID memberId;
    private BadgeGrade badgeGrade;
    private BadgeType badgeType;
    private Long regionId;
}
