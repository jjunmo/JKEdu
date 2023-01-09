package com.example.jkedudemo.module.jwt;

import com.example.jkedudemo.module.handler.MyForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) return bearerToken.substring(7);

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveAccessToken(request);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    public Long getCurrentMemberId2(HttpServletRequest request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) throw new MyForbiddenException("Security Context에 인증 정보가 없습니다.");

        try{
            return Long.parseLong(authentication.getName());
        }catch (NumberFormatException e){
            return getTokenPayLoad(request);
        }
    }

    public Long getTokenPayLoad(HttpServletRequest request) {
        String token = resolveAccessToken(request);
        // 만료된 Access Token을 디코딩하여 Payload 값을 가져옴
        HashMap<String, String> payloadMap = JwtUtil.getPayloadByToken(Objects.requireNonNull(token));

        return Long.parseLong(Objects.requireNonNull(payloadMap).get("sub"));
    }

}