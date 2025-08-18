package org.danji.availableMerchant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AvailableMerchantServiceImpl implements AvailableMerchantService {
    private final AvailableMerchantMapper merchantMapper;

    @Override
    public AvailableMerchantDTO create(AvailableMerchantDTO dto) {
        log.info("create available merchant: " + dto);

        dto.setAvailableMerchantId(UUID.randomUUID());
        AvailableMerchantVO vo = dto.toVo();
        merchantMapper.create(vo);

        return AvailableMerchantDTO.of(vo);
    }

    @Override
    public List<AvailableMerchantDTO> findByFilter(MerchantFilterDTO filterDTO) {
        return merchantMapper.findByFilter(filterDTO);
    }
}
