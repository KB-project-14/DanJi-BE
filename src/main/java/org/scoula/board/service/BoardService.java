package org.scoula.board.service;

import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.common.pagination.Page;
import org.scoula.common.pagination.PageRequest;

import java.util.List;

public interface BoardService {

    List<BoardDTO> getList();

    Page<BoardDTO> getPage(PageRequest pageRequest);

    BoardDTO get(Long no);

    BoardDTO create(BoardDTO board);

    BoardDTO update(BoardDTO board);

    BoardDTO delete(Long no);


    // 첨부파일 관련 메서드 추가
    BoardAttachmentVO getAttachment(Long no);

    boolean deleteAttachment(Long no);

}