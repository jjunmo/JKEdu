package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberInsertDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void all() {
        saveMember();
    }

    private void saveMember() {
        MemberInsertDTO memberInsertDTO = new MemberInsertDTO();
        memberInsertDTO.setLoginId("aaa");
        memberInsertDTO.setPassword("123456");
        memberInsertDTO.setEmail("aa@aa");
        memberInsertDTO.setName("momo");
        memberInsertDTO.setPhone("123456789");

        memberService.save(memberInsertDTO);
    }

    @BeforeEach
    public void each() {
    }

    // 1. Member 저장 실패
    // 2. Member 저장
    // 3. Member 리스트 조회
    // 4. Member 조회
    // 5. Member 조회 실패 ( idx 값이 없을 때 )
    // 6. Member 수정
    // 7. Member 수정 실패 ( idx 값이 없을 때 )
    // 8. Member 수정 실패 ( title이 빈 값 일때 )

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
    public void member_save() throws Exception {
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

    @Test
    @DisplayName("3. 멤버 리스트 조회")
    public void member_list() throws Exception {
        saveMember();
        saveMember();
        saveMember();
        mockMvc.perform(
                        get(URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andExpect(jsonPath("[0].loginId").value("aaa"))
                .andExpect(jsonPath("[0].password").value("123456"))
                .andExpect(jsonPath("[0].email").value("aa@aa"))
                .andExpect(jsonPath("[0].name").value("momo"))
                .andExpect(jsonPath("[0].phone").value("123456789"))
                .andExpect(jsonPath("[0].status").value("WHITE"))
                .andExpect(jsonPath("[0].createDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("[0].updateDate").value(String.valueOf(LocalDate.now())))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("4. member 조회 (id)")
    public void member() throws Exception {

        // When && Then
        // 조회
        mockMvc.perform(
                        get(URL + "/{id}", 1).contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andExpect(jsonPath("loginId").value("aaa"))
                .andExpect(jsonPath("password").value("123456"))
                .andExpect(jsonPath("email").value("aa@aa"))
                .andExpect(jsonPath("name").value("momo"))
                .andExpect(jsonPath("phone").value("123456789"))
                .andExpect(jsonPath("status").value("WHITE"))
                .andExpect(jsonPath("createDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("updateDate").value(String.valueOf(LocalDate.now())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("5. member 조회 실패 ( id 값이 없을 때 )")
    public void member_fail() throws Exception {
        mockMvc.perform(
                        get(URL + "/1000")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("6. member 수정")
    public void member_update() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();
        memberUpdateDTO.setEmail("modifyEmail");
        memberUpdateDTO.setPassword("modifyPassword");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberUpdateDTO);

        mockMvc.perform(
                        put(URL + "/1")
                                .content(paramString)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("loginId").value("aaa"))
                .andExpect(jsonPath("password").value("modifyPassword"))
                .andExpect(jsonPath("email").value("modifyEmail"))
                .andExpect(jsonPath("name").value("momo"))
                .andExpect(jsonPath("phone").value("123456789"))
                .andExpect(jsonPath("status").value("WHITE"))
                .andExpect(jsonPath("createDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("updateDate").value(String.valueOf(LocalDate.now())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("7. member {id} 값이 없을 때")
    public void member_update_fail() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();
        memberUpdateDTO.setEmail("modifyEmail");
        memberUpdateDTO.setPassword("modifyPassword");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberUpdateDTO);

        mockMvc.perform(
                        put(URL + "/1000")
                                .content(paramString)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("8. member update 실패 (body 가 없을 때)")
    public void member_update_fail2() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberUpdateDTO);

        mockMvc.perform(
                        put(URL + "/1")
                                .content(paramString)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


}