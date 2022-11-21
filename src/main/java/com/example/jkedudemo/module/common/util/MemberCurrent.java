package com.example.jkedudemo.module.common.util;

import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Deprecated
public class MemberCurrent {

    private MemberRepository memberRepository;


}
