package com.example.jkedudemo.module.member.controller;



import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.member.dto.request.AcademyMemberRequestDto;
import com.example.jkedudemo.module.member.dto.request.ChangePasswordRequestDto;
import com.example.jkedudemo.module.member.dto.request.DeleteMemberRequestDto;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;

import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;


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
    public HttpEntity<String> sendSMS(@RequestParam("phone") String phone,@RequestParam("phoneauthtype") PhoneAuthType phoneAuthType) {
        String result = memberService.certifiedPhone(phone,phoneAuthType);
        if (result.equals("OK")) {
            return ResponseEntity.ok("인증번호 발송");
        } else {
            return ResponseEntity.badRequest().body("시스템 운영자에 문의해주세요");
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
    public HttpEntity<String> sendSMSCheck(@RequestParam("phone") String phone ,@RequestParam("smscode") String smscode,@RequestParam("phoneauthtype")PhoneAuthType phoneAuthType){

        String result = memberService.certifiedPhoneCheck(phone,smscode,phoneAuthType);
        if(result.equals("OK")) {
            return ResponseEntity.ok("인증을 완료 했습니다.");
        }else {
            return ResponseEntity.badRequest().body("인증을 실패하였습니다.");
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
