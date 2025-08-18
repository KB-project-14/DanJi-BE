package org.danji.batch.step;

import lombok.RequiredArgsConstructor;

import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LocalCurrencyPartitioner implements Partitioner {

    private final LocalCurrencyMapper localCurrencyMapper;

    private static final int PARTITION_SIZE = 10;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        List<LocalCurrencyDTO> allLocalCurrencies = localCurrencyMapper.findAll();
        Map<String, ExecutionContext> partitions = new HashMap<>();

        int partitionCount = (int) Math.ceil((double) allLocalCurrencies.size() / PARTITION_SIZE);

        for (int i = 0; i < partitionCount; i++) {
            int fromIndex = i * PARTITION_SIZE;
            int toIndex = Math.min(fromIndex + PARTITION_SIZE, allLocalCurrencies.size());

            List<LocalCurrencyDTO> subList = allLocalCurrencies.subList(fromIndex, toIndex);

            ExecutionContext context = new ExecutionContext();
            context.put("localCurrencyList", subList);

            partitions.put("partition" + i, context);
        }

        return partitions;
    }
}
