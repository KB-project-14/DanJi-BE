package org.scoula.travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scoula.common.pagination.PageRequest;
import org.scoula.travel.domain.TravelImageVO;
import org.scoula.travel.domain.TravelVO;

import java.util.List;

@Mapper
public interface TravelMapper {
    int getTotalCount();

    List<String> getDistricts();    // 권역 목록 얻기

    List<TravelVO> getTravels();    // 목록 얻기

    List<TravelVO> getPage(PageRequest pageRequest);    // 페이지별 목록 얻기

    List<TravelVO> getTravelsByDistrict(String district);  // 해당 권역의 목록 얻기

    TravelVO getTravel(Long no);    // 특정 관광지 정보 얻기

    List<TravelImageVO> getImages(Long travelNo);  // 해당 관광지 이미지 목록 얻기

    TravelImageVO getImage(Long no);  // 이미지 정보 얻기

    boolean delete(Long no);
}
