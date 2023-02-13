package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.jwt.TokenDto;
import com.example.jkeduhomepage.module.member.dto.MemberRequestDTO;
import com.example.jkeduhomepage.module.member.dto.MemberResponseDTO;
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

import static com.example.jkeduhomepage.module.member.dto.MemberResponseDTO.*;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     * @param memberRequestDTO 입력한 정보
     * @return 회원가입 member
     */
    @PostMapping
    public HttpEntity<Object> save(@RequestBody MemberRequestDTO memberRequestDTO){
        if(memberRequestDTO.getLoginId().equals("")) return new ResponseEntity<>("ID를 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberRequestDTO.getPassword().equals("")) return new ResponseEntity<>("비밀번호를 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberRequestDTO.getEmail().equals("")) return new ResponseEntity<>("이메일을 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberRequestDTO.getName().equals("")) return new ResponseEntity<>("이름을 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberRequestDTO.getPhone().equals("")) return new ResponseEntity<>("휴대폰 번호를 입력하세요.",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(saveMember(memberService.save(memberRequestDTO)),HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpEntity<Object> login(@RequestBody MemberRequestDTO memberRequestDTO){
        if(memberRequestDTO.getLoginId().equals("")) return  ResponseEntity.badRequest().body("아이디를 입력하세요.");
        if(memberRequestDTO.getPassword().equals("")) return ResponseEntity.badRequest().body("비밀번호를 입력하세요.");

        Object result=memberService.login(memberRequestDTO);

        if(result.equals("id_fail")){
            return ResponseEntity.badRequest().body("존재하지 않는 아이디 입니다.");
        }

        if(result.equals("pw_fail")){
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }


        return ResponseEntity.ok().body(result);
    }

    /**
     * 멤버 선택
     * @param id PK
     * @return member 정보
     */
    @GetMapping("/{id}")
    public HttpEntity<Object> choice(@PathVariable Long id){
        Optional<Member> memberOptional=memberService.getMember(id);

        if(memberOptional.isEmpty())
            return new ResponseEntity<>("얘는 누구냐",HttpStatus.BAD_REQUEST);

        Member member=memberOptional.get();

        return new ResponseEntity<>(choiceMember(member),HttpStatus.OK);
    }

    /**
     * 회원정보 수정
     * @param memberUpdateDTO email , password
     * @param id memberId
     * @return member
     */
    @PutMapping("/{id}")
    public HttpEntity<Object> update(@RequestBody MemberUpdateDTO memberUpdateDTO, @PathVariable Long id){

        if(memberUpdateDTO.getEmail().equals("")) return new ResponseEntity<>("이메일을 입력하세요.",HttpStatus.BAD_REQUEST);
        if(memberUpdateDTO.getPassword().equals("")) return new ResponseEntity<>("비밀번호를 입력하세요.",HttpStatus.BAD_REQUEST);

        Optional<Member> memberOptional=memberService.updateMember(memberUpdateDTO,id);

        if(memberOptional.isEmpty()) return new ResponseEntity<>("해당 정보가 없습니다.",HttpStatus.BAD_REQUEST);

        Member member=memberOptional.get();

        return new ResponseEntity<>("비밀번호가 변경 되었습니다.",HttpStatus.OK);
    }

    /**
     * 멤버 리스트
     * @return memberList
     */
    @GetMapping
    public HttpEntity<Collection<MemberResponseDTO>> memberList(){
        List<Member> memberList = memberService.allList();

        return new ResponseEntity<>(listMember(memberList),HttpStatus.OK);
    }
}
