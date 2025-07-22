package org.danji.availableMerchant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AvailableMerchantServiceImpl implements AvailableMerchantService {
    private final AvailableMerchantMapper mapper;

    @Override
    public AvailableMerchantDTO create(AvailableMerchantDTO dto) {
        log.info("create available merchant: " + dto);

        //UUID 생성 및 VO 변환
        dto.setAvailableMerchantId(UUID.randomUUID());

        //DTO -> VO 변환
        AvailableMerchantVO vo = dto.toVo();

        //DB 저장
        mapper.create(vo);
        //다시 조회해 DTO 반환
        return AvailableMerchantDTO.of(vo);
    }
}
