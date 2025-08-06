package org.danji.batch.step;

import lombok.RequiredArgsConstructor;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.danji.region.domain.RegionVO;
import org.danji.region.mapper.RegionMapper;
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
    private final RegionMapper regionMapper;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        List<LocalCurrencyVO> localCurrencies = localCurrencyMapper.findAll(); // 예: DB에서 전체 가져오기
        Map<String, ExecutionContext> partitions = new HashMap<>();

        for (int i = 0; i < localCurrencies.size(); i++) {
            ExecutionContext context = new ExecutionContext();
            LocalCurrencyVO localCurrencyVO = localCurrencies.get(i);
            RegionVO regionVO = regionMapper.findById(localCurrencyVO.getRegionId());
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
            context.put("localCurrency", localCurrencyDTO); // 직렬화 가능해야 함
            partitions.put("partition" + i, context);
        }

        return partitions;
    }
}
