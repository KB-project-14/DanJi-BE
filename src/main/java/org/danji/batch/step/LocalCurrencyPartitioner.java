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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LocalCurrencyPartitioner implements Partitioner {

    private final LocalCurrencyMapper localCurrencyMapper;
    private final RegionMapper regionMapper;

    private static final int PARTITION_SIZE = 10;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        List<LocalCurrencyVO> allLocalCurrencies = localCurrencyMapper.findAll();
        Map<String, ExecutionContext> partitions = new HashMap<>();

        List<LocalCurrencyDTO> allDtos = new ArrayList<>();

        // LocalCurrencyVO → DTO 변환
        for (LocalCurrencyVO vo : allLocalCurrencies) {
            RegionVO regionVO = regionMapper.findById(vo.getRegionId());

            LocalCurrencyDTO dto = LocalCurrencyDTO.builder()
                    .localCurrencyId(vo.getLocalCurrencyId())
                    .regionId(vo.getRegionId())
                    .name(vo.getName())
                    .benefitType(vo.getBenefitType())
                    .maximum(vo.getMaximum())
                    .percentage(vo.getPercentage())
                    .createdAt(vo.getCreatedAt())
                    .updatedAt(vo.getUpdatedAt())
                    .regionName(regionVO.getProvince())
                    .build();

            allDtos.add(dto);
        }

        // 10개씩 나누어 파티션 생성
        int partitionCount = (int) Math.ceil((double) allDtos.size() / PARTITION_SIZE);

        for (int i = 0; i < partitionCount; i++) {
            int fromIndex = i * PARTITION_SIZE;
            int toIndex = Math.min(fromIndex + PARTITION_SIZE, allDtos.size());

            List<LocalCurrencyDTO> subList = allDtos.subList(fromIndex, toIndex);

            ExecutionContext context = new ExecutionContext();
            context.put("localCurrencyList", subList); // 직렬화 가능해야 함

            partitions.put("partition" + i, context);
        }

        return partitions;
    }
}
