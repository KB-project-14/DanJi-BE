package org.danji.region.mapper;

import org.danji.region.domain.RegionVO;

import java.util.List;

public interface RegionMapper {
    //지역 리스트 조회
    List<RegionVO> findAll();
    //지역 단건 조
    RegionVO findById(Long regionId);
}
