package com.example.jkedudemo.module.member.service;

import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.common.enums.RoleType;
import com.example.jkedudemo.module.common.enums.Status;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.jwt.TokenProvider;
import com.example.jkedudemo.module.member.dto.TokenDto;
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

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final MemberPhoneAuthRepository memberPhoneAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Transactional
    public MemberResponseDto signup(MemberRequestDto requestDto) {


        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        //비밀번호 인코딩
        Member member = requestDto.toMember(passwordEncoder);

        //권한에 따라 학원아이디 부여
        if(requestDto.getRoleType().equals(RoleType.ROLE_ACADEMY)){
                member.setAcademyId(member.getId());
        }

        //휴대폰 인증 여부
        Optional<MemberPhoneAuth> memberPhoneAuthOptional = memberPhoneAuthRepository.findByPhoneNumberAndCheckYnAndPhoneAuthType(requestDto.getPhoneNumber(),YN.Y, PhoneAuthType.JOIN);

        if(memberPhoneAuthOptional.isEmpty()){
            throw new IllegalArgumentException("인증을 완료하세요");
        }

        //계정 상태 체크
        if (memberRepository.existsByPhoneNumberAndStatus(requestDto.getPhoneNumber(), Status.YELLOW)) {
            throw new IllegalArgumentException("정지대상입니다 고객센터에 문의하세요.");
        }else {
            memberPhoneAuthOptional.get().setMember(member);
        }

        return MemberResponseDto.of(memberRepository.save(member));

    }

    @Transactional
    public TokenDto login(MemberRequestDto requestDto) {

        // TODO: Status 체크
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);


        return tokenProvider.generateTokenDto(authentication);
    }

}
