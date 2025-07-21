package org.danji.cashback.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.cashback.domain.CashbackVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CashbackMapper {
    CashbackVO insert(CashbackVO cashbackVo);

    List<CashbackVO> findDueCashBacks(LocalDateTime now);

    void markAsCompleted(@Param("cashbackId") UUID cashbackId);
}
