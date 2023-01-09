package com.example.jkedudemo.module.config;

import com.example.jkedudemo.module.handler.MyForbiddenException;
import com.example.jkedudemo.module.handler.MyUnAuthorizedException;
import com.example.jkedudemo.module.jwt.JwtFilter;
import com.example.jkedudemo.module.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName()== null) throw new MyForbiddenException("Security Context에 인증 정보가 없습니다.");

        try{
            log.info(authentication.getName());
            return Long.parseLong(authentication.getName());
        }catch (NumberFormatException e){
            throw new MyUnAuthorizedException("JWT Expired");
        }

    }

}
