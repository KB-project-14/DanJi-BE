package org.danji.member.controller;

import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.common.utils.AuthUtils;
import org.danji.global.common.ApiResponse;
import org.danji.member.dto.*;
import org.danji.member.service.MemberService;
import org.danji.security.util.JwtProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService service;
    private final JwtProcessor jwtProcessor;

    // @ModelAttribute 생략됨 -> formdata 형태로 요청이 올 때 이미지를 받기 위해
    @PostMapping()
    public ResponseEntity<ApiResponse<MemberDTO>> join(@RequestBody MemberJoinDTO member) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.join(member)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberDTO>> login(@RequestBody LoginDTO loginDTO) {
        MemberDTO memberDTO = service.login(loginDTO);
        String jwt = jwtProcessor.generateToken(memberDTO);;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(ApiResponse.success(memberDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberDTO>> get() {
        String username = AuthUtils.getUsername();
        MemberDTO dto = service.get(username);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> delete(@RequestBody MemberDeleteDTO member) {
        service.delete(member);
        return ResponseEntity.ok("삭제 완료");
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<MemberDTO>> update(@RequestBody MemberUpdateDTO dto) {
        MemberDTO updated = service.update(dto);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

}
