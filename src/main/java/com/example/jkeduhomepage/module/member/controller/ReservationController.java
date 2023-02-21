package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberReservationDTO;
import com.example.jkeduhomepage.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final MemberService memberService;

    @PostMapping
    public HttpEntity<Object> reservationExam(@RequestBody MemberReservationDTO memberReservationDTO) throws CoolsmsException {
        memberService.reservation(memberReservationDTO);
        return ResponseEntity.ok("예약 되었습니다.");
    }


}
