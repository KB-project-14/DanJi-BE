package org.danji.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.common.utils.AuthUtils;
import org.danji.global.error.ErrorCode;
import org.danji.member.domain.MemberVO;
import org.danji.member.dto.*;
import org.danji.member.exception.MemberException;
import org.danji.member.mapper.MemberMapper;
import org.danji.wallet.dto.WalletCreateDTO;
import org.danji.wallet.enums.WalletType;
import org.danji.wallet.service.WalletService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {

    private final MemberMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;

    @Override
    public MemberDTO get(String username) {
        MemberVO vo = mapper.get(username);
        if (vo == null) {
            throw new MemberException(ErrorCode.USER_NOT_FOUND);
        }
        return MemberDTO.of(vo);
    }


    // 2) 가입
    @Transactional
    @Override
    public MemberDTO join(MemberJoinDTO dto) {
        if (mapper.get(dto.getUsername()) != null) {
            throw new MemberException(ErrorCode.DUPLICATED_USERNAME);
        }

        MemberVO member = dto.toVO();
        UUID memberId = UUID.randomUUID();

        member.setMemberId(memberId);
        member.setPassword(passwordEncoder.encode(dto.getPassword()));

        mapper.insert(member);

        walletService.createWallet(
                WalletCreateDTO.builder().memberId(memberId).walletType(WalletType.CASH).build());

        return get(member.getUsername());
    }


    // 3) 수정
    @Override
    public MemberDTO update(MemberUpdateDTO dto) {
        MemberVO member = validateMember(dto.getUsername(), dto.getPassword());

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

    // 결제 시 사용 할 서비스
    @Override
    public boolean checkPaymentPin(String paymentPin) {
        String username = AuthUtils.getUsername();
        MemberVO member = mapper.get(username);

        int result = 0;
        for (int i = 0; i < paymentPin.length(); i++) {
            result |= paymentPin.charAt(i) ^ member.getPaymentPin().charAt(i);
        }

        return result == 0;
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
