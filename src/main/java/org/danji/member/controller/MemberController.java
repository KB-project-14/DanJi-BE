package org.danji.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.member.dto.MemberDTO;
import org.danji.member.dto.MemberJoinDTO;
import org.danji.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService service;


    // @ModelAttribute 생략됨 -> formdata 형태로 요청이 올 때 이미지를 받기 위해
    @PostMapping()
    public ResponseEntity<MemberDTO> join(@RequestBody MemberJoinDTO member) {
        return ResponseEntity.ok(service.join(member));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        service.delete(username);
        return ResponseEntity.ok("삭제 완료");
    }


}
