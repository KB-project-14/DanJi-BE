package org.danji.localCurrency.service;

import lombok.extern.log4j.Log4j2;
import org.danji.global.config.RootConfig;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.enums.BenefitType;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j2
@Transactional
class LocalCurrencyServiceImplTest {

    @Autowired
    private LocalCurrencyService localCurrencyService;

    @Autowired
    private LocalCurrencyMapper localCurrencyMapper;

    @Test
    void createLocalCurrency() {
        // given
        LocalCurrencyDTO createDto = LocalCurrencyDTO.builder()
                .regionId(11001L)   // test용 1번 아무거나
                .name("종로구 지역화폐")
                .benefitType(BenefitType.CASHBACK)
                .maximum(500000)
                .percentage(10)
                .build();

        // when
        LocalCurrencyDTO result = localCurrencyService.createLocalCurrency(createDto);

        // then
        log.info("resultDTO 확인 -------> " + result);
        log.info("resultDTO BaseDTO 찍히는지 확인 -------> " + result.getCreatedAt());
        assertNotNull(result);

        LocalCurrencyVO findVO = localCurrencyMapper.findById(result.getLocalCurrencyId());

        assertEquals(LocalCurrencyDTO.of(findVO), result);
    }
}