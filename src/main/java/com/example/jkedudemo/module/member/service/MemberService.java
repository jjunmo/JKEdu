package com.example.jkedudemo.module.member.service;

import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.common.enums.Phoneauth;
import com.example.jkedudemo.module.common.enums.Role;
import com.example.jkedudemo.module.common.enums.Status;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.member.dto.request.AcademyMemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.*;
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

import static com.example.jkedudemo.module.common.util.Cer.getCerNum;import static com.example.jkedudemo.module.common.util.Cer.getStrStrCerNum;

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
                .orElseThrow(() -> new MyInternalServerException("로그인 유저 정보가 없습니다"));
    }

    //TODO : 회원 삭제 후 재 회원가입에 대한 Status 상태값 체크 필요.

    @Transactional
    public String certifiedPhone(String phone, Phoneauth phoneauth) {

        StringBuilder cerNum = getCerNum(phone);
        //인증요청시 연락처 유효성 체크
        if(phoneauth.equals(Phoneauth.JOIN)) {
            Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(phone, List.of(Status.GREEN, Status.YELLOW));
            if (memberOptional.isPresent()) {
                throw new MyInternalServerException("이미 사용중인 전화번호 입니다.");
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

            //해당 휴대전화로 인증요청이 없을경우 있을경우도 동일한 코드로 확인됨.
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
        throw new MyInternalServerException("운영자에게 문의 부탁드립니다.");
    }

    @Transactional
    public String certifiedPhoneCheck(String phone ,String smscode , Phoneauth phoneauth){

        Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneAndSmscodeAndPhoneauth(phone, smscode, phoneauth);
        if(optional.isEmpty()){
            throw new MyInternalServerException("인증번호가 일치하지 않습니다.");
        }else{
            MemberPhoneAuth memberPhoneAuth = optional.get();
            memberPhoneAuth.setCheckYn(YN.Y);

            log.info("인증"+memberPhoneAuth.getCheckYn().toString());
            log.info("인증유형"+memberPhoneAuth.getPhoneauth().toString());

            return "OK";
        }
    }

    public MemberMyInfoResponseDto getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberMyInfoResponseDto::myInfo)
                .orElseThrow(() -> new MyInternalServerException("로그인 유저 정보가 없습니다"));
    }


    @Transactional
    public MemberStatusOkResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = isMemberCurrent();
        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            throw new MyInternalServerException("비밀번호가 맞지 않습니다");
        }

        member.setPassword(passwordEncoder.encode((newPassword)));
        memberRepository.save(member);
        return MemberStatusOkResponseDto.statusOk();
    }


    @Transactional
    public MemberStatusOkResponseDto deleteMember(String password) {
        Member member = isMemberCurrent();
        List<MemberPhoneAuth> memberPhoneAuth =memberPhoneAuthRepository.findByPhone(member.getPhone());

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new MyInternalServerException("비밀번호가 맞지 않습니다");
        }
        //회원탈퇴시 기존 인증여부 N
        member.setStatus(Status.RED);
        memberPhoneAuth.forEach(memberPhoneAuth1->memberPhoneAuth1.setCheckYn(YN.N));
        memberRepository.save(member);
        return MemberStatusOkResponseDto.statusOk();
    }

    public MemberNameResopnseDto memberName(){
        Member member = isMemberCurrent();
        return MemberNameResopnseDto.name(member);
    }


    @Transactional
    public MemberIdFindResopnseDto getMemberEmail(String phone , String smscode, Phoneauth phoneauth) {

        String result = certifiedPhoneCheck(phone, smscode,phoneauth);

        //TODO : 예외처리 이후 테스트 / 정상적인 sms 인증 이후 다시 재인증시 인증되지않음 / POSTMAN 이용시 정상작동 확인

        if(result.equals("OK")){
            Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(phone,List.of(Status.GREEN,Status.YELLOW));
            if(memberOptional.isEmpty()){
                throw new MyInternalServerException("해당 정보로 가입된 아이디가 없습니다.");
            }

            Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndPhoneauth(phone,Phoneauth.ID);
            if(memberPhoneAuthOptional.isEmpty()){
                throw new MyInternalServerException("휴대폰 인증을 완료하세요.");
            }
            MemberPhoneAuth memberPhoneAuth = memberPhoneAuthOptional.get();
            Member member=memberOptional.get();
            memberPhoneAuth.setMember(member);

            return MemberIdFindResopnseDto.idFind(member);
             }
        throw new MyInternalServerException("인증이 실패하였습니다.");
        }

    //TODO:비밀번호 찾기 이후 비밀번호 재설정
