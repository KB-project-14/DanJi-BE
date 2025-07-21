package org.danji.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.cashback.domain.CashbackVO;
import org.danji.cashback.mapper.CashbackMapper;
import org.danji.wallet.mapper.WalletMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class CashbackScheduler {

    private final CashbackMapper cashbackMapper;
    private final WalletMapper walletMapper;

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시 실행
    public void processDueCashbacks() {
        List<CashbackVO> dueList = cashbackMapper.findDueCashBacks(LocalDateTime.now());

        for (CashbackVO cashbackVO : dueList) {
            walletMapper.updateWalletBalance(cashbackVO.getWalletId(), cashbackVO.getAmount());
            cashbackMapper.markAsCompleted(cashbackVO.getCashbackId());
            log.info("캐시백 지급 완료: walletId={}, amount={}", cashbackVO.getWalletId(), cashbackVO.getAmount());
        }
    }
}
