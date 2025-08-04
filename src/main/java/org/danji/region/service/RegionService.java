package org.danji.region.service;

import org.danji.region.dto.RegionDTO;
import org.danji.region.dto.RegionFilterDTO;

import java.util.List;

public interface RegionService {
    List<RegionDTO> getRegionList(RegionFilterDTO filterDTO);
}
