package org.danji.availableMerchant.service;

import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;

import java.util.List;

public interface AvailableMerchantService {

    AvailableMerchantDTO create(AvailableMerchantDTO availableMerchant);

    List<AvailableMerchantDTO> findByFilter(MerchantFilterDTO filterDTO);

    default List<AvailableMerchantDTO> getAll() {
        return findByFilter(new MerchantFilterDTO());
    }
}
