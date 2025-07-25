package org.danji.member.service;

import org.danji.member.dto.*;

public interface MemberService {

//    boolean checkDuplicate(String username);

    MemberDTO get(String username);

    MemberDTO join(MemberJoinDTO member);

}
