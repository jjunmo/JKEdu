package com.example.jkedudemo.module.member.service;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.enums.member.Status;
import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.exam.entity.ExamResult;
import com.example.jkedudemo.module.exam.repository.ExamPaperRepository;
import com.example.jkedudemo.module.exam.repository.ExamResultRepository;
import com.example.jkedudemo.module.handler.MyBadRequestException;
import com.example.jkedudemo.module.handler.MyForbiddenException;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.handler.MyNotFoundException;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.jkedudemo.module.common.utility.Cer.getCerNum;
import static com.example.jkedudemo.module.common.utility.Cer.getStrStrCerNum;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ExamResultRepository examResultRepository;
    private final ExamPaperRepository examPaperRepository;

    private final MemberPhoneAuthRepository memberPhoneAuthRepository;

    //토큰확인

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new MyForbiddenException("로그인 유저 정보가 없습니다"));
    }

    @Transactional
    public String certifiedPhone(String phone, PhoneAuth phonauth) {

        StringBuilder cerNum = getCerNum(phone);
        //인증요청시 연락처 유효성 체크
        if(phonauth.equals(PhoneAuth.JOIN)) {
            Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(phone, List.of(Status.GREEN, Status.YELLOW));

            if (memberOptional.isPresent()) throw new MyForbiddenException("이미 사용중인 전화번호 입니다.");

        }

        //collSMS 등록된 사용자
        String api_key = "NCSFXG0SLI1M6IP8";
        String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<>();
        params.put("to", phone);    // 수신전화번호
        params.put("from", "010-8948-8846");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());

            //해당 휴대전화로 인증요청이 없을경우 있을경우도 동일한 코드로 확인됨.
            Optional<MemberPhoneAuth> optional = memberPhoneAuthRepository.findByPhoneAndPhoneauth(phone, phonauth);
            if (optional.isEmpty()) memberPhoneAuthRepository.save( new MemberPhoneAuth(null, null, phone, phonauth, YN.N, cerNum.toString()));
                // save
             else {
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
    public String certifiedPhoneCheck(String phone ,String smscode , PhoneAuth phoneauth){

        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndSmscodeAndPhoneauth(phone, smscode, phoneauth);
        if(memberPhoneAuthOptional.isEmpty()) throw new MyForbiddenException("인증번호가 일치하지 않습니다.");
        else{

            MemberPhoneAuth memberPhoneAuth = memberPhoneAuthOptional.get();
            memberPhoneAuth.setCheckYn(YN.Y);

            log.info("인증"+memberPhoneAuth.getCheckYn().toString());
            log.info("인증유형"+memberPhoneAuth.getPhoneauth().toString());

            return "OK";
        }
    }

    public MemberMyInfoResponseDto getMyInfoBySecurity() {
        Member member =isMemberCurrent();
        return MemberMyInfoResponseDto.myInfo(member);
    }


    @Transactional
    public MemberStatusOkResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = isMemberCurrent();

        if (!passwordEncoder.matches(exPassword, member.getPassword()))
            throw new MyForbiddenException("현재 비밀번호가 옳지 않습니다.");

        member.setPassword(passwordEncoder.encode((newPassword)));
        memberRepository.save(member);
        return MemberStatusOkResponseDto.statusOk();
    }


    @Transactional
    public MemberStatusOkResponseDto deleteMember(String password) {
        Member member = isMemberCurrent();
        List<MemberPhoneAuth> memberPhoneAuth =memberPhoneAuthRepository.findByPhone(member.getPhone());

        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new MyForbiddenException("비밀번호가 맞지 않습니다");

        //회원탈퇴시 기존 인증여부 N
        member.setStatus(Status.RED);
        memberPhoneAuth.forEach(memberPhoneAuth1->memberPhoneAuth1.setCheckYn(YN.N));
        memberRepository.save(member);
        return MemberStatusOkResponseDto.statusOk();
    }

    @Transactional
    public MemberIdFindResopnseDto getMemberEmail(String phone , String smscode, PhoneAuth phoneauth) {

        String result = certifiedPhoneCheck(phone, smscode,phoneauth);

        if(result.equals("OK")){
            Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(phone,List.of(Status.GREEN,Status.YELLOW));

            if(memberOptional.isEmpty()) throw new MyNotFoundException("해당 정보로 가입된 아이디가 없습니다.");

            Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndPhoneauth(phone, PhoneAuth.ID);

            if(memberPhoneAuthOptional.isEmpty()) throw new MyForbiddenException("휴대폰 인증을 완료하세요.");

            MemberPhoneAuth memberPhoneAuth = memberPhoneAuthOptional.get();
            Member member=memberOptional.get();
            memberPhoneAuth.setMember(member);

            return MemberIdFindResopnseDto.idFind(member);
        }
        throw new MyForbiddenException("인증이 실패하였습니다.");
    }


    @Transactional
    public AcademyMemberResponseDto setAcademyMember(AcademyMemberRequestDto requestDto) {
        Member member = isMemberCurrent();

        if(!member.getRole().equals(Role.ROLE_ACADEMY))
            throw new MyForbiddenException("접근 권한이 없습니다.");

        if(requestDto.getPhone().isBlank()||requestDto.getPhone()==null)
            throw new MyForbiddenException("전화번호를 입력하세요.");

        Optional<Member> memberOptional = memberRepository.findByPhoneAndRoleAndAcademyIdAndStatus(requestDto.getPhone(), Role.ROLE_ACADEMY_STUDENT, member.getAcademyId(),Status.GREEN);

        if(memberOptional.isPresent()){
            Member member1=memberOptional.get();
            List<ExamResult> examResultList=examResultRepository.findByMember(member1);

            log.info("examResultList empty 확인");
            if(examResultList.isEmpty()) {
                return new AcademyMemberResponseDto("200","already",member1.getId(),examResultRepository.save(new ExamResult(null,member1)).getId());
            }

            log.info("examResultList stream");
            Optional<ExamResult> examResultOptional=examResultList
                    .stream()
                    .filter(er->!examPaperRepository.existsByExamResult(er))
                    .findFirst();

            if(examResultOptional.isEmpty()) {
                log.info("examResult는 있지만 examPaper가 없는 경우가 없다");
                return new AcademyMemberResponseDto("200", "already", member1.getId(), examResultRepository.save(new ExamResult(null, member1)).getId());
            }
            else return new AcademyMemberResponseDto("200","already",member1.getId(),examResultOptional.get().getId());

        }

        Member member1 = new Member(requestDto.getPhone(),requestDto.getName(),requestDto.getBirth(),Role.ROLE_ACADEMY_STUDENT, member.getAcademyId(),Status.GREEN);

        if(requestDto.getName().isBlank()||requestDto.getName()==null) return new AcademyMemberResponseDto("200", "ready");
        else{
            // name 유효성 체크
            memberRepository.save(member1);

            List<ExamResult> examResultList=examResultRepository.findByMember(member1);

            log.info("examResultList empty 확인");
            if(examResultList.isEmpty()) {
                return new AcademyMemberResponseDto("200","go",member1.getId(),examResultRepository.save(new ExamResult(null,member1)).getId());
            }

            log.info("examResultList stream");
            Optional<ExamResult> examResultOptional=examResultList
                    .stream()
                    .filter(er->!examPaperRepository.existsByExamResult(er))
                    .findFirst();

            if(examResultOptional.isEmpty()) {
                log.info("examResult는 있지만 examPaper가 없는 경우가 없다");
                return new AcademyMemberResponseDto("200", "go", member1.getId(), examResultRepository.save(new ExamResult(null, member1)).getId());
            }
            else return new AcademyMemberResponseDto("200","go",member1.getId(),examResultOptional.get().getId());

        }

    }

    public String exEmailCheck(String email){

        Optional<Member> member = memberRepository.findByEmailAndStatusIn(email,List.of(Status.GREEN,Status.YELLOW,Status.RED));

        if(member.isEmpty()) return "OK";

        else throw new MyForbiddenException("이미 가입된 이메일 입니다.");

    }

    @Transactional
    public MemberStatusOkResponseDto getNewPassword(String email, String phone , String smscode , PhoneAuth phoneauth){

        if(email.isEmpty()) throw new MyForbiddenException("이메일을 입력하세요.");

        //인증번호 확인
        certifiedPhoneCheck(phone,smscode,phoneauth);

        Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(phone,List.of(Status.GREEN,Status.YELLOW));

        if(memberOptional.isEmpty()) throw new MyNotFoundException("해당 정보로 가입된 아이디가 없습니다.");


        Member member=memberOptional.get();

        if(!member.getEmail().equals(email)) throw new MyNotFoundException("해당 정보로 가입된 아이디가 없습니다.");


        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYnAndPhoneauth(phone,YN.Y, PhoneAuth.PW);

        if(memberPhoneAuthOptional.isEmpty()) throw new MyForbiddenException("인증을 완료하세요");


        memberPhoneAuthOptional.get().setMember(member);

        StringBuilder cerNum = getStrStrCerNum(phone);

            //collSMS 등록된 사용자
        String api_key = "NCSFXG0SLI1M6IP8";
        String api_secret = "LSMCMXB2HEIL4LHFZTCKU4PAHGBECFSX";
        Message coolsms = new Message(api_key, api_secret);

            // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<>();
        params.put("to", phone);    // 수신전화번호
        params.put("from", "010-8948-8846");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", " 변경된 임시비밀번호는 " + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());
            member.setPassword(passwordEncoder.encode(cerNum));

            return MemberStatusOkResponseDto.statusOk();
                //인증코드 발송
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        throw new MyForbiddenException("인증이 실패하였습니다.");

    }

    /**
     *
     * @return testCount
     */
    public TestCountResopnseDto getTestCount(){
        Member member = isMemberCurrent();
        return TestCountResopnseDto.testCount(member);
    }

    public AcademyManagementResponseDto findAll(Pageable pageable){
        Member member = isMemberCurrent();

        if(member.getRole().equals(Role.ROLE_USER)) throw new MyBadRequestException("잘못된 요청입니다. 넌 학생임");

        Slice<AcademyMemberListResponseDto> academyMemberListResponseDtoList=
                memberRepository.findByAcademyIdAndRoleAndStatusOrderByIdAsc(member.getAcademyId(),Role.ROLE_ACADEMY_STUDENT,Status.GREEN,pageable)
                .map(AcademyMemberListResponseDto::find);
        return AcademyManagementResponseDto.getPage(academyMemberListResponseDtoList.hasNext(),academyMemberListResponseDtoList.getContent());

    }

    public AcademyManagementResponseDto find(String naming , Pageable pageable){
        Member member=isMemberCurrent();
        Slice<AcademyMemberListResponseDto> academyMemberListResponseDtoList=
                memberRepository.findByAcademyIdAndRoleAndStatusAndNameContainingIgnoreCaseOrderByIdAsc(member.getAcademyId(),Role.ROLE_ACADEMY_STUDENT,Status.GREEN,naming,pageable)
                        .map(AcademyMemberListResponseDto::find);
        return AcademyManagementResponseDto.getPage(academyMemberListResponseDtoList.hasNext(),academyMemberListResponseDtoList.getContent());
    }

    @Transactional
    public MemberStatusOkResponseDto deleteAcademyMember(Long studentId){
       isMemberCurrent();

        Optional<Member> memberOptional=memberRepository.findById(studentId);

        if(memberOptional.isEmpty()) throw new MyNotFoundException("존재하지 않는 학생 입니다.");

        Member academyStudentMember=memberOptional.get();
        academyStudentMember.setStatus(Status.RED);
        memberRepository.save(academyStudentMember);

        return MemberStatusOkResponseDto.statusOk();
    }

    public MemberResultResponseDto resultSelect(Long id,Pageable pageable) {
        Member member = isMemberCurrent();

        if (Objects.equals(member.getRole(), Role.ROLE_ACADEMY)) {
            if(id == null) throw new MyBadRequestException("잘못된 요청입니다.");
            member =  memberRepository.findById(id).orElseThrow(()->new MyBadRequestException("학생을 다시 선택하세요."));
        }

        Slice<ResultListDto> resultListDtoSlice=examResultRepository.findByMemberOrderByIdAsc(member,pageable)
                .map(ResultListDto::getResultList);

        return MemberResultResponseDto.toDto(member,resultListDtoSlice.hasNext(),resultListDtoSlice.getContent());

    }
}
