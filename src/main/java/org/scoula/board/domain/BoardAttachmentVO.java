package org.scoula.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.common.util.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAttachmentVO {

    private Long no;
    private Long bno;
    private Long size;
    private String contentType;
    private String path;
    private String filename;
    private Date regDate;

    // of
    // MultipartFile 정보로 BoardAttachmentVO 생성
    // MultipartFile, Board no, 파일 경로 배개변수로 받아옴
    public static BoardAttachmentVO of(MultipartFile part, Long bno, String path) {
        return BoardAttachmentVO.builder()
                .bno(bno)   // 해당하는 게시물 번호
                .filename(part.getOriginalFilename())
                .path(path)
                .size(part.getSize())
                .contentType(part.getContentType())
                .build();
    }

    public String getFileSize() {
        return size != null ? UploadFiles.getFormatSize(size) : "0 B";
    }
}
