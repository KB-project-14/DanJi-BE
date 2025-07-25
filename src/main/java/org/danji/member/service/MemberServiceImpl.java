package org.danji.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.member.domain.MemberVO;
import org.danji.member.dto.*;
import org.danji.member.exception.PasswordMissmatchException;
import org.danji.member.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(memberJoinDTO.getPassword());


        MemberVO member = MemberVO.builder()
                .memberId(UUID.randomUUID())
                .username(memberJoinDTO.getUsername())
                .password(memberJoinDTO.getPassword())
                .name(memberJoinDTO.getName())
                .role(memberJoinDTO.getRole())
                .build();
        mapper.insert(member);
        return get(member.getUsername());
    }

    // 정보 수정
    @Override
    public MemberDTO update(MemberUpdateDTO member) {
        MemberVO vo = mapper.get(member.getUsername());
        if (!member.getPassword().equals(vo.getPassword())) {
            throw new PasswordMissmatchException();
        }

        mapper.update(member.toVO());
        return get(member.getUsername());
    }

    // 삭제
    @Override
    public void delete(MemberDeleteDTO dto) {
        String username = dto.getUsername();
        String inputPassword = dto.getPassword();

        MemberVO member = mapper.get(username);

        if (member == null) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        } // DB에 있는 회원인지 조회

        if (!dto.getPassword().equals(member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } // 비밀번호 확인 조회
        mapper.deleteByUsername(username);
    }
}
