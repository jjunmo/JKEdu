package com.example.jkedudemo.module.member.controller;


import com.example.jkedudemo.module.common.enums.RoleType;
import com.example.jkedudemo.module.member.dto.TokenDto;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;
import com.example.jkedudemo.module.member.entity.Member;
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
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/member/register")
    public HttpEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {

        return ResponseEntity.ok(authService.signup(requestDto));
    }

    /**
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/member/login")
    public HttpEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}