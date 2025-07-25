package org.danji.board.service;

import lombok.extern.log4j.Log4j2;
import org.danji.board.dto.BoardDTO;
import org.danji.global.config.RootConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j2
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    void create() {
        // given
        BoardDTO dto = BoardDTO.builder()
                .title("test1")
                .content("testContent")
                .writer("user1")
                .build();

        // when
        BoardDTO result = boardService.create(dto);

        // then
        log.info("crateadAt 확인 : " + result.getCreatedAt());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void get() {
        // given
        BoardDTO createdDTO = boardService.create(BoardDTO.builder()
                .title("test1")
                .content("testContent")
                .writer("user1")
                .build());

        // when
        BoardDTO result = boardService.get(createdDTO.getNo());

        // then
        log.info("crateadAt 확인 : " + result.getCreatedAt());
        assertNotNull(result.getCreatedAt());
    }


    @Test
    void update() {
        // given
        BoardDTO createdDTO = boardService.create(BoardDTO.builder()
                .title("test1")
                .content("testContent")
                .writer("user1")
                .build());

        BoardDTO updateDTO = BoardDTO.builder()
                .no(createdDTO.getNo())
                .title("updateTest1")
                .content("testContent")
                .writer("user1")
                .build();

        // when
        BoardDTO result = boardService.update(updateDTO);

        // then
        log.info("updatedAt 확인 : " + result.getUpdatedAt());
        assertNotNull(result.getUpdateDate());
        assertEquals(updateDTO.getTitle(), result.getTitle());
    }

    @Test
    void delete() {
        // given
        BoardDTO createdDTO = boardService.create(BoardDTO.builder()
                .title("test1")
                .content("testContent")
                .writer("user1")
                .build());

        // when
        boardService.delete(createdDTO.getNo());

        // then
        assertEquals(0, boardService.getList().size());

    }
}