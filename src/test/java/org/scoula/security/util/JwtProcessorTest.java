package org.scoula.security.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j2
class JwtProcessorTest {
    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    void generateToken() {
        String username = "user0";
        String token = jwtProcessor.generateToken(username);
        log.info(token);
        assertNotNull(token);
    }

    //eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTc1MDc0MDUwNiwiZXhwIjoxNzUwNzQwODA2fQ.424JPChxXjVzrEElajoWnFlZgRA2Ak9Zg-i-NunmJ5DxTEi2Pq-0MxxGhmrE8Jpe

    @Test
    void validateToken() {
        // 5분경과후테스트
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTc1MDgxMzQ3OCwiZXhwIjoxNzUwODEzNTk4fQ.N6Z5uO-czbD1XhyNMi9r0dwLrAowfAaydmGHDOPThcUYkjB-RduuevnbPfqs-A1C";

        boolean isValid = jwtProcessor.validateToken(token); // 5분경과후면예외발생
        log.info(isValid);
        assertTrue(isValid);    // 5분전이면true
    }
}