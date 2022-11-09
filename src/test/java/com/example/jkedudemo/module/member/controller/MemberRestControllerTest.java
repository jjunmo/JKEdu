package com.example.jkedudemo.module.member.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MemberRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("문자 인증 테스트")
    public void sendSMSTest()throws Exception{
        this.mockMvc.perform(get("/member/sendsms"))
                .andExpect(status().isOk());

    }

}