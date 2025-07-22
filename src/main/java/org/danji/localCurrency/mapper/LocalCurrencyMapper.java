package org.danji.localCurrency.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.board.domain.BoardVO;
import org.danji.localCurrency.domain.LocalCurrencyVO;

import java.util.Optional;
import java.util.UUID;

public interface LocalCurrencyMapper {

    Optional<LocalCurrencyVO> findLocalCurrency(UUID localCurrencyId);

    void create(LocalCurrencyVO localCurrency);

}
