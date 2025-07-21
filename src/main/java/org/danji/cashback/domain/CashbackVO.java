package org.danji.cashback.domain;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.danji.global.domain.BaseVO;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class CashbackVO extends BaseVO {
    private UUID cashbackId;
    private UUID memberId;
    private UUID walletId;
    private Integer amount;
    private LocalDateTime cashbackDate;

    @Builder
    public CashbackVO(UUID cashbackId, UUID memberId, UUID walletId, int amount, LocalDateTime cashbackDate) {
        this.cashbackId = cashbackId;
        this.memberId = memberId;
        this.walletId = walletId;
        this.amount = amount;
        this.cashbackDate = cashbackDate;
    }
}
