package com.example.jkedudemo.module.member.controller;



import com.example.jkedudemo.module.common.enums.Phoneauth;
import com.example.jkedudemo.module.member.dto.TokenDto;
import com.example.jkedudemo.module.member.dto.request.*;
import com.example.jkedudemo.module.member.dto.response.HttpStatusResopnse;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;

import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.service.AuthService;
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

    private final AuthService authService;
    private final MemberService memberService;

    /**
     *
     * @param phone
     * @param phoneauth
     * @return
     */
    @GetMapping("/cert")
    public HttpEntity<HttpStatusResopnse> sendSMS(@RequestParam("phone") String phone, @RequestParam("phoneauth") Phoneauth phoneauth) {
        String result = memberService.certifiedPhone(phone, phoneauth);
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
     * @param phoneauth
     * @return
     */
    @GetMapping("/cert/ex")
    public HttpEntity<Object> sendSMSCheck(@RequestParam("phone") String phone ,@RequestParam("smscode") String smscode,@RequestParam("phoneauth") Phoneauth phoneauth){
        String result = memberService.certifiedPhoneCheck(phone,smscode, phoneauth);
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
     * @param smscode
     * @param phoneauth
     * @return
     */
    @GetMapping("/check")
    public HttpEntity<MemberResponseDto> getMemberEmail(String phone , String smscode,Phoneauth phoneauth){
        return ResponseEntity.ok(memberService.getMemberEmail(phone,smscode,phoneauth));
    }

    //TODO : 비밀번호 찾기

    @PostMapping("/check")
    public HttpEntity<TokenDto> getPassword(@RequestBody FindPasswordRequestDto request){
        return ResponseEntity.ok(authService.getPassword(request));
    }

    @PutMapping("/check")
    public HttpEntity<MemberResponseDto> getNewPassword(String newPassword){
        throw new RuntimeException("aaa");
    }


    /**
     * 이메일 중복확인
     * @param email
     * @return
     */
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
