package org.danji.localCurrency.mapper;

import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.dto.LocalCurrencyDetailDTO;
import org.danji.localCurrency.dto.LocalCurrencyFilterDTO;

import java.util.List;
import java.util.UUID;

public interface LocalCurrencyMapper {
    List<LocalCurrencyVO> findAllByName(String name);
    // Mybatis 에서는 Optional 반환이 안됨
    LocalCurrencyVO findById(UUID localCurrencyId);

    LocalCurrencyVO findByRegionId(Long RegionId);

    List<LocalCurrencyDTO> findByFilter(LocalCurrencyFilterDTO filter);

    void create(LocalCurrencyVO localCurrency);

    LocalCurrencyVO findByWalletId(UUID walletId);

    List<LocalCurrencyVO> findAll();

    //이미지 파일 포함한 세부 조회
    LocalCurrencyDetailDTO findDetailById(UUID localCurrencyId);
}
