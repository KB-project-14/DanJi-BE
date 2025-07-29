package org.danji.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.global.error.ErrorCode;
import org.danji.member.domain.MemberVO;
import org.danji.member.dto.*;
import org.danji.member.exception.MemberException;
import org.danji.member.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {

    private final MemberMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO get(String username) {
        MemberVO member = Optional.ofNullable(mapper.get(username))
                .orElseThrow(() -> new MemberException(ErrorCode.USER_NOT_FOUND));
        return MemberDTO.of(member);
    }


    // 2) 가입
    @Transactional
    @Override
    public MemberDTO join(MemberJoinDTO dto) {
        if (mapper.get(dto.getUsername()) != null) {
            throw new MemberException(ErrorCode.DUPLICATED_USERNAME);
        }

        String encoded = passwordEncoder.encode(dto.getPassword());
        MemberVO member = MemberVO.builder()
                .memberId(UUID.randomUUID())
                .username(dto.getUsername())
                .password(encoded)
                .name(dto.getName())
                .role(dto.getRole())
                .build();

        mapper.insert(member);
        return get(member.getUsername());
    }


    // 3) 수정
    @Override
    public MemberDTO update(MemberUpdateDTO dto) {
        MemberVO member = validateMember(dto.getUsername(), dto.getPassword());

        if (mapper.get(dto.getUsername()) != null) {
            throw new MemberException(ErrorCode.DUPLICATED_USERNAME);
        }

        member.setName(dto.getName());

        mapper.update(member);

        return MemberDTO.of(mapper.get(member.getUsername()));

    }

    // 4) 삭제
    @Override
    @Transactional
    public void delete(MemberDeleteDTO dto) {
        MemberVO member = validateMember(dto.getUsername(), dto.getPassword());

        mapper.deleteByUsername(member.getUsername());
    }


    @Override
    public MemberDTO login(LoginDTO loginDTO) {
        MemberVO vo = validateMember(loginDTO.getUsername(), loginDTO.getPassword());

        return MemberDTO.of(vo);
    }


    private MemberVO validateMember(String username, String rawPassword) {
        MemberVO member = mapper.get(username);
        if (member == null) {
            throw new MemberException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD);
        }

        return member;
    }
}
