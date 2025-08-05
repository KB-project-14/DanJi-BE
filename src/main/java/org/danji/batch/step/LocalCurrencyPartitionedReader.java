package org.danji.batch.step;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.danji.region.domain.RegionVO;
import org.danji.region.mapper.RegionMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@StepScope
@RequiredArgsConstructor
public class LocalCurrencyPartitionedReader implements ItemReader<LocalCurrencyDTO> {

    private final RegionMapper regionMapper;

    @Value("#{stepExecutionContext['localCurrency']}") // 혹은 'localCurrencyId', 'currencyName' 등
    private LocalCurrencyVO localCurrencyVO;

    private boolean isRead = false;

    @Override
    public LocalCurrencyDTO read() {
        RegionVO regionVO = regionMapper.findById(localCurrencyVO.getRegionId());
        if (!isRead) {
            isRead = true;
            LocalCurrencyDTO localCurrencyDTO = LocalCurrencyDTO.builder()
                    .localCurrencyId(localCurrencyVO.getLocalCurrencyId())
                    .regionId(localCurrencyVO.getRegionId())
                    .name(localCurrencyVO.getName())
                    .benefitType(localCurrencyVO.getBenefitType())
                    .maximum(localCurrencyVO.getMaximum())
                    .percentage(localCurrencyVO.getPercentage())
                    .createdAt(localCurrencyVO.getCreatedAt())
                    .updatedAt(localCurrencyVO.getUpdatedAt())
                    .regionName(regionVO.getProvince())
                    .build();
            return localCurrencyDTO;
        } else {
            return null;
        }
    }
}
