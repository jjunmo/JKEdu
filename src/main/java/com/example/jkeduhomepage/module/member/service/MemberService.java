package com.example.jkeduhomepage.module.member.service;


import com.amazonaws.services.kms.model.NotFoundException;
import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.common.enums.YN;
import com.example.jkeduhomepage.module.config.SecurityUtil;
import com.example.jkeduhomepage.module.jwt.TokenProvider;
import com.example.jkeduhomepage.module.member.dto.MemberRequestDTO;
import com.example.jkeduhomepage.module.member.dto.MemberReservationDTO;
import com.example.jkeduhomepage.module.member.dto.MemberResponseDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.entity.MemberPhoneAuth;
import com.example.jkeduhomepage.module.member.repository.MemberPhoneAuthRepository;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.example.jkeduhomepage.module.common.utility.Cer.getCerNum;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final AuthenticationManagerBuilder managerBuilder;

    private final MemberRepository memberRepository;

    private final MemberPhoneAuthRepository memberPhoneAuthRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NotFoundException("로그인 유저 정보가 없습니다"));
    }

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
            member.setStatus(Status.RED);
            member.setRole(Role.ROLE_USER);
            member.setMemberPhoneAuth(memberPhoneAuthOptional.get());
            memberRepository.save(member);
            return "YES";

        } else return null;
    }
    @Transactional
    public Object login(MemberRequestDTO memberRequestDTO){
        Optional<Member> memberOptional = memberRepository.findByLoginId(memberRequestDTO.getLoginId());

        if(memberOptional.isEmpty()) return "id_fail";

        Member member = memberOptional.get();

        if (!passwordEncoder.matches(memberRequestDTO.getPassword(), member.getPassword())) return "pw_fail";

        if(member.getStatus().equals(Status.RED)) return "approval_fail";


        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDTO.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);


        return tokenProvider.generateTokenDto(authentication,member.getName());

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
        return memberRepository.findByStatusAndRole(Status.GREEN,Role.ROLE_USER);
    }

    public List<MemberResponseDTO> approvalList(){
        List<Member> memberList=memberRepository.findByStatus(Status.RED);
        return MemberResponseDTO.approvalList(memberList);
    }


    @Transactional
    public String approvalMember(Long id){
            Optional<Member> memberOptional=memberRepository.findById(id);
            if(memberOptional.isPresent()){
                Member member = memberOptional.get();
                member.setStatus(Status.GREEN);
                memberRepository.save(member);
                return "OK";
            }

            return null;
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

        Optional<MemberPhoneAuth> memberPhoneAuthOptional= memberPhoneAuthRepository.findByPhone(phone);

        if(memberPhoneAuthOptional.isEmpty()){
            MemberPhoneAuth memberPhoneAuth=new MemberPhoneAuth();
            memberPhoneAuth.setPhone(phone);
            memberPhoneAuth.setSmscode(String.valueOf(cerNum));
            memberPhoneAuth.setCheckYn(YN.N);

            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());

            return memberPhoneAuthRepository.save(memberPhoneAuth);

        }

            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());

            MemberPhoneAuth memberPhoneAuth =memberPhoneAuthOptional.get();
            memberPhoneAuth.setCheckYn(YN.N);
            memberPhoneAuth.setSmscode(String.valueOf(cerNum));

        return memberPhoneAuth;

        }

    @Transactional
    public String certifiedPhoneCheck(String phone ,String smscode ){

        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYn(phone, YN.N);

        if(memberPhoneAuthOptional.isEmpty()){
            return "NO";
        }

        MemberPhoneAuth memberPhoneAuth= memberPhoneAuthOptional.get();

        if(memberPhoneAuth.getSmscode().equals(smscode)){
            memberPhoneAuth.setCheckYn(YN.Y);
            memberPhoneAuthRepository.save(memberPhoneAuth);
            return "OK";
        }
            return smscode;

    }

    public void reservation(MemberReservationDTO memberReservationDTO) throws CoolsmsException {
        LocalDate localDate= new Date(memberReservationDTO.getDay().getTime()).toLocalDate();

        String api_key = "NCSFXG0SLI1M6IP8";
        String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<>();
        params.put("to", "010-8948-8846");    // 수신전화번호 01089488846
        params.put("from", "051-747-1788");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("subject","입학 테스트 신청");
        params.put("text",
//                "입학 테스트 신청"+"\n" +
                        "테스트 날짜 : "+ localDate.toString() + "\n" +
                        "학생이름 : "+ memberReservationDTO.getName() +"\n" +
//                        "학교 : "+ memberReservationDTO.getSchool() +"\n" +
//                        "학년 : "+memberReservationDTO.getGrade()+ "\n" +
                        "전화번호 : "+memberReservationDTO.getPhone()+"\n");
//                        "예약자 관계 : "+ memberReservationDTO.getRelationship());
        params.put("app_version", "test app 1.2"); // application name and version


        coolsms.send(params);

    }
}