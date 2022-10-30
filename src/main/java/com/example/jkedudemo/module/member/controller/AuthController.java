package com.example.jkedudemo.module.member.controller;


import com.example.jkedudemo.module.member.dto.TokenDto;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;
import com.example.jkedudemo.module.member.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     *  회원가입
     * @param requestDto 회원가입 데이터
     * @return 회원가입 성공.
     */
    @PostMapping("/user/register")
    public HttpEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        // TODO:회원가입 requestDto 데이터 체크
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    /**
     *  로그인
     * @param requestDto 사용자 정보
     * @return 토큰 값 , 토큰 유효시간
     */
    @PostMapping("/user/login")
    public HttpEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}