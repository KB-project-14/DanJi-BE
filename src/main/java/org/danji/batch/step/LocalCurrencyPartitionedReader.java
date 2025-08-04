package org.danji.batch.step;

import lombok.RequiredArgsConstructor;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@StepScope
@RequiredArgsConstructor
public class LocalCurrencyPartitionedReader implements ItemReader<LocalCurrencyVO> {

    @Value("#{stepExecutionContext['localCurrency']}") // 혹은 'localCurrencyId', 'currencyName' 등
    private LocalCurrencyVO localCurrencyVO;

    private boolean isRead = false;

    @Override
    public LocalCurrencyVO read() {
        if (!isRead) {
            isRead = true;
            return localCurrencyVO;
        } else {
            return null;
        }
    }
}
