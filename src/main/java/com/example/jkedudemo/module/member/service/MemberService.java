package com.example.jkedudemo.module.member.service;


import com.example.jkedudemo.module.common.enums.Phoneauth;
import com.example.jkedudemo.module.common.enums.Role;
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

    /**
     *
     * @param phone
     * @param phoneauth
     * @return
     */
    @Transactional
    public String certifiedPhone(String phone, Phoneauth phoneauth) {

        StringBuilder cerNum = getCerNum(phone);
        if(phoneauth.equals(Phoneauth.JOIN)) {
            Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(phone, List.of(Status.GREEN, Status.YELLOW));
            if (memberOptional.isPresent()) {
                return "exEmail";
            }
        }

        //collSMS 등록된 사용자
        String api_key = "NCSFXG0SLI1M6IP8";
        String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phone);    // 수신전화번호
        params.put("from", "010-8948-8846");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());

            Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneAndPhoneauth(phone, phoneauth);
            if (optional.isEmpty()) {
                memberPhoneAuthRepository.save( new MemberPhoneAuth(
                    null, null, phone, phoneauth, YN.N, cerNum.toString()
                ));
                // save
            } else {
                MemberPhoneAuth memberPhoneAuth = optional.get();
                memberPhoneAuth.setMember(null);
                memberPhoneAuth.setCheckYn(YN.N);
                memberPhoneAuth.setSmscode(cerNum.toString());
            }
            return "OK";
            //인증코드 발송
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        return "시스템 운영자에게 문의하세요.";
    }

    /**
     *
     * @param phone
     * @param smscode
     * @param phoneauth
     * @return
     */
    @Transactional
    public String certifiedPhoneCheck(String phone ,String smscode , Phoneauth phoneauth){
        Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneAndSmscodeAndPhoneauth(phone, smscode, phoneauth);
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
     * @return
     */
    public MemberResponseDto getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    /**
     *
     * @param exPassword
     * @param newPassword
     * @return
     */
    @Transactional
    public MemberResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = isMemberCurrent();
        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }

        member.setPassword(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));
    }

    /**
     *
     * @param password
     * @return
     */
    @Transactional
    public MemberResponseDto deleteMember(String password) {
        Member member = isMemberCurrent();
        List<MemberPhoneAuth> memberPhoneAuth =memberPhoneAuthRepository.findByPhone(member.getPhone());

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        //회원탈퇴시 기존 인증여부 N
        member.setStatus(Status.RED);
        memberPhoneAuth.forEach(memberPhoneAuth1->memberPhoneAuth1.setCheckYn(YN.N));
        return MemberResponseDto.of(memberRepository.save(member));
    }

    /**
     *
     * @param phone
     * @return
     */
    public MemberResponseDto getMemberEmail(String phone){
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYnAndPhoneauth(phone,YN.Y, Phoneauth.ID);
        if(memberPhoneAuthOptional.isEmpty()){
            throw new RuntimeException("인증을 완료하세요");
        }
        Optional<Member> memberOptional=memberRepository.findByPhoneAndStatusIn(phone,List.of(Status.GREEN,Status.YELLOW));
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            return MemberResponseDto.of(member);
        }

        throw new RuntimeException("가입된 이메일 정보가 없습니다.");
    }

    //TODO: return 타입 확인

    /**
     *
     * @param email
     * @param phone
     * @return
     */
    public MemberResponseDto getPassword(String email , String phone){
        if(memberRepository.existsByEmailAndStatusIn(email,List.of(Status.GREEN,Status.YELLOW))){
            throw new RuntimeException("해당 이메일로 가입된 아이디가 없습니다.");
        }
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYnAndPhoneauth(phone,YN.Y, Phoneauth.PW);
        if(memberPhoneAuthOptional.isEmpty()){
            throw new RuntimeException("인증을 완료하세요");
        }
        Optional<Member> memberOptional=memberRepository.findByPhoneAndStatusIn(phone,List.of(Status.GREEN));
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            return MemberResponseDto.of(member);
        }
        //정지된 회원
        throw new RuntimeException("시스템 운영자에 문의하세요");
    }

    //TODO:비밀번호 찾기 이후 비밀번호 재설정
//    public MemberResponseDto getPasswordChange(String exPassword,String newPassword){
//        Member member = isMemberCurrent();
//        if () {
//            throw new RuntimeException("비밀번호가 맞지 않습니다");
//        }
//
//        member.setPassword(passwordEncoder.encode((newPassword)));
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
        if(!member.getRole().equals(Role.ROLE_ACADEMY))
            throw new RuntimeException("잘못된 요청입니다.");

        return MemberResponseDto
                .of(memberRepository.save(new Member(null, null, requestDto.getName(), requestDto.getBirth(), null, requestDto.getPhone(), member.getAcademyId(), Role.ROLE_ACADEMY_STUDENT, null, 0)));
        }

    /**
     * 이메일 중복체크
     * @param email
     * @return
     */
    public String exEmailCheck(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            return "YES";
        }else{
            return "NO";
        }

        }

    /**
     *
      * @return
     */
    public Member setTestMember(){
        return memberRepository.save(
                new Member(
                        null,"1111","aaaa",null,"1111","1111","1111",null,null,0
                )
        );

     }
    }
