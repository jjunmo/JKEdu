package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.member.dto.request.*;
import com.example.jkedudemo.module.member.dto.response.*;

import com.example.jkedudemo.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @Operation(summary = "휴대폰 인증요청",description = "회원가입 , ID 찾기 ,PW 찾기")
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
    @Operation(summary = "휴대폰 인증확인", description = "인증여부 확인")
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
    @Operation(summary = "마이페이지", description = "로그인 후 마이페이지")
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
    @Operation(summary = "비밀번호 변경", description = "내정보에서 비밀번호 변경")
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
    @Operation(summary = "회원삭제", description = "비밀번호 확인 후 회원탈퇴")
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
    @Operation(summary = "ID 찾기", description = "인증여부 확인")
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
    @Operation(summary = "비밀번호 찾기", description = "임시 비밀번호 발급")
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
    @Operation(summary = "아이디 중복확인", description = "로그인시 아이디 중복 확인")
    public HttpEntity<MemberStatusOkResponseDto> exEmail(String email) {
        String result = memberService.exEmailCheck(email);

        if (result.equals("OK")) return ResponseEntity.ok(MemberStatusOkResponseDto.statusOk());

        else throw new MyInternalServerException("이미 존재하는 아이디입니다.");
    }

    /**
     * @return testCount
     */
    @GetMapping("/exam")
    @Operation(summary = "가지고 있는 테스트 횟수", description = "테스트 횟수 확인")
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
    @Operation(summary = "학원 회원가입 및 시험응시", description = "전화번호 확인 후 새로 가입 및 기존회원 로그인")
    public HttpEntity<AcademyMemberResponseDto> academyMember(@RequestBody AcademyMemberRequestDto request) {
        return ResponseEntity.ok(memberService.setAcademyMember(request));
    }

    @GetMapping("/management")
    @Operation(summary = "학생 관리", description = "학원계정의 학원관리")
    public HttpEntity<AcademyManagementResponseDto> academyMemberList( @PageableDefault Pageable pageable){

        return ResponseEntity.ok(memberService.findAll(pageable));
    }

    @GetMapping("/management/search")
    @Operation(summary = "학생관리에서 학생 이름 검색", description = "이름 검색")
    public HttpEntity<AcademyManagementResponseDto> academyMemberList(@RequestParam(value = "name") String naming, @PageableDefault Pageable pageable){

        return ResponseEntity.ok(memberService.find(naming,pageable));
    }

    @PostMapping("/management")
    @Operation(summary = "학원 학생 삭제", description = "학생 삭제")
    public HttpEntity<MemberStatusOkResponseDto> setAcademyMemberDelete(@RequestParam(value="student-id") Long studentId) {
        return ResponseEntity.ok(memberService.deleteAcademyMember(studentId));
    }

    @GetMapping("/management/result")
    public HttpEntity<MemberResultResponseDto> resultSelect(@PageableDefault Pageable pageable, @RequestParam(value="id") Long id){
        return ResponseEntity.ok(memberService.resultSelect(pageable, id));
    }

}