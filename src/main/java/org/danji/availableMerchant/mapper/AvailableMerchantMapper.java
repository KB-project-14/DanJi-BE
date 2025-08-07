package org.danji.availableMerchant.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AvailableMerchantMapper {

    //가맹점 추가
    void create(AvailableMerchantVO availableMerchant);

    //전체 목록 조회
    List<AvailableMerchantDTO> findByFilter(MerchantFilterDTO filterDTO);

    //여러 개의 가맹점 데이터를 한 번에 저장
    void createBatch(@Param("list") List<AvailableMerchantVO> merchants);

    AvailableMerchantVO findById(UUID availableMerchantId);

    List<AvailableMerchantVO> findExistingByNameAndAddressList(@Param("merchantKeys") List<Map<String, String>> merchantKeys);
}
