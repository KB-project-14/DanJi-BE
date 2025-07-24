package org.danji.localCurrency.service;

import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.dto.LocalCurrencyFilterDTO;

import java.util.List;

public interface LocalCurrencyService {

    LocalCurrencyDTO createLocalCurrency(LocalCurrencyDTO localCurrencyDTO);

    LocalCurrencyDTO getLocalCurrencyByRegionId(Long RegionId);

    List<LocalCurrencyDTO> getLocalCurrencyList(LocalCurrencyFilterDTO filter);

}
