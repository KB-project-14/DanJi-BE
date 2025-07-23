package org.danji.availableMerchant.mapper;

import org.apache.ibatis.annotations.Param;
import org.danji.availableMerchant.domain.AvailableMerchantVO;

public interface AvailableMerchantMapper {

    //가맹점 추가
    void create(AvailableMerchantVO availableMerchant);

    //중복 체
    boolean existsByNameAndAddress(@Param("name") String name, @Param("address") String address);
}
