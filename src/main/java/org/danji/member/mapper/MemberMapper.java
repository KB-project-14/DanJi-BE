package org.danji.member.mapper;

import org.danji.auth.account.domain.AuthVO;
import org.danji.auth.account.domain.MemberVO;
import org.danji.member.dto.ChangePasswordDTO;


public interface MemberMapper {

    MemberVO get(String username);

    MemberVO findByUsername(String username); // id 중복 체크시 사용

    int insert(MemberVO member); // 회원 정보 추가

    int insertAuth(AuthVO auth); // 회원 권한 정보 추가

    int update(MemberVO member);

    int updatePassword(ChangePasswordDTO changePasswordDTO);

    void deleteByMemberId(Long memberId);
}
