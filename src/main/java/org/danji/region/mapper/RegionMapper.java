package org.danji.region.mapper;

import org.danji.region.domain.RegionVO;

import java.util.List;

public interface RegionMapper {
    List<RegionVO> findAll();
}
