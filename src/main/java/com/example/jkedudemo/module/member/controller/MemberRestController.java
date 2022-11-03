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


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberRestController {

    private final MemberService memberService;

    /**
     *
     * @param phoneNumber 수신자 번호
     * @param phoneAuthType JOIN , ID_FIND , PW_FIND
     * @return
     */
    @GetMapping("/sendSMS")
    public HttpEntity<String> sendSMS(String phoneNumber, PhoneAuthType phoneAuthType) {
        String result = memberService.certifiedPhoneNumber(phoneNumber,phoneAuthType);
        if (result.equals("OK")) {
            return ResponseEntity.ok("인증번호 발송");
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/testMember")
    public HttpEntity<Member> testMember(){
        return ResponseEntity.ok(memberService.setTestMember());

    }

    /**
     *
     * @param phoneNumber 수신자 번호
     * @param code 인증번호
     * @return 인증결과
     */
    @GetMapping("/sendSMS/check")
    public HttpEntity<String> sendSMSCheck(String phoneNumber , String code,PhoneAuthType phoneAuthType){

        String result = memberService.certifiedPhoneNumberCheck(phoneNumber,code,phoneAuthType);
        if(result.equals("OK")) {
            return ResponseEntity.ok("인증을 완료 했습니다.");
        }else {
            return ResponseEntity.badRequest().body("인증을 실패하였습니다.");
        }
    }

    /**
     * 내 정보
     * @return 내 정보
     */
    @GetMapping("/info")
    public HttpEntity<MemberResponseDto> MemberInfo(){
        return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }


    /**
     *  비밀번호 변경
     * @param request 기존 비밀번호 , 새 비밀번호
     * @return 비밀번호 변경
     */
    @PutMapping("/password")
    public HttpEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    /**
     * 계정 삭제
     * @param request 현재 로그인된 계정의 비밀번호
     * @return 계정 삭제로 상태변경
     */
    @PostMapping("/delete")
    public HttpEntity<MemberResponseDto> setMemberDelete(@RequestBody DeleteMemberRequestDto request) {
        return ResponseEntity.ok(memberService.deleteMember(request.getMemberPassword()));
    }

    /**
     *
     * @param phoneNumber 사용자 휴대폰 번호
     * @return 해당 사용자의 Email
     */
    @GetMapping("/find/email")
    public HttpEntity<MemberResponseDto> getMemberEmail(String phoneNumber){
        return ResponseEntity.ok(memberService.getMemberEmail(phoneNumber));
    }

    @GetMapping("/find/password")
    public HttpEntity<MemberResponseDto> getMemberPassword(@RequestParam MemberRequestDto request){
        return ResponseEntity.ok(memberService.getMemberPassword(request.getEmail(), request.getPhoneNumber()));
    }

    //비밀번호 찾기 이후 비밀번호 재설정
//    @PostMapping("/find/password/change")
//    public HttpEntity<MemberResponseDto> getMemberPasswordChange(@RequestBody ChangePasswordRequestDto){
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
