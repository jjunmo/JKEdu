package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberInsertDTO;
import com.example.jkeduhomepage.module.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@AutoConfigureRestDocs
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MemberControllerTest {
    private final String URL = "/member";

    @Autowired
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public void all(){
        saveMember();
    }

    private void saveMember(){
        MemberInsertDTO memberInsertDTO=new MemberInsertDTO();
        memberInsertDTO.setLoginId("aaa");
        memberInsertDTO.setPassword("123456");
        memberInsertDTO.setEmail("aa@aa");
        memberInsertDTO.setName("momo");
        memberInsertDTO.setPhone("123456789");

        memberService.save(memberInsertDTO);
    }

    @BeforeEach
    public void each(){
    }

    @Test
    @DisplayName("1. Member 저장 실패")
    public void member_save_fail() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberInsertDTO memberInsertDTO = new MemberInsertDTO();

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberInsertDTO);

        mockMvc.perform(
                        post(URL)
                                .content(paramString) // body
                                .accept(MediaType.ALL)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("2. Member 저장 성공")
    public void event_save() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        MemberInsertDTO memberInsertDTO = new MemberInsertDTO();
        memberInsertDTO.setLoginId("memeberLoginId");
        memberInsertDTO.setPassword("memberPassword");
        memberInsertDTO.setEmail("memberEmail");
        memberInsertDTO.setName("memberName");
        memberInsertDTO.setPhone("memberPhone");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberInsertDTO);

        mockMvc.perform(
                        post(URL)
                                .content(paramString) // body
                                .accept(MediaType.ALL)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("loginId").value("memeberLoginId"))
                .andExpect(jsonPath("password").value("memberPassword"))
                .andExpect(jsonPath("email").value("memberEmail"))
                .andExpect(jsonPath("name").value("memberName"))
                .andExpect(jsonPath("phone").value("memberPhone"))
                .andExpect(jsonPath("status").value("WHITE"));
    }

}