package com.example.jkeduhomepage.module.member.service;


import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.member.dto.MemberInsertDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(MemberInsertDTO memberInsertDTO){
        Member member = new Member();
        member.setLoginId(memberInsertDTO.getLoginId());
        member.setPassword(memberInsertDTO.getPassword());
        member.setEmail(memberInsertDTO.getEmail());
        member.setName(memberInsertDTO.getName());
        member.setPhone(memberInsertDTO.getPhone());
        member.setStatus(Status.WHITE);

        return memberRepository.save(member);
    }

    public Optional<Member> getMember(Long id){
        return memberRepository.findById(id);
    }

    public Optional<Member> updateMember (MemberUpdateDTO memberUpdateDTO,Long id){

        Optional<Member> memberOptional= memberRepository.findById(id);

        if(memberOptional.isEmpty()) return memberOptional;

        Member member =memberOptional.get();

        member.setEmail(memberUpdateDTO.getEmail());
        member.setPassword(memberUpdateDTO.getPassword());

        return memberOptional;
    }

    public List<Member> allList(){
        return memberRepository.findAll();
    }

}
