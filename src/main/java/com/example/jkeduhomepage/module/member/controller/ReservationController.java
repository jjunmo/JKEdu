package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberReservationDTO;
import com.example.jkeduhomepage.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReservationController {

    private final MemberService memberService;

    @GetMapping
    public HttpEntity<Object> reservationExam(MemberReservationDTO memberReservationDTO) throws CoolsmsException {
        memberService.reservation(memberReservationDTO);
        return ResponseEntity.ok("예약 되었습니다.");
    }


}
