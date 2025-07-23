package org.danji.cashback.converter;

import org.danji.cashback.domain.CashbackVO;
import org.danji.cashback.enums.CashBackStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CashbackConverter {

    public CashbackVO toCashbackVO(UUID cashbackId, UUID walletId, Integer amount, LocalDateTime date, CashBackStatus status) {
        return CashbackVO.builder()
                .cashbackId(cashbackId)
                .walletId(walletId)
                .amount(amount)
                .cashbackDate(date)
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

    }
}
