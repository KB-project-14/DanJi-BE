package org.danji.availableMerchant.service;

import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;

public interface AvailableMerchantService {
    //가맹점 추가
    AvailableMerchantDTO create(AvailableMerchantDTO availableMerchant);

    //공공데이터에서 가맹점 가져와서 DB에 저장
    void importFromPublicAPI();
}
