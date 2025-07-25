package org.danji.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthUtils {

    public static UUID getMemberId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof String) {
            return UUID.fromString(
                    (String) authentication.getPrincipal());
        } else if (authentication.getPrincipal() instanceof UUID) {
            return (UUID) authentication.getPrincipal();
        }
        return null;
    }
}
