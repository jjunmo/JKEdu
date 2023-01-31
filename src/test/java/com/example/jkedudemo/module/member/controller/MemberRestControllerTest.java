package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import com.example.jkedudemo.module.member.repository.MemberPhoneAuthRepository;
import com.example.jkedudemo.module.member.service.AuthService;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("dev")
@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class MemberRestControllerTest {

    //1. 문자 인증 요청
    //2. 휴댜폰 인증 확인실패(잘못된 인증번호)
    //3. 휴대폰 인증 성공
    //4. 마이페이지 확인
    //5. 비밀번호 변경
    //6. 회원 탈퇴

    private MockMvc mockMvc;

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
        MultiValueMap<String, String> param = getStringStringMultiValueMap();

        mockMvc.perform(get(URL+"/cert")
                        .params(param)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL))
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("PhoneAuthCert-JOIN", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("2. 회원가입 휴대폰 인증 실패")
    public void sendSMSCheckTest_Fail() throws Exception{
        MultiValueMap<String, String> param1=getStringStringMultiValueMap();

        mockMvc.perform(get(URL+"/cert")
                        .params(param1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print());

        MultiValueMap<String, String> param2 = new LinkedMultiValueMap<>();

        param2.add("phone","1234");
        param2.add("smscode","1111");
        param2.add("phoneauth","JOIN");

        mockMvc.perform(get(URL+"/cert/ex")
                        .params(param2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("인증번호가 일치하지 않습니다."))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andDo(document("PhoneAuthCert-JOIN-CHECK-FAIL", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("errortype","에러코드").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("3. 회원가입 휴대폰 인증 성공")
    public void sendSMSCheckTest_Success() throws Exception{

        MemberPhoneAuth memberPhoneAuth=MemberPhoneAuth.builder()
                        .phone("1234")
                        .smscode("1111")
                        .phoneauth(PhoneAuth.JOIN)
                        .build();

        memberPhoneAuthRepository.save(memberPhoneAuth);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        param.add("phone","1234");
        param.add("smscode","1111");
        param.add("phoneauth","join");

        mockMvc.perform(get(URL+"/cert/ex")
                        .params(param)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("PhoneAuthCert-JOIN-CHECK-SUCCESS", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    private static MultiValueMap<String, String> getStringStringMultiValueMap() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        param.add("phone","1234");
        param.add("phoneauth","JOIN");

        return param;
    }


    private FieldDescriptor getDescription(String name, String description) {
        return fieldWithPath(name)
                .description(description);
    }

}