package com.example.jkedudemo.module.member.service;


import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.common.enums.RoleType;
import com.example.jkedudemo.module.common.enums.Status;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.member.dto.request.AcademyMemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import com.example.jkedudemo.module.member.repository.MemberPhoneAuthRepository;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.jkedudemo.module.common.util.Cer.getCerNum;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final MemberPhoneAuthRepository memberPhoneAuthRepository;

    //토큰확인
    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    //TODO : 회원 삭제 후 재 회원가입에 대한 Status 상태값 체크 필요.
    @Transactional
    public String certifiedPhoneNumber(String phoneNumber, PhoneAuthType phoneAuthType) {

        StringBuilder cerNum = getCerNum(phoneNumber);
        if(phoneAuthType.equals(PhoneAuthType.JOIN)) {
            Optional<Member> memberOptional = memberRepository.findByPhoneNumberAndStatusIn(phoneNumber, List.of(Status.GREEN, Status.YELLOW));
            if (memberOptional.isPresent()) {
                return "해당 휴대전화로 가입된 회원이 존재합니다.";
            }
        }

        //collSMS 등록된 사용자
        String api_key = "NCSFXG0SLI1M6IP8";
        String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "050713731789");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());

            Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneNumberAndPhoneAuthType(phoneNumber,phoneAuthType);
            if (optional.isEmpty()) {
                memberPhoneAuthRepository.save( new MemberPhoneAuth(
                    null, null, phoneNumber, phoneAuthType, YN.N, cerNum.toString()
                ));
                // save
            } else {
                MemberPhoneAuth memberPhoneAuth = optional.get();
                memberPhoneAuth.setMember(null);
                memberPhoneAuth.setCheckYn(YN.N);
                memberPhoneAuth.setCode(cerNum.toString());
            }
            return "OK";
            //인증코드 발송
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        return "시스템 운영자에게 문의하세요.";
    }

    @Transactional
    public String certifiedPhoneNumberCheck(String phoneNumber ,String code , PhoneAuthType phoneAuthType){
        Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneNumberAndCodeAndPhoneAuthType(phoneNumber, code, phoneAuthType);
        if(optional.isEmpty()){
            return "휴대폰번호와 인증번호를 확인하세요";
        }else{
            MemberPhoneAuth memberPhoneAuth = optional.get();
            memberPhoneAuth.setCheckYn(YN.Y);
            log.info("인증"+memberPhoneAuth.getCheckYn().toString());
        }
        return "OK";
    }


    /**
     *
     * @return 현재 유저정보를 가져옴
     */
    public MemberResponseDto getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    /**
     *  비밀번호 변경
     * @param exPassword 기존 비밀번호
     * @param newPassword 새 비밀번호
     * @return 변경된 비밀번호 저장
     */
    @Transactional
    public MemberResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = isMemberCurrent();
        if (!passwordEncoder.matches(exPassword, member.getMemberPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }

        member.setMemberPassword(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));
    }

    /**
     * 계정 삭제
     * @param memberPassword 현재 비밀번호
     * @return 계정 삭제 상태로 변경
     */
    @Transactional
    public MemberResponseDto deleteMember(String memberPassword) {
        Member member = isMemberCurrent();
        List<MemberPhoneAuth> memberPhoneAuth =memberPhoneAuthRepository.findByPhoneNumber(member.getPhoneNumber());

        if (!passwordEncoder.matches(memberPassword, member.getMemberPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        //회원탈퇴시 기존 인증여부 N
        member.setStatus(Status.RED);
        memberPhoneAuth.forEach(memberPhoneAuth1->memberPhoneAuth1.setCheckYn(YN.N));
        return MemberResponseDto.of(memberRepository.save(member));
    }

    /**
     *
     * @param phoneNumber 수신자 번호
     * @return
     */
    public MemberResponseDto getMemberEmail(String phoneNumber){
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneNumberAndCheckYnAndPhoneAuthType(phoneNumber,YN.Y, PhoneAuthType.ID_FIND);
        if(memberPhoneAuthOptional.isEmpty()){
            throw new RuntimeException("인증을 완료하세요");
        }
        Optional<Member> memberOptional=memberRepository.findByPhoneNumberAndStatusIn(phoneNumber,List.of(Status.GREEN,Status.YELLOW));
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            return MemberResponseDto.of(member);
        }

        throw new RuntimeException("가입된 이메일 정보가 없습니다.");
    }

    //TODO: return 타입 확인
    /**
     * 비밀번호 찾기
     * @param email 가입된 이메일
     * @param phoneNumber 가입된 휴대전화
     * @return member
     */
    public MemberResponseDto getMemberPassword(String email , String phoneNumber){
        if(memberRepository.existsByEmailAndStatusIn(email,List.of(Status.GREEN,Status.YELLOW))){
            throw new RuntimeException("해당 이메일로 가입된 아이디가 없습니다.");
        }
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneNumberAndCheckYnAndPhoneAuthType(phoneNumber,YN.Y, PhoneAuthType.PW_FIND);
        if(memberPhoneAuthOptional.isEmpty()){
            throw new RuntimeException("인증을 완료하세요");
        }
        Optional<Member> memberOptional=memberRepository.findByPhoneNumberAndStatusIn(phoneNumber,List.of(Status.GREEN));
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            return MemberResponseDto.of(member);
        }
        //정지된 회원
        throw new RuntimeException("시스템 운영자에 문의하세요");
    }

    //TODO:비밀번호 찾기 이후 비밀번호 재설정
//    public MemberResponseDto getMemberPasswordChange(String exPassword,String newPassword){
//        Member member = isMemberCurrent();
//        if () {
//            throw new RuntimeException("비밀번호가 맞지 않습니다");
//        }
//
//        member.setMemberPassword(passwordEncoder.encode((newPassword)));
//        return MemberResponseDto.of(memberRepository.save(member));
//    }


    /**
     * 학원 학생 시험보
     * @param requestDto 기름 , 연락처 ,생일
     * @return AcademyId , ROLE_ACADEMY_STUDENT 를 가진 멤버 생성
     */
    @Transactional
    public MemberResponseDto setAcademyMember(AcademyMemberRequestDto requestDto) {
        Member member = isMemberCurrent();
        if(!member.getRoleType().equals(RoleType.ROLE_ACADEMY))
            throw new RuntimeException("잘못된 요청입니다.");

        return MemberResponseDto
                .of(memberRepository.save(new Member(null, null, requestDto.getName(), requestDto.getBirth(), null, requestDto.getPhoneNumber(), member.getAcademyId(), RoleType.ROLE_ACADEMY_STUDENT, null)));
        }

    public Member setTestMember(){
        return memberRepository.save(
                new Member(
                        null,"1111","aaaa",null,"1111","1111","1111",null,null
                )
        );

     }
    }
