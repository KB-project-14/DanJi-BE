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
//서비스에 가입이랑 탈퇴에도 엔코드 어쩌구 하기...

    private final MemberMapper mapper;
    private final PasswordEncoder passwordEncoder;

    // 사용자 조회
    @Override
    public MemberDTO get(String username) {
        MemberVO member = Optional.ofNullable(mapper.get(username))
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        return MemberDTO.of(member);
    }


    @Transactional
    @Override
    public MemberDTO join(MemberJoinDTO memberJoinDTO) {
        String encodedPassword = passwordEncoder.encode(memberJoinDTO.getPassword());

        MemberVO member = MemberVO.builder()
                .memberId(UUID.randomUUID())
                .username(memberJoinDTO.getUsername())
                .password(encodedPassword)
                .name(memberJoinDTO.getName())
                .role(memberJoinDTO.getRole())
                .build();
        mapper.insert(member);
        return get(member.getUsername());
    }


    @Override
    public MemberDTO update(MemberUpdateDTO dto) {
        MemberVO existing = Optional.ofNullable(mapper.get(dto.getUsername()))
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

           if (!passwordEncoder.matches(dto.getPassword(), existing.getPassword())) {
            throw new PasswordMissmatchException();
        }

        existing.setName(dto.getName());

        mapper.update(existing);

        MemberVO updated = mapper.get(existing.getUsername());
        return MemberDTO.of(updated);
    }

// 삭제
@Override
@Transactional
public void delete(MemberDeleteDTO dto) {
    // 1) 입력값 꺼내기
    String username     = dto.getUsername();
    String rawPassword  = dto.getPassword();

    // 2) DB에서 사용자 조회
    MemberVO member = mapper.get(username);
    if (member == null) {
        throw new NoSuchElementException("존재하지 않는 회원입니다.");
    }

    // 3) 비밀번호 검증 (평문 vs 해시 비교)
    if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
        throw new PasswordMissmatchException();  // 또는 IllegalArgumentException
    }

    // 4) 본인 확인 완료 → 탈퇴 처리
    mapper.deleteByUsername(username);
}

}
