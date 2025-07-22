package org.danji.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.auth.account.domain.AuthVO;
import org.danji.auth.account.domain.MemberVO;
import org.danji.member.dto.*;
import org.danji.member.exception.PasswordMissmatchException;
import org.danji.member.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapper mapper;

    // 아이디 중복 검사
    @Override
    public boolean checkDuplicate(String username) {
        return mapper.findByUsername(username) != null;
    }

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
    public MemberDTO join(MemberJoinDTO dto) {
        MemberVO member = dto.toVO();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        mapper.insert(member);

        AuthVO auth = new AuthVO();
        auth.setUsername(member.getUsername());
        auth.setAuth("ROLE_MEMBER");
        mapper.insertAuth(auth);

        return get(member.getUsername());
    }

    // 정보수정
    @Override
    public MemberDTO update(MemberUpdateDTO member) {
        MemberVO vo = mapper.get(member.getUsername());
        if (!passwordEncoder.matches(member.getPassword(), vo.getPassword())) {
            throw new PasswordMissmatchException();
        }

        mapper.update(member.toVO());
        return get(member.getUsername());
    }

    // 석제
    @Transactional
    @Override
    public void delete(MemberDeleteDTO dto) {
        log.info("회원 탈퇴 요청: {}", dto.getMemberId());
        mapper.deleteByMemberId(dto.getMemberId());
    }

    // 비밀번호 변경
    @Override
    public void changePassword(ChangePasswordDTO changePassword) {
        MemberVO member = mapper.get(changePassword.getUsername());
        if (!passwordEncoder.matches(changePassword.getOldPassword(), member.getPassword())) {
            throw new PasswordMissmatchException();
        }

        changePassword.setNewPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        mapper.updatePassword(changePassword);
    }

}
