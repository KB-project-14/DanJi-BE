package org.danji.localCurrency.service;

import lombok.RequiredArgsConstructor;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalCurrencyServiceImpl implements LocalCurrencyService{

//    private final RegionMapper regionMapper;

    private final LocalCurrencyMapper localCurrencyMapper;

    public LocalCurrencyDTO createLocalCurrency(LocalCurrencyDTO localCurrencyDTO) {

        // TODO region 조회 로직
        /*
        if (regionMapper.get(localCurrencyDTO.getRegionId()).isEmpty()) {
            throw new RegionException();
        }
         */




    }

}
