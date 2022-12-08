package com.example.jkedudemo.module.member.controller;


import com.example.jkedudemo.module.jwt.dto.RefreshApiResponseMessage;
import com.example.jkedudemo.module.jwt.dto.TokenDto;
import com.example.jkedudemo.module.jwt.service.JwtService;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.*;
import com.example.jkedudemo.module.member.service.AuthService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
//    private final RedisService redisService;

    /**
     * 회원가입
     * @param requestDto memberRequestDto
     * @return OK,FAIL
     */
    @PostMapping("/member/register")
    public HttpEntity<MemberStatusOkResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    /**
     * 로그인
     * @param requestDto email ,password
     * @return JWT Token ,token expire , Bearer
     */
    @PostMapping("/member/login")
    public HttpEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto,@RequestHeader("User-Agent") String userAgent) {
        return ResponseEntity.ok(authService.login(requestDto,userAgent));
    }

    @PostMapping("/refresh")
    public HttpEntity<RefreshApiResponseMessage> validateRefreshToken(@RequestBody HashMap<String, String> bodyJson) {

        log.info("refresh controller 실행");
        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));

        if (map.get("status").equals("402")) {
            log.info("RefreshController - Refresh Token이 만료.");
            RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
            return new ResponseEntity<>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
        }

        log.info("RefreshController - Refresh Token이 유효.");
        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
        return new ResponseEntity<>(refreshApiResponseMessage, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public HttpEntity<TokenDto> refresh(@RequestHeader("User-Agent") String userAgent) {
        return ResponseEntity.ok(jwtService.refresh(userAgent));
    }

}