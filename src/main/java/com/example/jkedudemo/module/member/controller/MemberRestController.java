package com.example.jkedudemo.module.member.controller;



import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.member.dto.request.AcademyMemberRequestDto;
import com.example.jkedudemo.module.member.dto.request.ChangePasswordRequestDto;
import com.example.jkedudemo.module.member.dto.request.DeleteMemberRequestDto;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.HttpStatusResopnse;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;

import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberRestController {

    private final MemberService memberService;

    /**
     *
     * @param phone
     * @param phoneAuthType
     * @return
     */
    @GetMapping("/cert")
    public HttpEntity<HttpStatusResopnse> sendSMS(@RequestParam("phone") String phone, @RequestParam("phoneauthtype") PhoneAuthType phoneAuthType) {
        String result = memberService.certifiedPhone(phone,phoneAuthType);
        HttpStatusResopnse httpStatusResopnse = new HttpStatusResopnse();
        if (result.equals("OK")) {
            httpStatusResopnse.setStatus("200");
            httpStatusResopnse.setMessage("메세지 발송 성공");
            return ResponseEntity.ok(httpStatusResopnse);
        } else {
            httpStatusResopnse.setStatus("400");
            httpStatusResopnse.setMessage("관리자에게 문의 해주세요");
            return ResponseEntity.badRequest().body(httpStatusResopnse);
        }
    }

    /**
     *
     * @return
     */
    @PostMapping("/testmember")
    public HttpEntity<Member> testMember(){
        return ResponseEntity.ok(memberService.setTestMember());

    }

    /**
     *
     * @param phone
     * @param smscode
     * @param phoneAuthType
     * @return
     */
    @GetMapping("/cert/ex")
    public HttpEntity<Object> sendSMSCheck(@RequestParam("phone") String phone ,@RequestParam("smscode") String smscode,@RequestParam("phoneauthtype")PhoneAuthType phoneAuthType){
        String result = memberService.certifiedPhoneCheck(phone,smscode,phoneAuthType);
        HttpStatusResopnse httpStatusResopnse = new HttpStatusResopnse();
        if(result.equals("OK")) {
            httpStatusResopnse.setStatus("200");
            httpStatusResopnse.setMessage("인증 성공");
            return ResponseEntity.ok(httpStatusResopnse);
        }else {
            httpStatusResopnse.setStatus("400");
            httpStatusResopnse.setMessage("인증 실패");
            return ResponseEntity.badRequest().body(httpStatusResopnse);
        }
    }

    /**
     *
     * @return
     */
    @GetMapping("/myinfo")
    public HttpEntity<MemberResponseDto> MemberInfo(){
        return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }


    /**
     *
     * @param request
     * @return
     */
    @PutMapping
    public HttpEntity<MemberResponseDto> setPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping
    public HttpEntity<MemberResponseDto> setMemberDelete(@RequestBody DeleteMemberRequestDto request) {
        return ResponseEntity.ok(memberService.deleteMember(request.getPassword()));
    }

    /**
     *
     * @param phone
     * @return
     */
    // TODO:휴대폰 인증여부 같이 확인 필요
    @GetMapping("/check")
    public HttpEntity<MemberResponseDto> getMemberEmail(String phone){
        return ResponseEntity.ok(memberService.getMemberEmail(phone));
    }

    //TODO : 비밀번호 찾기

    @GetMapping
    public HttpEntity<MemberResponseDto> getPassword(MemberRequestDto request){
        return ResponseEntity.ok(memberService.getPassword(request.getEmail(), request.getPhone()));
    }

    @GetMapping("/excheck")
    public HttpEntity<Object> exEmail(String email){
        String result = memberService.exEmailCheck(email);
        HttpStatusResopnse httpStatusResopnse = new HttpStatusResopnse();
        if(result.equals("OK")) {
            httpStatusResopnse.setStatus("200");
            httpStatusResopnse.setMessage("회원가입이 가능한 아이디 입니다.");
            return ResponseEntity.ok(httpStatusResopnse);
        }else {
            httpStatusResopnse.setStatus("400");
            httpStatusResopnse.setMessage("이미 존재하는 아이디 입니다.");
            return ResponseEntity.badRequest().body(httpStatusResopnse);
        }

    }

    //비밀번호 찾기 이후 비밀번호 재설정
//    @PostMapping("/find/password/change")
//    public HttpEntity<MemberResponseDto> getPasswordChange(@RequestBody ChangePasswordRequestDto){
//
//    }

    /**
     *
     * @param request 이름 , 생일 , 연락처
     * @return AcademyId , ROLE_ACADEMY_STUDENT 멤버 생성
     */

    @PostMapping("/academy/exam")
    public HttpEntity<MemberResponseDto> academyMember(@RequestBody AcademyMemberRequestDto request){
        return ResponseEntity.ok(memberService.setAcademyMember(request));
    }


}
