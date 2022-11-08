package com.example.jkedudemo.module.member.controller;



import com.example.jkedudemo.module.common.enums.Phoneauth;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.member.dto.request.*;
import com.example.jkedudemo.module.member.dto.response.*;

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
    public HttpEntity<MemberStatusOkResponseDto> sendSMS(@RequestParam("phone") String phone, @RequestParam("phoneauth") Phoneauth phoneauth) {
        String result = memberService.certifiedPhone(phone, phoneauth);
        if (result.equals("OK")) {
            return ResponseEntity.ok(MemberStatusOkResponseDto.statusOk());
        } else {
            throw new MyInternalServerException("관리자에게 문의해주세요.");
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
    public HttpEntity<MemberStatusOkResponseDto> sendSMSCheck(@RequestParam("phone") String phone , @RequestParam("smscode") String smscode, @RequestParam("phoneauth") Phoneauth phoneauth){
        String result = memberService.certifiedPhoneCheck(phone,smscode, phoneauth);
        if(result.equals("OK")) {
            return ResponseEntity.ok(MemberStatusOkResponseDto.statusOk());
        }else {
            throw new MyInternalServerException("인증실패");
        }
    }

    /**
     *
     * @return
     */
    @GetMapping("/myinfo")
    public HttpEntity<MemberMyInfoResponseDto> MemberInfo(){
        return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }


    /**
     *
     * @param request
     * @return
     */
    @PutMapping("/myinfo")
    public HttpEntity<MemberStatusOkResponseDto> setPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping
    public HttpEntity<MemberStatusOkResponseDto> setMemberDelete(@RequestBody DeleteMemberRequestDto request) {
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
    public HttpEntity<MemberIdFindResopnseDto> getMemberEmail(String phone , String smscode, Phoneauth phoneauth){
        return ResponseEntity.ok(memberService.getMemberEmail(phone,smscode,phoneauth));
    }

    //  TODO : 비밀번호 찾기 기존 방식 인코딩된 비밀번호 해결못함 / 이메일로 임시 비밀번호 발급방식 적용검토
//
//    @PostMapping("/check")
//    public HttpEntity<TokenDto> getPassword(@RequestBody FindPasswordRequestDto request){
//        return ResponseEntity.ok();
//    }

    @PutMapping("/check")
    public HttpEntity<MemberMyInfoResponseDto> getNewPassword(String newPassword){
        throw new MyInternalServerException("aaa");
    }


    /**
     * 이메일 중복확인
     * @param email
     * @return
     */
    @GetMapping("/excheck")
    public HttpEntity<MemberStatusOkResponseDto> exEmail(String email){
        String result = memberService.exEmailCheck(email);
        if(result.equals("OK")) {
            return ResponseEntity.ok(MemberStatusOkResponseDto.statusOk());
        }else {
            throw new MyInternalServerException("이미 존재하는 아이디입니다.");
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
    public HttpEntity<AcademyMemberResponseDto> academyMember(@RequestBody AcademyMemberRequestDto request){
        return ResponseEntity.ok(memberService.setAcademyMember(request));
    }

    @GetMapping("/name")
    public HttpEntity<MemberNameResopnseDto> setMemberName() {
        return ResponseEntity.ok(memberService.memberName());
    }



}
