package org.danji.cashback.converter;


import lombok.RequiredArgsConstructor;
import org.danji.cashback.domain.CashbackVO;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.transaction.dto.request.TransferDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CashbackConverter {

    public CashbackVO toCashbackVO(UUID cashbackId, UUID memberId, UUID walletId, int amount, LocalDateTime cashbackDate){
        return CashbackVO.builder()
                .cashbackId(cashbackId)
                .memberId(memberId)
                .walletId(walletId)
                .amount(amount)
                .cashbackDate(cashbackDate)
                .build();
    }
}
