package org.danji.region.mapper;

import org.danji.region.domain.RegionVO;
import org.danji.region.dto.RegionFilterDTO;

import java.util.List;

public interface RegionMapper {

    List<RegionVO> findByFilter(RegionFilterDTO filterDTO);

    RegionVO findById(Long regionId);
}
