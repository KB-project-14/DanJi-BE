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

import java.util.List;
import java.util.UUID;

@Component
@StepScope
@RequiredArgsConstructor
public class LocalCurrencyPartitionedReader implements ItemReader<LocalCurrencyDTO> {

    @Value("#{stepExecutionContext['localCurrencyList']}")
    private List<LocalCurrencyDTO> localCurrencyList;

    private int currentIndex = 0;

    @Override
    public LocalCurrencyDTO read() {
        if (localCurrencyList == null || currentIndex >= localCurrencyList.size()) {
            return null;
        }
        return localCurrencyList.get(currentIndex++);
    }
}
