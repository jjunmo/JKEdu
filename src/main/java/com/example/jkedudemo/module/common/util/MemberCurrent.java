package com.example.jkedudemo.module.common.util;

import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCurrent {

    private static MemberRepository memberRepository;

    public static Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new MyInternalServerException("로그인 유저 정보가 없습니다"));
    }
}
