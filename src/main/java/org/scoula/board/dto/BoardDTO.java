package org.scoula.board.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.domain.BoardVO;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {

    private Long no;
    private String title;
    private String content;
    private String writer;
    private Date regDate;
    private Date updateDate;

    // 첨부파일
    private List<BoardAttachmentVO> attaches;

    // 실제 업로드된 파일들
    private List<MultipartFile> files = new ArrayList<>();

    public static BoardDTO of(BoardVO vo) {
        return vo == null ? null : BoardDTO.builder()
            .no(vo.getNo())
            .title(vo.getTitle())
            .content(vo.getContent())
            .writer(vo.getWriter())
            .regDate(vo.getRegDate())
            .updateDate(vo.getUpdateDate())
            .attaches(vo.getAttaches())
            .build();
    }

    public BoardVO toVo() {
        return BoardVO.builder()
            .no(no)
            .title(title)
            .content(content)
            .writer(writer)
            .regDate(regDate)
            .updateDate(updateDate)
            .attaches(attaches)
            .build();
    }
}