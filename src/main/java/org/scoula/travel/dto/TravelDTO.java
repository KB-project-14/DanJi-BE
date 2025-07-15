package org.scoula.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.travel.domain.TravelVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelDTO {
    List<MultipartFile> imageFiles;

    private long no;
    private String district;
    private String title;
    private String description;
    private String address;
    private String phone;
    private List<TravelImageDTO> images;

    public static TravelDTO of(TravelVO vo) {
        TravelDTO travel = TravelDTO.builder()
                .no(vo.getNo())
                .district(vo.getDistrict())
                .title(vo.getTitle())
                .description(vo.getDescription())
                .address(vo.getAddress())
                .phone(vo.getPhone())
                .build();

        if (vo.getImages() != null) {
            travel.setImages(vo.getImages().stream().map(TravelImageDTO::of).toList());
        }

        return travel;
    }
}
