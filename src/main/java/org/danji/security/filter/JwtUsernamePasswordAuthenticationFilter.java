package org.danji.security.filter;


import lombok.extern.slf4j.Slf4j;
import org.danji.member.dto.LoginDTO;
import org.danji.security.handler.LoginFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public JwtUsernamePasswordAuthenticationFilter(
        AuthenticationManager authenticationManager,
      LoginFailureHandler loginFailureHandler) {

        super(authenticationManager);
        setFilterProcessesUrl("/api/auth/login");
       setAuthenticationFailureHandler(loginFailureHandler);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        LoginDTO login = LoginDTO.of(request);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());


        return getAuthenticationManager().authenticate(authenticationToken);
    }

}
