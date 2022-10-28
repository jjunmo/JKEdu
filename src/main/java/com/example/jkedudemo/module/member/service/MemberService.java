package com.example.jkedudemo.module.member.service;


import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.member.dto.response.MemberResponseDto;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

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
        if (!passwordEncoder.matches(exPassword, member.getMember_password())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.setMember_password(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));
    }


}
