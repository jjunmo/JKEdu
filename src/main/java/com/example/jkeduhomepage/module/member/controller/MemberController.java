package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberInsertDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public HttpEntity<Object> save(@RequestBody MemberInsertDTO memberInsertDTO){
        if(memberInsertDTO.getLoginId().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getPassword().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getEmail().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getName().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(memberInsertDTO.getPhone().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(memberService.save(memberInsertDTO),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public HttpEntity<Object> choiceMember(@PathVariable Long id){
        Optional<Member> memberOptional=memberService.getMember(id);

        if(memberOptional.isEmpty())
            return new ResponseEntity<>("얘는 누구냐",HttpStatus.BAD_REQUEST);

        Member member=memberOptional.get();

        return new ResponseEntity<>(member,HttpStatus.OK);

    }

}
