package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("dev")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    private final String URL = "/auth";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    // 1. 멤버 저장 실패
    // 2. 멤버 저장 저장

    @Test
    @DisplayName("1. Member 저장 실패")
    public void member_register_fail() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        MemberRequestDto memberRequestDto = new MemberRequestDto();

        String paramString =objectMapper.writeValueAsString(memberRequestDto);

        mockMvc.perform(
                post(URL+"/register")
                        .content(paramString) // body
                        .accept(MediaType.ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

            }

    }

