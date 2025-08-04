package org.danji.region.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.region.domain.RegionVO;
import org.danji.region.dto.RegionDTO;
import org.danji.region.dto.RegionFilterDTO;
import org.danji.region.mapper.RegionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{
    private final RegionMapper mapper;

    @Override
    public List<RegionDTO> getRegionList(RegionFilterDTO filterDTO) {

        List<RegionVO> regions = mapper.findByFilter(filterDTO);

        return regions.stream()
                .map(RegionDTO::of)
                .collect(Collectors.toList());
    }
}
