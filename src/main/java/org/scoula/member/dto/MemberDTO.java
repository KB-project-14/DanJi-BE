package org.scoula.member.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.security.account.domain.AuthVO;
import org.scoula.security.account.domain.MemberVO;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDTO {

    private String username;
    private String email;
    private Date regDate;
    private Date updateDate;

    private MultipartFile avatar;
    private List<String> authList;

    public static MemberDTO of(MemberVO m) {
        return MemberDTO.builder()
            .username(m.getUsername())
            .email(m.getEmail())
            .regDate(m.getRegDate())
            .updateDate(m.getUpdateDate())
            .authList(m.getAuthList().stream().map(AuthVO::getAuth).toList())
            .build();
    }

    public MemberVO toVO() {
        return MemberVO.builder()
            .username(username)
            .email(email)
            .regDate(regDate)
            .updateDate(updateDate)
            .build();
    }
}
