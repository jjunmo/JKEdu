package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.member.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("dev")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MemberRestControllerTest {

    private final String URL = "/member";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;




}