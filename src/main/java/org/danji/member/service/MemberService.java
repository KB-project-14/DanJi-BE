package org.danji.member.service;

import org.danji.member.dto.*;

public interface MemberService {

    MemberDTO get(String username);

    MemberDTO join(MemberJoinDTO member);

    MemberDTO update(MemberUpdateDTO member);

    void delete(MemberDeleteDTO member);

    MemberDTO login(LoginDTO loginDTO);

    boolean checkPaymentPin(String paymentPin);
}
