package com.example.jkedudemo.module.member.service;

import com.example.jkedudemo.module.common.enums.Role;
import com.example.jkedudemo.module.common.util.Cer;
import com.example.jkedudemo.module.common.enums.Phoneauth;
import com.example.jkedudemo.module.common.enums.Status;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.jwt.TokenProvider;
import com.example.jkedudemo.module.member.dto.TokenDto;
import com.example.jkedudemo.module.member.dto.request.FindPasswordRequestDto;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import com.example.jkedudemo.module.member.repository.MemberPhoneAuthRepository;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final MemberPhoneAuthRepository memberPhoneAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final MemberService memberService;


    @Transactional
    public MemberResponseDto signup(MemberRequestDto requestDto) {

        //TODO:이미 가입된 휴대전화 ,재인증 ,인증요청 횟수

        if (memberRepository.existsByEmailAndStatusIn(requestDto.getEmail(), List.of(Status.GREEN,Status.YELLOW))) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        //비밀번호 인코딩
        Member reqMember = requestDto.toMember(passwordEncoder);
        Member member = memberRepository.save(reqMember);


        //휴대폰 인증 여부
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYnAndPhoneauth(requestDto.getPhone(),YN.Y, Phoneauth.JOIN);

        if(memberPhoneAuthOptional.isEmpty()){
            throw new IllegalArgumentException("인증을 완료하세요");
        }

        //계정 상태 체크
        if (memberRepository.existsByPhoneAndStatus(requestDto.getPhone(), Status.YELLOW)) {
            throw new IllegalArgumentException("정지대상입니다 고객센터에 문의하세요.");
        }else {
            memberPhoneAuthOptional.get().setMember(member);
        }

        //학원 학생인지 확인 (학원코드 발행)
        // TODO:학원코드 중복확인
        if (member.getRole().equals(Role.ROLE_ACADEMY)) {
            String academyId = Cer.getCerStrNum(requestDto.getPhone());
            Optional<Member> academyIdCheck = memberRepository.findByAcademyId(academyId);
            if (academyIdCheck.isEmpty()) {
                member.setAcademyId(academyId);
            }
        }


        member.setStatus(Status.GREEN);
        return MemberResponseDto.of(memberRepository.save(member));

    }

    @Transactional
    public TokenDto login(MemberRequestDto requestDto) {


        Optional<Member> memberOptional = memberRepository.findByEmail(requestDto.getEmail());
        if(memberOptional.isEmpty()){
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

        //Status 체크
        Member reqMember = memberOptional.get();
        if(reqMember.getStatus().equals(Status.YELLOW)){
            throw new RuntimeException("정지 대상입니다.");
        }


        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);


        return tokenProvider.generateTokenDto(authentication);
    }


    //TODO:비밀번호 찾기 안됨.
    @Transactional
    public TokenDto getPassword(FindPasswordRequestDto request){

        String result = memberService.certifiedPhoneCheck(request.getPhone(),request.getSmscode(),request.getPhoneauth());


        if(result.equals("OK")){
            Optional<Member> memberOptional = memberRepository.findByPhoneAndStatusIn(request.getPhone(),List.of(Status.GREEN,Status.YELLOW));
            if(memberOptional.isEmpty()){
                throw new RuntimeException("해당 정보로 가입된 아이디가 없습니다.");
            }
            Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndPhoneauth(request.getPhone(),Phoneauth.PW);
            if(memberPhoneAuthOptional.isEmpty()){
                throw new RuntimeException("휴대폰 인증을 완료하세요.");
            }
            MemberPhoneAuth memberPhoneAuth = memberPhoneAuthOptional.get();
            Member member=memberOptional.get();
            memberPhoneAuth.setMember(member);
            memberPhoneAuth.setCheckYn(YN.Y);


            UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication(member.getEmail(), member.getPassword());
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);


            return tokenProvider.generateTokenDto(authentication);
        }

        throw new RuntimeException("인증이 실패하였습니다.");

    }

}
