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
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.HashMap;


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
    public HttpEntity<Object> sendSMS(@RequestParam("phone") String phone,@RequestParam("phoneauthtype") PhoneAuthType phoneAuthType) {
        String result = memberService.certifiedPhone(phone,phoneAuthType);
        HashMap<HttpStatus,String> httpStatusStringHashMap = new HashMap<>();
        if (result.equals("OK")) {
            httpStatusStringHashMap.put(HttpStatus.valueOf(200),"문자 발송 성공");
            return ResponseEntity.ok(JSONObject.toJSONString(httpStatusStringHashMap));
        } else {
            httpStatusStringHashMap.put(HttpStatus.valueOf(400),"관리자에게 문의 해주세요.");
            return ResponseEntity.badRequest().body(JSONObject.toJSONString(httpStatusStringHashMap));
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
        HashMap<HttpStatus,String> httpStatusStringHashMap = new HashMap<>();
        if(result.equals("OK")) {
            httpStatusStringHashMap.put(HttpStatus.valueOf(200),"인증을 성공하였습니다.");
            return ResponseEntity.ok(JSONObject.toJSONString(httpStatusStringHashMap));
        }else {
            httpStatusStringHashMap.put(HttpStatus.valueOf(400),"인증에 실패하였습니다.");
            return ResponseEntity.badRequest().body(JSONObject.toJSONString(httpStatusStringHashMap));
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
