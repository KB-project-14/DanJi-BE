package org.danji.availableMerchant.service;

import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;

import java.util.List;

public interface AvailableMerchantService {
    //가맹점 추가
    AvailableMerchantDTO create(AvailableMerchantDTO availableMerchant);

    //공공데이터에서 가맹점 가져와서 DB에 저장
    void importFromPublicAPI();

    //가맹점 전체 조회
    List<AvailableMerchantDTO> findByFilter(MerchantFilterDTO filterDTO);

    //findByFilter를 재사용해 전체 목록 조회 기능 제공
    default List<AvailableMerchantDTO> getAll() {
        return findByFilter(new MerchantFilterDTO());
    }
}
