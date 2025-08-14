package org.danji.member.mapper;

import org.danji.member.domain.MemberVO;

import java.util.UUID;


public interface MemberMapper {

    MemberVO get(String username);

    int insert(MemberVO member); // 회원 정보 추가

    int update(MemberVO member); // 회원 정보 수정

    void deleteByUsername(String username);

    MemberVO findById(UUID memberId);

    String findPaymentPinById(UUID memberId);
}
