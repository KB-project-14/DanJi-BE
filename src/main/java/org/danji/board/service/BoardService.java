package org.danji.board.service;

import org.danji.board.domain.BoardAttachmentVO;
import org.danji.board.dto.BoardDTO;
import org.danji.common.pagination.Page;
import org.danji.common.pagination.PageRequest;

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