package com.example.jkedudemo.module.jwt.service;

import com.example.jkedudemo.module.jwt.TokenProvider;
import com.example.jkedudemo.module.jwt.entity.RefreshToken;
import com.example.jkedudemo.module.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;


    public Map<String, String> validateRefreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenOptional =refreshTokenRepository.findByRefreshToken(refreshToken);
        if(refreshTokenOptional.isPresent()) {
            RefreshToken refresh = refreshTokenOptional.get();
            String createdAccessToken = tokenProvider.validateRefreshToken(refresh);
            return createRefreshJson(createdAccessToken);
        }
        return null;
    }

    public Map<String, String> createRefreshJson(String createdAccessToken){

        Map<String, String> map = new HashMap<>();
        if(createdAccessToken == null){
            map.put("errortype", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");

            return map;
        }
        //기존에 존재하는 accessToken 제거

        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);

        return map;


    }
}
