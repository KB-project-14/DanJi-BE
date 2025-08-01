package org.danji.localCurrency.service;

import lombok.RequiredArgsConstructor;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.dto.LocalCurrencyFilterDTO;
import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalCurrencyServiceImpl implements LocalCurrencyService {

    private final LocalCurrencyMapper localCurrencyMapper;

    @Override
    public LocalCurrencyDTO createLocalCurrency(LocalCurrencyDTO localCurrencyDTO) {

        UUID localCurrencyId = UUID.randomUUID();
        localCurrencyDTO.setLocalCurrencyId(localCurrencyId);

        localCurrencyMapper.create(localCurrencyDTO.toVo());

        return getLocalCurrency(localCurrencyId);
    }


    @Override
    public LocalCurrencyDTO getLocalCurrency(UUID localCurrencyId) {

        LocalCurrencyVO vo = localCurrencyMapper.findById(localCurrencyId);

        if (vo == null) {
            throw new LocalCurrencyException(ErrorCode.LOCAL_CURRENCY_NOT_FOUND);
        }

        return LocalCurrencyDTO.of(vo);
    }

    @Override
    public List<LocalCurrencyDTO> getLocalCurrencyList(LocalCurrencyFilterDTO filter) {

        List<LocalCurrencyVO> voList = localCurrencyMapper.findByFilter(filter);

        return voList.stream().map(LocalCurrencyDTO::of).toList();
    }
}
