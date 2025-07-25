package org.danji.member.service;

import lombok.extern.log4j.Log4j2;
import org.danji.global.config.RootConfig;
import org.danji.member.domain.MemberVO;
import org.danji.member.dto.MemberDTO;
import org.danji.member.dto.MemberDeleteDTO;
import org.danji.member.dto.MemberJoinDTO;
import org.danji.member.dto.MemberUpdateDTO;
import org.danji.member.enums.Role;
import org.danji.member.mapper.MemberMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

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
        // @Disabled
    void join() {
        // given
        MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
                .username("test")
                .password("danji")
                .name("dandan")
                .build();
        // when
        MemberDTO result = memberService.join(memberJoinDTO);

        // then
        assertEquals(MemberDTO.of(memberMapper.get("test")), result);
        //assertEquals(memberMapper.get("test"), result);
        // --> memberMapper.get("test")는 memberVO타입 인데 result는 memberDTO 타입 다른 타입이라 테스트 실패했다
    }

    @Test
    void update() {
        // given
        memberService.join(MemberJoinDTO.builder()
                .username("testuser")
                .password("1234")
                .name("기존이름")
                .build());

        // when
        MemberUpdateDTO dto = new MemberUpdateDTO();
        dto.setUsername("testuser");
        dto.setPassword("1234");
        dto.setName("변경된이름");

        MemberDTO result = memberService.update(dto);

        // then
        assertEquals("변경된이름", result.getName());
    }

    @Test
    void delete() {
        // given
        memberService.join(MemberJoinDTO.builder()
                .username("test")
                .password("1234")
                .name("삭제용유저")
                .build());

        // when
        MemberDeleteDTO dto = MemberDeleteDTO.builder()
                .username("test")
                .password("1234")
                .build();
        memberService.delete(dto.getUsername());

        // then
        assertThrows(NoSuchElementException.class, () -> {
            memberService.get("test");
        });
    }

    @Test
    void get() {
        // given
        MemberVO member = MemberVO.builder()
                .memberId(UUID.randomUUID())
                .username("test")
                .password("1234")
                .role(Role.USER)
                .name("조회용 유저")
                .build();

        memberMapper.insert(member); // 실제 DB에 insert

        // when
        MemberVO result = memberService.get("test").toVO();

        // then
        assertEquals("test", result.getUsername());  // username가 test인가
        assertEquals("조회용 유저", result.getName()); // name가 조회용유저인가
    }

}

