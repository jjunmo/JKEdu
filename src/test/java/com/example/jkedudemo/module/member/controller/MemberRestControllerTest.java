package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import com.example.jkedudemo.module.member.repository.MemberPhoneAuthRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    @Autowired
    private MemberPhoneAuthRepository memberPhoneAuthRepository;

    private final String URL = "/member";


    @BeforeAll
    public void all() {
        saveMember();
    }

    private void saveMember() {


        MemberPhoneAuth memberPhoneAuth=new MemberPhoneAuth();
        memberPhoneAuth.setSmscode("0000");
        memberPhoneAuth.setPhone("010-9109-7122");
        memberPhoneAuth.setCheckYn(YN.Y);
        memberPhoneAuth.setPhoneauth(PhoneAuth.JOIN);

        memberPhoneAuthRepository.save(memberPhoneAuth);


        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setName("준모");
        memberRequestDto.setEmail("aaaa");
        memberRequestDto.setPassword("1234");
        memberRequestDto.setPhone("010-9109-7122");
        memberRequestDto.setRole("academy");

        authService.signup(memberRequestDto);
    }


    @BeforeEach
    public void each(){
    }

    @Test
    @DisplayName("문자 인증 테스트")
    public void sendSMSTest()throws Exception{
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        param.add("phone","1234");
        param.add("phoneauth","JOIN");

        mockMvc.perform(get(URL+"/cert")
                .params(param)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print());

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