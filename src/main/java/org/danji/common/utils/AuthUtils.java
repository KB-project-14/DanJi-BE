package org.danji.common.utils;

import org.danji.security.account.domain.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public class AuthUtils {

    private AuthUtils() {
    }

    /**
     * 현재 인증된 사용자의 memberId(UUID)를 반환합니다.
     * - CustomUser인 경우 내부 MemberVO에서 꺼내고,
     * - 그렇지 않으면 principal이 String(sub)에 해당한다고 가정하고 UUID로 파싱합니다.
     */
    public static UUID getMemberId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }

        Object principal = auth.getPrincipal();
        // 1) CustomUser로 로그인한 경우
        if (principal instanceof CustomUser) {
            return ((CustomUser) principal).getMember().getMemberId();
        }

        // 2) JWT 필터가 subject(sub)를 그대로 principal(String)로 넣은 경우
        if (principal instanceof String) {
            try {
                return UUID.fromString((String) principal);
            } catch (IllegalArgumentException e) {
                // sub가 UUID 포맷이 아니면 null 리턴
            }
        }

        return null;
    }

    /**
     * 현재 인증된 사용자의 username(로그인 ID)을 반환합니다.
     * - UserDetails를 구현한 경우 getUsername()으로,
     * - 아니면 principal이 String이라면 그대로 반환합니다.
     */
    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        if (principal instanceof String) {
            return (String) principal;
        }
        return null;
    }
}
