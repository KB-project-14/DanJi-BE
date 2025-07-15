package org.scoula.security.account.mapper;

import org.scoula.security.account.domain.MemberVO;

public interface UserDetailsMapper {

    //로그인처리할 때 db연동할 기능을 정의하면 됨.!
    //username(id)주소 회원정보를 검색하자.!
    MemberVO get(String username);
}
