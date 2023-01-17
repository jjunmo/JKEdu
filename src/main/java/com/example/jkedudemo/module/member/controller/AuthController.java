package com.example.jkedudemo.module.member.controller;


import com.example.jkedudemo.module.jwt.dto.RefreshApiResponseMessage;
import com.example.jkedudemo.module.jwt.dto.TokenDto;
import com.example.jkedudemo.module.jwt.service.JwtService;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberStatusOkResponseDto;
import com.example.jkedudemo.module.member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Operation(summary = "회원가입", description = "회원가입 진행")
    public HttpEntity<MemberStatusOkResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    /**
     * 로그인
     * @param requestDto email ,password
     * @return JWT Token ,token expire , Bearer
     */
    @PostMapping("/member/login")
    @Operation(summary = "회원 로그인", description = "로그인 및 토큰 발급")
    public HttpEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto,@RequestHeader("User-Agent") String userAgent) {
        return ResponseEntity.ok(authService.login(requestDto,userAgent));
    }

    /**
     * 로그아웃
     * @param userAgent 브라우저 환경
     * @return refreshToken 삭제
     */
    @PostMapping("/member/logout")
    @Operation(summary = "회원 로그아웃", description = "로그아웃 및 Refresh Token 삭제")
    public HttpEntity<MemberStatusOkResponseDto> logout(HttpServletRequest request, @RequestHeader("User-Agent") String userAgent) {
        return ResponseEntity.ok(jwtService.logout(request,userAgent));
    }

    /**
     * accessToken 재발급
     * @param bodyJson refreshToken
     * @return accessToken
     */
    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급", description = "Refresh Token DB에서 확인 후 재발급")
    public HttpEntity<RefreshApiResponseMessage> validateRefreshToken(@RequestBody HashMap<String, String> bodyJson) {

        log.info("refresh controller 실행");
        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));

        log.info("RefreshController - Refresh Token이 유효.");
        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
        return new ResponseEntity<>(refreshApiResponseMessage, HttpStatus.OK);
    }

    /**
     * 새로고침
     * @param userAgent 브라우저 환경
     * @return accessToken,refreshToken
     */
    @GetMapping("/refresh")
    @Operation(summary = "새로고침", description = "브라우저 환경 확인하여 Refresh 사용 X")
    public HttpEntity<TokenDto> refresh(@RequestHeader("User-Agent") String userAgent) {
        return ResponseEntity.ok(jwtService.refresh(userAgent));
    }

}