//    public MemberResponseDto getPasswordChange(String exPassword,String newPassword){
//        Member member = isMemberCurrent();
//        if () {
//            throw new MyInternalServerException("비밀번호가 맞지 않습니다");
//        }
//
//        member.setPassword(passwordEncoder.encode((newPassword)));
//        return MemberResponseDto.of(memberRepository.save(member));
//    }

    @Transactional
    public AcademyMemberResponseDto setAcademyMember(AcademyMemberRequestDto requestDto) {
        Member member = isMemberCurrent();
        if(!member.getRole().equals(Role.ROLE_ACADEMY))
            throw new MyInternalServerException("잘못된 요청입니다.");
        return AcademyMemberResponseDto.academyExamId(memberRepository.save(new Member(null, null, requestDto.getName(), requestDto.getBirth(), null, requestDto.getPhone(), member.getAcademyId(), Role.ROLE_ACADEMY_STUDENT, null, 0)));
        }


    public String exEmailCheck(String email){
        Optional<Member> member = memberRepository.findByEmailAndStatusIn(email,List.of(Status.GREEN,Status.YELLOW));
        if(member.isEmpty()){
            return "OK";
        }else{
            throw new MyInternalServerException("이미 가입된 이메일 입니다.");
        }

        }

    @Transactional
    public MemberStatusOkResponseDto getNewPassword(String phone ,String smscode ,Phoneauth phoneauth){

        String result = certifiedPhoneCheck(phone,smscode,phoneauth);

        if(result.equals("OK")){
            Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(phone,List.of(Status.GREEN,Status.YELLOW));
            if(memberOptional.isEmpty()){
                throw new MyInternalServerException("해당 정보로 가입된 아이디가 없습니다.");
            }

            Member member=memberOptional.get();

            Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYnAndPhoneauth(phone,YN.Y, Phoneauth.PW);

            if(memberPhoneAuthOptional.isEmpty()){
                throw new MyInternalServerException("인증을 완료하세요");
            }

            memberPhoneAuthOptional.get().setMember(member);

            //TODO:임시 비밀번호 문자로 발급 후 데이터베이스에 비밀번호 임시비밀번호로 저장

            StringBuilder cerNum = getStrStrCerNum(phone);

            //collSMS 등록된 사용자
            String api_key = "NCSFXG0SLI1M6IP8";
            String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
            Message coolsms = new Message(api_key, api_secret);

            // 4 params(to, from, type, text) are mandatory. must be filled
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("to", phone);    // 수신전화번호
            params.put("from", "010-8948-8846");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
            params.put("type", "SMS");
            params.put("text", " 변경된 임시비밀번호는 " + "["+cerNum+"]" + "입니다.");
            params.put("app_version", "test app 1.2"); // application name and version

            try {
                JSONObject obj = (JSONObject) coolsms.send(params);
                System.out.println(obj.toString());
                member.setPassword(passwordEncoder.encode(cerNum));

                return MemberStatusOkResponseDto.statusOk();
                //인증코드 발송
            } catch (CoolsmsException e) {

                System.out.println(e.getMessage());
                System.out.println(e.getCode());
            }

        }

        throw new MyInternalServerException("인증이 실패하였습니다.");

    }

    public Member setTestMember(){
        return memberRepository.save(
                new Member(
                        null,"1111","aaaa",null,"1111","1111","1111",null,null,0
                )
        );
    }
}
