package org.danji.availableMerchant.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;

import java.util.List;
import java.util.UUID;

public interface AvailableMerchantMapper {

    //가맹점 추가
    void create(AvailableMerchantVO availableMerchant);

    //중복 체크
    boolean existsByNameAndAddress(@Param("name") String name, @Param("address") String address);

    //전체 목록 조회
    List<AvailableMerchantVO> findByFilter(MerchantFilterDTO filterDTO);

    //여러 개의 가맹점 데이터를 한 번에 저장
    void createBatch(List<AvailableMerchantVO> merchants);

    AvailableMerchantVO findById(UUID availableMerchantId);
}
