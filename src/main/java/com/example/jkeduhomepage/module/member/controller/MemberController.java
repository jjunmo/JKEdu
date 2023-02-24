package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.member.dto.MemberRequestDTO;
import com.example.jkeduhomepage.module.member.dto.MemberResponseDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
import com.example.jkeduhomepage.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.jkeduhomepage.module.member.dto.MemberResponseDTO.choiceMember;
import static com.example.jkeduhomepage.module.member.dto.MemberResponseDTO.listMember;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 내 정보
     * @return 내 정보
     */
    @GetMapping
    public HttpEntity<Object> myPage(){
        Member member=memberService.isMemberCurrent();

        return ResponseEntity.ok(MemberResponseDTO.info(member));
    }

    /**
     * 비밀번호 변경
     * @param memberRequestDTO 변경할 비밀번호
     * @return 비밀번호 변경
     */
    @PutMapping
    public HttpEntity<Object> myPasswordChange(@RequestBody MemberRequestDTO memberRequestDTO){
        Member member=memberService.isMemberCurrent();
        member.setPassword(passwordEncoder.encode(memberRequestDTO.getNewPassword()));
        memberRepository.save(member);

        return ResponseEntity.ok("비밀번호가 변경 되었습니다.");
    }


    @DeleteMapping
    public HttpEntity<Object> deleteMember(@RequestBody MemberRequestDTO memberRequestDTO){

        Member member=memberService.isMemberCurrent();

        if (!passwordEncoder.matches(memberRequestDTO.getPassword(),member.getPassword()))
            return new ResponseEntity<>("현재 비밀번호가 옳지 않습니다.",HttpStatus.BAD_REQUEST);
        else{
            memberRepository.delete(member);
            return ResponseEntity.ok("회원탈퇴 완료");
        }
    }


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

        Optional<Member> member=memberRepository.findByPhone(memberRequestDTO.getPhone());
        if(member.isPresent()) return ResponseEntity.badRequest().body("이미 가입된 회원입니다.");


        String result = memberService.save(memberRequestDTO);

        if(result.equals("NO")){
            return ResponseEntity.badRequest().body("휴대폰 인증을 완료하세요");
        }

            return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     *  로그인
     * @param memberRequestDTO ID , PW
     * @return 로그인 결과
     */
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
            return new ResponseEntity<>("조회에 실패했습니다.",HttpStatus.BAD_REQUEST);

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

        return new ResponseEntity<>("비밀번호가 변경 되었습니다.",HttpStatus.OK);
    }

    /**
     * 멤버 리스
     * @return 회원가입 멤버 리스트
     */
    @GetMapping("/management")
    public HttpEntity<Object> memberList(){

        Member member=memberService.isMemberCurrent();

        if(!member.getRole().equals(Role.ROLE_ADMIN)) return new ResponseEntity<>("잘못된 접근",HttpStatus.FORBIDDEN);

        List<Member> memberList = memberService.allList();

        return ResponseEntity.ok(listMember(memberList));
    }

    @GetMapping("/approval")
    public HttpEntity<Object> memberApprovalList(@PageableDefault Pageable pageable){

        Member member=memberService.isMemberCurrent();

        if(!member.getRole().equals(Role.ROLE_ADMIN)) return new ResponseEntity<>("잘못된 접근",HttpStatus.FORBIDDEN);

        return ResponseEntity.ok(memberService.approvalList(pageable));
    }

    @PostMapping("/approval/{id}")
    public HttpEntity<Object> memberApproval(@PathVariable Long id){

        Member member=memberService.isMemberCurrent();

        if(!member.getRole().equals(Role.ROLE_ADMIN)) return new ResponseEntity<>("잘못된 접근",HttpStatus.FORBIDDEN);

        String result =memberService.approvalMember(id);

        if(result.equals("OK")) return ResponseEntity.ok("회원가입을 승인하였습니다.");

        else return ResponseEntity.badRequest().body("운영자에게 문의하세요.");

    }



    /**
     * 휴대폰 인증
     * @param phone 인증받을 번호
     * @return 인증번호 발송
     * @throws CoolsmsException 예외처리
     */
    @PostMapping("/cert")
    public HttpEntity<Object> sendSMS(@RequestParam("phone") String phone) throws CoolsmsException {
        Optional<Member> member=memberRepository.findByPhone(phone);

        if(member.isPresent()) return ResponseEntity.badRequest().body("이미 회원입니다.");

        memberService.certifiedPhone(phone);

        return ResponseEntity.ok("인증문자 발송 완료.");
    }

    /**
     * 인증번호 확인
     * @param phone 휴대폰 번호
     * @param smscode 인증 번호
     * @return 인증 완료
     */
    @PutMapping("/cert")
    public HttpEntity<Object> sendSMSCheck(@RequestParam("phone") String phone, @RequestParam("smscode") String smscode) {
        String result = memberService.certifiedPhoneCheck(phone, smscode);
        if (result.equals("NO")) {
            return new ResponseEntity<>("휴대폰 인증을 다시 요청하세요",HttpStatus.NOT_FOUND);
        }
        if(result.equals("OK")){
            return ResponseEntity.ok("인증 완료 되었습니다.");
        }else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }

}
