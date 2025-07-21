package org.danji.member.service;

import org.danji.member.dto.ChangePasswordDTO;
import org.danji.member.dto.MemberDTO;
import org.danji.member.dto.MemberJoinDTO;
import org.danji.member.dto.MemberUpdateDTO;

public interface MemberService {

    boolean checkDuplicate(String username);

    MemberDTO get(String username);

    MemberDTO join(MemberJoinDTO member);

    MemberDTO update(MemberUpdateDTO member);

    void changePassword(ChangePasswordDTO changePassword);
}
