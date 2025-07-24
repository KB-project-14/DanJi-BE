package org.danji.localCurrency.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.board.domain.BoardVO;
import org.danji.localCurrency.domain.LocalCurrencyVO;

import java.util.Optional;
import java.util.UUID;

public interface LocalCurrencyMapper {

    // Mybatis 에서는 Optional 반환이 안됨
    LocalCurrencyVO findById(UUID localCurrencyId);

    void create(LocalCurrencyVO localCurrency);

}
