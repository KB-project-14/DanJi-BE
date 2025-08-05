package org.danji.localCurrency.service;

import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.dto.LocalCurrencyDetailDTO;
import org.danji.localCurrency.dto.LocalCurrencyFilterDTO;

import java.util.List;
import java.util.UUID;

public interface LocalCurrencyService {

    LocalCurrencyDTO createLocalCurrency(LocalCurrencyDTO localCurrencyDTO);

    LocalCurrencyDTO getLocalCurrency(UUID localCurrencyId);

    List<LocalCurrencyDTO> getLocalCurrencyList(LocalCurrencyFilterDTO filter);

    LocalCurrencyDetailDTO getLocalCurrencyDetail(UUID localCurrencyId);
}
