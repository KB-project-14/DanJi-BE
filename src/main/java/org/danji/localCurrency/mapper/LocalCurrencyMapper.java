package org.danji.localCurrency.mapper;

import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyFilterDTO;

import java.util.List;
import java.util.UUID;

public interface LocalCurrencyMapper {
    List<LocalCurrencyVO> findAllByName(String name);
    // Mybatis 에서는 Optional 반환이 안됨
    LocalCurrencyVO findById(UUID localCurrencyId);

    LocalCurrencyVO findByRegionId(Long RegionId);

    List<LocalCurrencyVO> findByFilter(LocalCurrencyFilterDTO filter);

    void create(LocalCurrencyVO localCurrency);

}
