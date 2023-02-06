package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberInsertDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     * @param memberInsertDTO 입력한 정보
     * @return 회원가입 member
     */
    @PostMapping
    public HttpEntity<Object> save(@RequestBody MemberInsertDTO memberInsertDTO){
        if(memberInsertDTO.getLoginId().equals("")) return new ResponseEntity<>("ID를 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getPassword().equals("")) return new ResponseEntity<>("비밀번호를 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getEmail().equals("")) return new ResponseEntity<>("이메일을 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getName().equals("")) return new ResponseEntity<>("이름을 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getPhone().equals("")) return new ResponseEntity<>("휴대폰 번호를 입력하세요.",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(memberService.save(memberInsertDTO),HttpStatus.OK);
    }

    /**
     * 멤버 선택
     * @param id PK
     * @return member 정보
     */
    @GetMapping("/{id}")
    public HttpEntity<Object> choiceMember(@PathVariable Long id){
        Optional<Member> memberOptional=memberService.getMember(id);

        if(memberOptional.isEmpty())
            return new ResponseEntity<>("얘는 누구냐",HttpStatus.BAD_REQUEST);

        Member member=memberOptional.get();

        return new ResponseEntity<>(member,HttpStatus.OK);
    }

    /**
     * 회원정보 수정
     * @param memberUpdateDTO email , password
     * @param id memberId
     * @return member
     */
    @PutMapping("/{id}")
    public HttpEntity<Object> updateMember(@RequestBody MemberUpdateDTO memberUpdateDTO, @PathVariable Long id){

        if(memberUpdateDTO.getEmail().equals("")) return new ResponseEntity<>("이메일을 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberUpdateDTO.getPassword().equals("")) return new ResponseEntity<>("비밀번호를 입력하세요.",HttpStatus.BAD_REQUEST);

        Optional<Member> memberOptional=memberService.updateMember(memberUpdateDTO,id);

        if(memberOptional.isEmpty()) return new ResponseEntity<>("해당 정보가 없습니다.",HttpStatus.BAD_REQUEST);

        Member member=memberOptional.get();

        return new ResponseEntity<>(member,HttpStatus.OK);
    }

    /**
     * 멤버 리스트
     * @return memberList
     */
    @GetMapping
    public HttpEntity<Collection<Member>> memberList(){

        List<Member> memberList = memberService.allList();

        return new ResponseEntity<>(memberList,HttpStatus.OK);
    }
}
