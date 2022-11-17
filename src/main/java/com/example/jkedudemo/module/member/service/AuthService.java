package com.example.jkedudemo.module.member.service;

import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.util.Cer;
import com.example.jkedudemo.module.common.enums.member.Phoneauth;
import com.example.jkedudemo.module.common.enums.member.Status;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.jwt.TokenProvider;
import com.example.jkedudemo.module.member.dto.TokenDto;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.dto.response.*;
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


    @Transactional
    public MemberStatusOkResponseDto signup(MemberRequestDto requestDto) {

        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new MyInternalServerException("이미 가입되어 있는 회원입니다");
        }

        //비밀번호 인코딩
        Member reqMember = requestDto.toMember(passwordEncoder);
        Member member = memberRepository.save(reqMember);


        //휴대폰 인증 여부
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneAndCheckYnAndPhoneauth(requestDto.getPhone(),YN.Y, Phoneauth.JOIN);

        if(memberPhoneAuthOptional.isEmpty()){
            throw new MyInternalServerException("인증을 완료하세요");
        }

        //계정 상태 체크
        if (memberRepository.existsByPhoneAndStatus(requestDto.getPhone(), Status.YELLOW)) {
            throw new MyInternalServerException("회원가입이 정지된 고객정보입니다. 고객센터에 문의하세요.");
        }else {
            memberPhoneAuthOptional.get().setMember(member);
        }

        //학원 학생인지 확인 (학원코드 발행)
        if (member.getRole().equals(Role.ROLE_ACADEMY)) {
            String academyId = Cer.getStrCerNum(requestDto.getPhone());
            Optional<Member> academyIdCheck = memberRepository.findByAcademyId(academyId);
            if (academyIdCheck.isEmpty()) {
                member.setAcademyId(academyId);
            }
        }

        member.setStatus(Status.GREEN);
        return MemberStatusOkResponseDto.statusOk();

    }

    @Transactional
    public TokenDto login(MemberRequestDto requestDto) {

        Optional<Member> memberOptional = memberRepository.findByEmailAndStatusIn(requestDto.getEmail(), List.of(Status.GREEN ,Status.YELLOW));
        if (memberOptional.isEmpty()) {
            throw new MyInternalServerException("존재하지 않는 회원입니다.");
        }
        Member reqMember = memberOptional.get();

        if (!passwordEncoder.matches(requestDto.getPassword(), reqMember.getPassword())) {
            throw new MyInternalServerException("비밀번호가 틀렸습니다");
        }

        //TODO: 비밀번호 유효성 체크 , 토큰이 남아있음.

        //Status 체크

        if (reqMember.getStatus().equals(Status.YELLOW)) {
            throw new MyInternalServerException("정지 대상입니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication,reqMember.getName());

    }

}