package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.service.AuthService;
import com.example.jkedudemo.module.member.service.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class MemberRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    private final String URL = "/member";

    public void all() {
        saveEvent(null,"1234","1234","123456789","1234",Role.ROLE_USER,"aaa12");
    }

    @BeforeEach
    public void each(){
    }

    @Test
    @DisplayName("문자 인증 테스트")
    public void sendSMSTest()throws Exception{
        mockMvc.perform(get(URL+"/cert"))
                .andExpect(status().isOk());
    }

    private void saveEvent(Long id, String email , String password , String phone , String name , Role role, String academyId) {
        MemberRequestDto memberRequestDto = new MemberRequestDto(id,email,password,phone,name,role,academyId);
        authService.signup(memberRequestDto);
    }


    @Test
    @DisplayName("hateoas 값 확인 (테스트 X)")
    public void test() throws Exception {
        mockMvc.perform(
                        get(URL + "/hateoas")
                                .accept(MediaType.ALL)
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                )
                .andDo(print());

    }

}