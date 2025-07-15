package org.scoula.travel.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.common.pagination.Page;
import org.scoula.common.pagination.PageRequest;
import org.scoula.common.util.UploadFiles;
import org.scoula.travel.dto.TravelDTO;
import org.scoula.travel.dto.TravelImageDTO;
import org.scoula.travel.service.TravelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
@Log4j2
public class TravelController {

    final TravelService service;

    @GetMapping("")
    public ResponseEntity<Page> getTravels(PageRequest pageRequest) {
        ResponseEntity<Page> list = ResponseEntity.ok(service.getPage(pageRequest));
        System.out.println(list);
        return list;
    }

    @GetMapping("/{no}")
    public ResponseEntity<TravelDTO> getTravels(@PathVariable("no") Long no) {
        System.out.println("no=========================> " + no);
        return ResponseEntity.ok(service.get(no));
    }


    @GetMapping("/image/{no}")
    public void viewImage(@PathVariable Long no, HttpServletResponse response) {
        TravelImageDTO image = service.getImage(no);
        File file = new File(image.getPath());
        UploadFiles.downloadImage(response, file);
    }
}
