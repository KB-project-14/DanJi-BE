package org.danji.member.service;

import lombok.extern.log4j.Log4j2;
import org.danji.global.config.RootConfig;
import org.danji.member.dto.MemberDTO;
import org.danji.member.dto.MemberJoinDTO;
import org.danji.member.mapper.MemberMapper;
import org.junit.jupiter.api.Disabled;
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
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberMapper memberMapper;

    @Test
    @Disabled
    void join() {
        // given
        MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
                .username("test")
                .password("danji")
                .name("dandan")
                .build();
        // when
        MemberDTO result =memberService.join(memberJoinDTO);

        // then
        assertEquals(memberMapper.get("test"), result);
    }
}