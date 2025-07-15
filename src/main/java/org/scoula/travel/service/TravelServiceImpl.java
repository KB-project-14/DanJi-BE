package org.scoula.travel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.common.pagination.Page;
import org.scoula.common.pagination.PageRequest;
import org.scoula.travel.domain.TravelImageVO;
import org.scoula.travel.domain.TravelVO;
import org.scoula.travel.dto.TravelDTO;
import org.scoula.travel.dto.TravelImageDTO;
import org.scoula.travel.mapper.TravelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {
    final TravelMapper mapper;

    @Override
    public Page<TravelDTO> getPage(PageRequest pageRequest) {

        List<TravelDTO> travels = mapper.getPage(pageRequest)
                .stream().map(TravelDTO::of).toList();

        travels.forEach(travel -> {
            List<TravelImageDTO> images = mapper.getImages(
                    travel.getNo()).stream().map(TravelImageDTO::of).toList();
            travel.setImages(images);
        });
        int totalCount = mapper.getTotalCount();
        return Page.of(pageRequest, totalCount, travels);
    }

    @Override
    public List<TravelDTO> getList() {
        List<TravelVO> travels = mapper.getTravels();
        return travels.stream().map(TravelDTO::of).toList();
    }

    @Override
    public TravelDTO get(Long no) {
        TravelVO travel = mapper.getTravel(no);
        if (travel == null) {
            throw new NoSuchElementException();
        }
        return TravelDTO.of(travel);
    }

    @Override
    public TravelImageDTO getImage(Long no) {
        TravelImageVO image = mapper.getImage(no);
        return TravelImageDTO.of(image);
    }

    @Override
    public boolean deleteImage(Long no) {
        return mapper.delete(no);
    }
}
