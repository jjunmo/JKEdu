package com.example.jkedudemo.module.member.service;


import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.common.enums.Status;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.config.SecurityUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final MemberPhoneAuthRepository memberPhoneAuthRepository;

    @Transactional
    public String certifiedPhoneNumber(String phoneNumber) {

        StringBuilder cerNum = getCerNum(phoneNumber);

        Optional<Member> memberOptional = memberRepository.findByPhoneNumberAndStatusIn(phoneNumber, List.of(Status.GREEN, Status.YELLOW));
        if (memberOptional.isPresent()) {
            return "해당 휴대전화로 가입된 회원이 존재합니다.";
        }

        String api_key = "NCSCAVV6MBJ5GU58";
        String api_secret = "VV7COUINJU9HWUXHXQ0SX4M5TRKHQEDN";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01091097122");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());

            Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneNumberAndPhoneAuthType(phoneNumber, PhoneAuthType.Join);
            if (optional.isEmpty()) {
                memberPhoneAuthRepository.save( new MemberPhoneAuth(
                    null, null, phoneNumber, PhoneAuthType.Join, YN.N, cerNum.toString()
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

    public String certifiedPhoneNumberCheck(String phoneNumber ,String code){
        Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneNumberAndCode(phoneNumber,code);
        if(optional.isEmpty()){
            return "인증번호가 올바르지 않습니다.";
        }else{
            optional.get().setCheckYn(YN.Y);
            return "OK";
        }
    }



    /**
     *
     * @param phoneNumber 수신자 번호
     * @return 인증번호 생성
     */
    private static StringBuilder getCerNum(String phoneNumber) {
        Random random  = new Random();
        StringBuilder cerNum = new StringBuilder();
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(random.nextInt(10));
            cerNum.append(ran);
        }

        log.info("수신자 번호 : " + phoneNumber);
        log.info("인증번호 : " + cerNum);
        return cerNum;
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

    //TODO: 아직 구현되지않음
    /**
     *  비밀번호 변경
     * @param exPassword 기존 비밀번호
     * @param newPassword 새 비밀번호
     * @return 변경된 비밀번호 저장
     */
    @Transactional
    public MemberResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인된 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getMemberPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.setMemberPassword(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));
    }


}
