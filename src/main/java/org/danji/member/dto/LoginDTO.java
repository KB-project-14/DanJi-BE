package org.danji.member.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danji.global.error.ErrorCode;
import org.danji.member.exception.MemberException;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String username;
    private String password;

    public static LoginDTO of(HttpServletRequest request) {
        try {
            return OBJECT_MAPPER.readValue(request.getInputStream(), LoginDTO.class);
        } catch (Exception e) {
            throw new MemberException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
