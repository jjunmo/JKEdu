package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.member.dto.request.*;
import com.example.jkedudemo.module.member.dto.response.*;

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
     * 문자발송
     *
     * @param phone     연락처
     * @param phoneauth JOIN , ID ,PW
     * @return SMS 발송
     */
    @GetMapping("/cert")
    public HttpEntity<MemberStatusOkResponseDto> sendSMS(@RequestParam("phone") String phone, @RequestParam("phoneauth") PhoneAuth phoneauth) {
        String result = memberService.certifiedPhone(phone, phoneauth);
        if (result.equals("OK")) {
            return ResponseEntity.ok(MemberStatusOkResponseDto.statusOk());
        } else {
            throw new MyInternalServerException("관리자에게 문의해주세요.");
        }
    }

    /**
     * 인증하기
     *
     * @param phone     연락처
     * @param smscode   인증번호
     * @param phoneauth JOIN ,ID ,PW
     * @return 인증여부
     */
    @GetMapping("/cert/ex")
    public HttpEntity<MemberStatusOkResponseDto> sendSMSCheck(@RequestParam("phone") String phone, @RequestParam("smscode") String smscode, @RequestParam("phoneauth") PhoneAuth phoneauth) {
        String result = memberService.certifiedPhoneCheck(phone, smscode, phoneauth);
        if (result.equals("OK")) {
            return ResponseEntity.ok(MemberStatusOkResponseDto.statusOk());
        } else {
            throw new MyInternalServerException("인증실패");
        }
    }

    /**
     * 내 정보
     *
     * @return 정보 확인
     */
    @GetMapping("/myinfo")
    public HttpEntity<MemberMyInfoResponseDto> MemberInfo() {
        return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    /**
     * 비밀번호 변경
     *
     * @param request exPassword , newPassword
     * @return 비밀번호 변경
     */
    @PutMapping("/myinfo")
    public HttpEntity<MemberStatusOkResponseDto> setPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    //TODO: 삭제 완료 이후 토큰으로 가입됨.나중에 확인

    /**
     * 계정 삭제
     *
     * @param request password
     * @return Status.RED
     */
    @PostMapping("/myinfo")
    public HttpEntity<MemberStatusOkResponseDto> setMemberDelete(@RequestBody DeleteMemberRequestDto request) {
        return ResponseEntity.ok(memberService.deleteMember(request.getPassword()));
    }

    /**
     * 아이디 찾기
     *
     * @param phone     연락처
     * @param smscode   인증번호
     * @param phoneauth 인증확인
     * @return email
     */
    @GetMapping("/check")
    public HttpEntity<MemberIdFindResopnseDto> getMemberEmail(String phone, String smscode, PhoneAuth phoneauth) {
        return ResponseEntity.ok(memberService.getMemberEmail(phone, smscode, phoneauth));
    }

    /**
     * 비밀번호 찾기
     *
     * @param phone     연락처
     * @param smscode   인증번호
     * @param phoneauth Phoneauth Y,N
     * @return 임시비밀번호 문자로 발송
     */
    @PostMapping("/check")
    public HttpEntity<MemberStatusOkResponseDto> getNewPassword(String email, String phone, String smscode, PhoneAuth phoneauth) {
        return ResponseEntity.ok(memberService.getNewPassword(email, phone, smscode, phoneauth));
    }

    /**
     * 아이디 중복체크
     *
     * @param email 이메일
     * @return OK, FAIL
     */
    @GetMapping("/excheck")
    public HttpEntity<MemberStatusOkResponseDto> exEmail(String email) {
        String result = memberService.exEmailCheck(email);

        if (result.equals("OK")) return ResponseEntity.ok(MemberStatusOkResponseDto.statusOk());

        else throw new MyInternalServerException("이미 존재하는 아이디입니다.");
    }

    /**
     * @return testCount
     */
    @GetMapping("/exam")
    public HttpEntity<TestCountResopnseDto> getTestCount() {
        return ResponseEntity.ok(memberService.getTestCount());
    }

    /**
     * 학원학생 시험 응시
     *
     * @param request name ,birth , phone
     * @return OK, FAIL  member.id
     */
    @PostMapping("/academy/exam")
    public HttpEntity<AcademyMemberResponseDto> academyMember(@RequestBody AcademyMemberRequestDto request) {
        return ResponseEntity.ok(memberService.setAcademyMember(request));
    }
}