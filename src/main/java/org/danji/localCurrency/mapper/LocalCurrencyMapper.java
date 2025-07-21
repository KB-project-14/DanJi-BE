package org.danji.localCurrency.mapper;

import org.danji.localCurrency.domain.LocalCurrencyVO;

import java.util.Optional;
import java.util.UUID;

public interface LocalCurrencyMapper {

    Optional<LocalCurrencyVO> findLocalCurrency(UUID localCurrencyId);

}
