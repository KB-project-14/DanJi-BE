package org.danji.cashback.domain;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.cashback.enums.CashBackStatus;
import org.danji.global.domain.BaseVO;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class CashbackVO extends BaseVO {
    private UUID cashbackId;
    private UUID walletId;
    private Integer amount;
    private LocalDateTime cashbackDate;
    private CashBackStatus status;

}
