package org.danji.cashback.mapper;

import org.danji.cashback.domain.CashbackVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CashbackMapper {
    CashbackVO insert(CashbackVO cashbackVo);

    List<CashbackVO> findDueCashbacks(LocalDateTime now);

    void markAsCompleted(UUID cashbackId);
}
