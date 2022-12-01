package com.example.jkedudemo.module.member.controller;


import com.example.jkedudemo.module.jwt.dto.TokenDto;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.*;
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
    public HttpEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}