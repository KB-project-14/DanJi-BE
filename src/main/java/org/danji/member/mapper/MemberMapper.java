package org.danji.member.mapper;

import org.danji.member.domain.MemberVO;


public interface MemberMapper {

    MemberVO get(String username);

    int insert(MemberVO member); // 회원 정보 추가

    int update(MemberVO member); // 회원 정보 수정

    void deleteByUsername(String username);

}
