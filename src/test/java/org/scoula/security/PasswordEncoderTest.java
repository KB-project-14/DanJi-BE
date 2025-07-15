package org.scoula.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RootConfig.class,
        SecurityConfig.class
})
@Log4j2
public class PasswordEncoderTest {

    //싱글톤으로 만든 암호화해주는 객체를 주입
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


}
