package org.danji.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.member.domain.MemberVO;
import org.danji.member.dto.*;
import org.danji.member.exception.PasswordMissmatchException;
import org.danji.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper mapper;


    // 아이디 중복 검사
//    @Override
//    public boolean checkDuplicate(String username) {
//        return mapper.findByUsername(username) != null;
//    }

    // 사용자 조회
    @Override
    public MemberDTO get(String username) {
        MemberVO member = Optional.ofNullable(mapper.get(username))
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        return MemberDTO.of(member);
    }

    // 가입
    @Transactional
    @Override
    public MemberDTO join(MemberJoinDTO memberJoinDTO) {
        MemberVO member = MemberVO.builder()
                .memberId(UUID.randomUUID())
                .username(memberJoinDTO.getUsername())
                .password(memberJoinDTO.getPassword())
                .name(memberJoinDTO.getName())
                .role(memberJoinDTO.getRole())
                .build();
        mapper.insert(member);
//        AuthVO auth = new AuthVO();
//        auth.setUsername(member.getUsername());
//        auth.setAuth("USER");
//        mapper.insertAuth(auth);
        return get(member.getUsername());
    }

    @Override
    public MemberDTO update(MemberUpdateDTO member) {
        MemberVO vo = mapper.get(member.getUsername());
        if (!member.getPassword().equals(vo.getPassword())) {
            throw new PasswordMissmatchException();
        }

        mapper.update(member.toVO());
        return get(member.getUsername());
    }

    @Override
    public void delete(String username) {
        mapper.deleteByUsername(username);
    }

}
