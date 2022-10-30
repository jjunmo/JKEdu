package com.example.jkedudemo.module.member.controller;



import com.example.jkedudemo.module.member.dto.request.ChangePasswordRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;
import com.example.jkedudemo.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/sendSMS")
    public HttpEntity<String> sendSMS(String phoneNumber) {
        String result = memberService.certifiedPhoneNumber(phoneNumber);
        if (result.equals("OK")) {
            return ResponseEntity.ok("인증을 성공했습니다.");
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }


    /**
     *  비밀번호 변경
     * @param request 기존 비밀번호 , 새 비밀번호
     * @return 비밀번호 변경
     */
    @PostMapping("/password")
    public HttpEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        // TODO: 비밀번호 맞는지 체크
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

}
