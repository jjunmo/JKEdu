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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("dev")
@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class MemberRestControllerTest {

    //TODO: Test 코드
    //1. 문자 인증 요청
    //2. 회원가입 실패 (정보 미기재)
    //3. 회원가입 성공
    //4.

    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberPhoneAuthRepository memberPhoneAuthRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

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


    @Test
    @DisplayName("1. 문자 인증 요청")
    public void sendSMSTest()throws Exception{
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        param.add("phone","1234");
        param.add("phoneauth","JOIN");

        mockMvc.perform(get(URL+"/cert")
                        .params(param)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("phoneAuthCert-JOIN", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }



    private FieldDescriptor getDescription(String name, String description) {
        return fieldWithPath(name)
                .description(description);
    }

}