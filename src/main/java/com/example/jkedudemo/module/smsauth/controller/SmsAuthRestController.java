package com.example.jkedudemo.module.smsauth.controller;

import com.example.jkedudemo.module.smsauth.service.SmsAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SmsAuthRestController {

    private final SmsAuthService smsAuthService;

    @GetMapping("/sendSMS")
    public String sendSMS(String phoneNumber) {

        Random random  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(random.nextInt(10));
            numStr+=ran;
        }

        log.info("수신자 번호 : " + phoneNumber);
        log.info("인증번호 : " + numStr);
        smsAuthService.certifiedPhoneNumber(phoneNumber,numStr);
        return numStr;
    }

}
