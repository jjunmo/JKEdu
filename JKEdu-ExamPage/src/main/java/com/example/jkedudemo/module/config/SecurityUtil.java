package com.example.jkedudemo.module.config;

import com.example.jkedudemo.module.handler.MyForbiddenException;
import com.example.jkedudemo.module.handler.MyUnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName()== null) throw new MyForbiddenException("로그인 유저 정보가 없습니다");

        try{
            log.info(authentication.getName());
            return Long.parseLong(authentication.getName());
        }catch (NumberFormatException e){
            throw new MyUnAuthorizedException("JWT Expired");
        }

    }

}
