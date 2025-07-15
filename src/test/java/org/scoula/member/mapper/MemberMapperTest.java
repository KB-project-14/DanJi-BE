package org.scoula.member.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.account.domain.AuthVO;
import org.scoula.security.account.domain.MemberVO;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j2
class MemberMapperTest {

    @Autowired
    MemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void insert() {

        MemberVO memberVO = MemberVO.builder()
            .username("test2")
            .password("$2a$10$LyIEnOZaHXqqiF2RzsISluXnCLOVFY6P/CJ5EMHagov09a5TtiA1G")
            .email("test@test.com")
            .build();

        int result = mapper.insert(memberVO);
        Assertions.assertEquals(0, result);
    }

    @Test
    void insertAuth() {

        AuthVO authVO = new AuthVO();
        authVO.setUsername("test2");
        authVO.setAuth("ROLE_MEMBER");

        int result = mapper.insertAuth(authVO);
        Assertions.assertEquals(0, result);
    }

    @Test
    void join() {

        MemberVO member = MemberVO.builder()
            .username("test6")
            .password("$2a$10$LyIEnOZaHXqqiF2RzsISluXnCLOVFY6P/CJ5EMHagov09a5TtiA1G")
            .email("test5@test.com")
            .build();

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        int result0 = mapper.insert(member);
        Assertions.assertEquals(1, result0);
        log.info("insert>> " + result0);

        AuthVO auth = new AuthVO();
        auth.setUsername(member.getUsername());
        auth.setAuth("ROLE_MEMBER");
        int result = mapper.insertAuth(auth);
        log.info("insertAuth>> " + result);
        Assertions.assertEquals(0, result);
    }
}