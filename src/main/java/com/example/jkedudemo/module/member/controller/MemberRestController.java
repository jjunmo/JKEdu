package com.example.jkedudemo.module.member.controller;



import com.example.jkedudemo.module.member.dto.request.ChangePasswordRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;

import com.example.jkedudemo.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberRestController {

    private final MemberService memberService;

    /**
     * 인증번호 발송
     * @param phoneNumber 수신자 번호
     * @return 인증번호 발송여부
     */
    @GetMapping("/sendSMS")
    public HttpEntity<String> sendSMS(String phoneNumber) {
        String result = memberService.certifiedPhoneNumber(phoneNumber);
        if (result.equals("OK")) {
            return ResponseEntity.ok("인증번호 발송");
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     *
     * @param phoneNumber 수신자 번호
     * @param code 인증번호
     * @return 인증결과
     */
    @GetMapping("/sendSMS/check")
    public HttpEntity<String> sendSMSCheck(String phoneNumber , String code){
        String result = memberService.certifiedPhoneNumberCheck(phoneNumber,code);
        if(result.equals("OK")) {
            return ResponseEntity.ok("인증을 완료 했습니다.");
        }else {
            return ResponseEntity.badRequest().body("인증을 실패하였습니다.");
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
