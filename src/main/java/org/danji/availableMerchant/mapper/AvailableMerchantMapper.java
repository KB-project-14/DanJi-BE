package org.danji.availableMerchant.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.availableMerchant.domain.AvailableMerchantVO;

import java.util.List;

public interface AvailableMerchantMapper {

    //가맹점 추가
    void create(AvailableMerchantVO availableMerchant);

    //중복 체크
    boolean existsByNameAndAddress(@Param("name") String name, @Param("address") String address);

    //전체 목록 조회
    List<AvailableMerchantVO> findAll();
}
