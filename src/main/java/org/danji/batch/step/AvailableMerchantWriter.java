package org.danji.batch.step;

import lombok.RequiredArgsConstructor;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AvailableMerchantWriter implements ItemWriter<List<AvailableMerchantVO>> {

    private final AvailableMerchantMapper merchantMapper;

    @Override
    public void write(List<? extends List<AvailableMerchantVO>> items) {
        for (List<AvailableMerchantVO> batch : items) {
            for (AvailableMerchantVO vo : batch) {
                if (!merchantMapper.existsByNameAndAddress(vo.getName(), vo.getAddress())) {
                    merchantMapper.create(vo);
                }
            }
        }
    }
}
