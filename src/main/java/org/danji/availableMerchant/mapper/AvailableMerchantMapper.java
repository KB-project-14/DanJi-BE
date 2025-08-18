package org.danji.availableMerchant.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AvailableMerchantMapper {

    void create(AvailableMerchantVO availableMerchant);

    List<AvailableMerchantDTO> findByFilter(MerchantFilterDTO filterDTO);

    void createBatch(@Param("list") List<AvailableMerchantVO> merchants);

    AvailableMerchantVO findById(UUID availableMerchantId);

    List<AvailableMerchantVO> findExistingByNameAndAddressList(@Param("merchantKeys") List<Map<String, String>> merchantKeys);
}
