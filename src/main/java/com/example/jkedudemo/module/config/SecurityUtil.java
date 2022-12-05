package com.example.jkedudemo.module.config;

import com.example.jkedudemo.module.handler.MyForbiddenException;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() { }

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new MyInternalServerException("Security Context에 인증 정보가 없습니다.");
        }

        try{
            return Long.parseLong(authentication.getName());
        }catch (NumberFormatException e){
            throw new MyForbiddenException("JWT Expired");
        }
    }
}
