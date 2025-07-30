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
import org.danji.security.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j2
@Transactional
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void test1() {
        String result1 = encoder.encode("1234");
        log.info("result1: " + result1);
        log.info(encoder.matches("1234", result1));

        String result2 = encoder.encode("1234");
        log.info("result2: " + result2);
        log.info(encoder.matches("1234", result2));
    }

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
        // given: 먼저 가입하고
        memberService.join(MemberJoinDTO.builder()
                .username("testuser")
                .password("1234")
                .name("기존이름")
                .build());

        // when: DTO 하나로만 업데이트 요청
        MemberUpdateDTO updateDto = MemberUpdateDTO.builder()
                .username("testuser")
                .password("1234")         // 본인 확인용 비밀번호
                .name("변경된이름")        // 바꿀 새 이름
                .build();

        MemberDTO result = memberService.update(updateDto);

        // then: 이름이 잘 바뀌었는지, username은 그대로인지 확인
        assertEquals("변경된이름", result.getName());
        assertEquals("testuser", result.getUsername());
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
        memberService.delete(dto);

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
                .role(Role.ROLE_USER)
                .name("조회용 유저")
                .build();

        memberMapper.insert(member); // 실제 DB에 insert

        // when
        MemberVO result = memberMapper.get("test");

        // then
        assertEquals("test", result.getUsername());  // username가 test인가
        assertEquals("조회용 유저", result.getName()); // name가 조회용유저인가
    }

}

