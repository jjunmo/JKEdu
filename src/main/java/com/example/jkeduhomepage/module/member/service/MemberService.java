package com.example.jkeduhomepage.module.member.service;


import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.jwt.TokenProvider;
import com.example.jkeduhomepage.module.member.dto.MemberRequestDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final AuthenticationManagerBuilder managerBuilder;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    @Transactional
    public Member save(MemberRequestDTO memberRequestDTO){
        Member member = new Member();
        member.setLoginId(memberRequestDTO.getLoginId());
        member.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));
        member.setEmail(memberRequestDTO.getEmail());
        member.setName(memberRequestDTO.getName());
        member.setPhone(memberRequestDTO.getPhone());
        member.setStatus(Status.GREEN);
        member.setRole(Role.ROLE_USER);

        return memberRepository.save(member);
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

        Member member =memberOptional.get();

        member.setEmail(memberUpdateDTO.getEmail());
        member.setPassword(memberUpdateDTO.getPassword());

        return memberOptional;
    }

    public List<Member> allList(){
        return memberRepository.findAll();
    }

}
