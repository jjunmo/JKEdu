package com.example.jkeduhomepage.module.member.service;


import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.common.enums.YN;
import com.example.jkeduhomepage.module.jwt.TokenProvider;
import com.example.jkeduhomepage.module.member.dto.MemberRequestDTO;
import com.example.jkeduhomepage.module.member.dto.MemberReservationDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.entity.MemberPhoneAuth;
import com.example.jkeduhomepage.module.member.repository.MemberPhoneAuthRepository;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.example.jkeduhomepage.module.common.utility.Cer.getCerNum;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final AuthenticationManagerBuilder managerBuilder;

    private final MemberRepository memberRepository;

    private final MemberPhoneAuthRepository memberPhoneAuthRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    @Transactional
    public String save(MemberRequestDTO memberRequestDTO) {
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYn(memberRequestDTO.getPhone(), YN.Y);

        if (memberPhoneAuthOptional.isEmpty()) {
            return "NO";
        }

        Optional<Member> memberOptional = memberRepository.findByPhone(memberRequestDTO.getPhone());

        if (memberOptional.isEmpty()) {
            Member member = new Member();
            member.setLoginId(memberRequestDTO.getLoginId());
            member.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));
            member.setEmail(memberRequestDTO.getEmail());
            member.setName(memberRequestDTO.getName());
            member.setPhone(memberRequestDTO.getPhone());
            //TODO: Test
            member.setStatus(Status.GREEN);
            member.setRole(Role.ROLE_USER);
            member.setMemberPhoneAuth(memberPhoneAuthOptional.get());
            memberRepository.save(member);
            return "YES";
        } else

            return null;
    }
    @Transactional
    public Object login(MemberRequestDTO memberRequestDTO){
        Optional<Member> memberOptional = memberRepository.findByLoginIdAndStatus(memberRequestDTO.getLoginId(), Status.GREEN);

        if(memberOptional.isEmpty()){
            return "id_fail";
        }
         Member member = memberOptional.get();

            if (!passwordEncoder.matches(memberRequestDTO.getPassword(), member.getPassword())) return "pw_fail";

            UsernamePasswordAuthenticationToken authenticationToken = memberRequestDTO.toAuthentication();
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);


           return tokenProvider.generateTokenDto(authentication);

    }

    public Optional<Member> getMember(Long id){

        return memberRepository.findById(id);
    }

    public Optional<Member> updateMember (MemberUpdateDTO memberUpdateDTO,Long id){

        Optional<Member> memberOptional= memberRepository.findById(id);

        if(memberOptional.isEmpty()) return memberOptional;

        Member paramMember =memberOptional.get();

        paramMember.setEmail(memberUpdateDTO.getEmail());
        paramMember.setPassword(memberUpdateDTO.getPassword());

        return Optional.of(paramMember);
    }

    public List<Member> allList(){
        return memberRepository.findAll();
    }

    @Transactional
    public MemberPhoneAuth certifiedPhone(String phone) throws CoolsmsException {

        StringBuilder cerNum = getCerNum(phone);
        //인증요청시 연락처 유효성 체크

        //collSMS 등록된 사용자
        String api_key = "NCSFXG0SLI1M6IP8";
        String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<>();
        params.put("to", phone);    // 수신전화번호
        params.put("from", "051-747-1788");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "휴대폰 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version


            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());

        Optional<MemberPhoneAuth> memberPhoneAuthOptional= memberPhoneAuthRepository.findByPhoneAndCheckYn(phone,YN.Y);
        if(memberPhoneAuthOptional.isEmpty()){
            MemberPhoneAuth memberPhoneAuth=new MemberPhoneAuth();
            memberPhoneAuth.setPhone(phone);
            memberPhoneAuth.setSmscode(String.valueOf(cerNum));
            memberPhoneAuth.setCheckYn(YN.N);
            return memberPhoneAuthRepository.save(memberPhoneAuth);
        }

        return memberPhoneAuthOptional.get();

        }

    @Transactional
    public String certifiedPhoneCheck(String phone ,String smscode ){

        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYn(phone,YN.N);

        if(memberPhoneAuthOptional.isEmpty()){
            return "NO";
        }

        MemberPhoneAuth memberPhoneAuth= memberPhoneAuthOptional.get();

        if(memberPhoneAuth.getSmscode().equals(smscode)){
            memberPhoneAuth.setCheckYn(YN.Y);
            memberPhoneAuthRepository.save(memberPhoneAuth);
            return "OK";
        }
            return null;

    }

    public void reservation(MemberReservationDTO memberReservationDTO) throws CoolsmsException {

        String api_key = "NCSFXG0SLI1M6IP8";
        String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<>();
        params.put("to", "010-9109-7122");    // 수신전화번호
        params.put("from", "051-747-1788");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text",
                "입학 테스트\n" +
                        "테스트 날짜 : "+memberReservationDTO.getDay()+ "\n" +
                "학생이름 : "+ memberReservationDTO.getName()+"\n" +
                "학교 : "+memberReservationDTO.getSchool()+"\n" +
                "학년 : "+memberReservationDTO.getGrade()+ "\n" +
                "전화번호 : "+memberReservationDTO.getPhone()+"\n" +
                "예약자 관계 : "+ memberReservationDTO.getRelationship());
        params.put("app_version", "test app 1.2"); // application name and version


        coolsms.send(params);

    }
}