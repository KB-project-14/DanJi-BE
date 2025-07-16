package org.danji.travel.service;

import org.danji.common.pagination.Page;
import org.danji.common.pagination.PageRequest;
import org.danji.travel.dto.TravelDTO;
import org.danji.travel.dto.TravelImageDTO;

import java.util.List;

public interface TravelService {
    Page<TravelDTO> getPage(PageRequest pageRequest);

    List<TravelDTO> getList();

    TravelDTO get(Long no);

    TravelImageDTO getImage(Long no);

    boolean deleteImage(Long no);

}
