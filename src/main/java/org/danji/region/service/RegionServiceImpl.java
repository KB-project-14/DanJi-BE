package org.danji.region.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.region.domain.RegionVO;
import org.danji.region.dto.RegionDTO;
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
    public List<RegionDTO> getRegions() {
        log.info("get All Regions");

        //DB에서 모든 지역 정보 조회
        List<RegionVO> regions = mapper.findAll();

        //조회한 VO 리스트를 DTO로 변환
        //각 RegionVO 객체를 RegionDTO.of(vo)로 매핑
        //스트링을 통해 map 후 collect로 리스트로 변환
        return regions.stream()
                .map(RegionDTO::of)
                .collect(Collectors.toList());
    }
}
