package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.enums.member.Status;
import com.example.jkedudemo.module.member.dto.request.MemberRequestDto;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import com.example.jkedudemo.module.member.repository.MemberPhoneAuthRepository;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("dev")
@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    //1.회원가입 실패 ( 휴대폰 미인증 )
    //2.회원가입 실패 ( 이메일 중복 )
    //3.회원가입 성공
    //4.로그인 실패
    //5.로그인 성공
    //6.로그아웃
    //7.RefreshToken 재발급 실패
    //8.RefreshToken 재발급 성공

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private MemberPhoneAuthRepository memberPhoneAuthRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Long SECURITY_MEMBER_ID;

    private Long SECURITY_DELETE_ID;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }
    private final String URL = "/auth";

    @BeforeAll
    public void all(){saveMember();}



    public void saveMember() {
        SECURITY_MEMBER_ID=memberRepository.save(Member.builder()
                .email("aaaa")
                .phone("01091097122")
                .name("momo")
                .role(Role.ROLE_ACADEMY)
                .password(passwordEncoder.encode("1234"))
                .status(Status.GREEN)
                .build()).getId();

        SECURITY_DELETE_ID=memberRepository.save(Member.builder()
                .email("aaaa2")
                .phone("01010101")
                .name("momo2")
                .role(Role.ROLE_ACADEMY)
                .password(passwordEncoder.encode("1111"))
                .status(Status.GREEN)
                .build()).getId();
    }

    @AfterTransaction
    public void accountCleanup() {
        memberRepository.deleteById(SECURITY_MEMBER_ID);
        memberRepository.deleteById(SECURITY_DELETE_ID);
    }


    @Test
    @DisplayName("1. 회원가입 실패 (휴대폰 인증 미완료)")
    public void memberSaveTest_Fail_PhoneAuth()throws Exception{

        MemberRequestDto memberRequestDto=MemberRequestDto.builder()
                .email("bbbb")
                .password("123456")
                .phone("00000000")
                .name("abcd")
                .role("user")
                .build();

        String paramString = objectMapper.writeValueAsString(memberRequestDto);

        mockMvc.perform(post(URL+"/member/register")
                       .content(paramString)
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("인증을 완료하세요."))
                .andDo(document("Member-Save-Fail-PhoheAuth", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("email", "회원 로그인 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING),
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("role", "일반 회원 / 학원").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("errortype","에러코드").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("2. 회원가입 실패 (중복된 이메일)")
    public void memberSaveTest_Fail_ID()throws Exception{

        MemberRequestDto memberRequestDto=MemberRequestDto.builder()
                .email("aaaa")
                .password("123456")
                .phone("010-9109-7122")
                .name("asdf")
                .role("user")
                .build();

        String paramString = objectMapper.writeValueAsString(memberRequestDto);

        mockMvc.perform(post(URL+"/member/register")
                        .content(paramString)
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("이미 가입되어 있는 회원입니다."))
                .andDo(document("Member-Save-Fail-EMAIL", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("email", "회원 로그인 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING),
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("role", "일반 회원 / 학원").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("errortype","에러코드").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("3. 회원가입 성공")
    public void memberSaveTest_Success()throws Exception{

        MemberPhoneAuth memberPhoneAuth=new MemberPhoneAuth();
        memberPhoneAuth.setSmscode("0000");
        memberPhoneAuth.setPhone("010-1234-1234");
        memberPhoneAuth.setCheckYn(YN.Y);
        memberPhoneAuth.setPhoneauth(PhoneAuth.JOIN);

        memberPhoneAuthRepository.save(memberPhoneAuth);

        MemberRequestDto memberRequestDto=MemberRequestDto.builder()
                .email("bbbb")
                .password("123456")
                .phone("010-1234-1234")
                .name("asdf")
                .role("academy")
                .build();

        String paramString = objectMapper.writeValueAsString(memberRequestDto);

        mockMvc.perform(post(URL+"/member/register")
                        .content(paramString)
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(document("Member-Save-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("email", "회원 로그인 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING),
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("role", "일반 회원 / 학원").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("4. 로그인 실패")
    public void memberLogin_Fail()throws Exception{

        MemberRequestDto memberRequestDto=MemberRequestDto.builder()
                .email("aaaa2")
                .password("12345")
                .build();

        String paramString = objectMapper.writeValueAsString(memberRequestDto);

        mockMvc.perform(post(URL+"/member/login")
                        .content(paramString)
                        .header("User-Agent","aaa")
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errortype").value("not_found"))
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("message").value("아이디 혹은 비밀번호가 일치하지 않습니다."))
                .andDo(document("MemberLogin-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("email", "회원 로그인 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("errortype","에러코드").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("5. 로그인 성공")
    public void memberLogin_Success()throws Exception{

        MemberRequestDto memberRequestDto=MemberRequestDto.builder()
                .email("aaaa")
                .password("1234")
                .build();

        String paramString = objectMapper.writeValueAsString(memberRequestDto);

        mockMvc.perform(post(URL+"/member/login")
                        .content(paramString)
                        .header("User-Agent","aaa")
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("grantType").value("bearer"))
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(document("MemberLogin-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("email", "회원 로그인 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("grantType","허가유형").type(JsonFieldType.STRING),
                                getDescription("status", "상태코드").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING),
                                getDescription("accessToken", "발급된 accessToken").type(JsonFieldType.STRING),
                                getDescription("refreshToken","발급된 refreshToken").type(JsonFieldType.STRING),
                                getDescription("keyId","로그인한 멤버의 고유번호").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("6. 로그아웃 실패")
    public void memberLogout_Fail()throws Exception{

        mockMvc.perform(post(URL+"/member/logout")
                        .header("User-Agent","aaa")
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("로그인 유저 정보가 없습니다."))
                .andDo(document("MemberLogout-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("errortype", "에러유형").type(JsonFieldType.STRING),
                                getDescription("status", "상태코드").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @WithUserDetails("aaaa")
    @DisplayName("7. 로그아웃")
    public void memberLogout()throws Exception{
        MemberRequestDto memberRequestDto=MemberRequestDto.builder()
                .email("aaaa")
                .password("1234")
                .build();

        String paramString = objectMapper.writeValueAsString(memberRequestDto);

        mockMvc.perform(post(URL+"/member/login")
                .content(paramString)
                .header("User-Agent","aaa")
                .accept(MediaType.ALL)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        mockMvc.perform(post(URL+"/member/logout")
                        .header("User-Agent","aaa")
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(document("MemberLogout-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("status", "상태코드").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("8. RefreshToken 재발급")
    public void memberRefreshToken() throws Exception{
        MemberRequestDto memberRequestDto=MemberRequestDto.builder()
                .email("bbbb")
                .password("123456")
                .build();

        String paramString = objectMapper.writeValueAsString(memberRequestDto);

        MvcResult result = mockMvc.perform(post(URL+"/member/login")
                .content(paramString)
                .header("User-Agent","aaa")
                .accept(MediaType.ALL)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        JSONParser paser = new JSONParser();

        JSONObject jsonObject= (JSONObject) paser.parse(result.getResponse().getContentAsString());

        HashMap<String,String> hashMap2=new HashMap<>();
        hashMap2.put("refreshToken", (String) jsonObject.get("refreshToken"));

        String paramString2=objectMapper.writeValueAsString(hashMap2);

        mockMvc.perform(post(URL+"/refresh")
                        .content(paramString2)
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("refreshResponseMap.message").value("Refresh 토큰을 통한 Access Token 생성이 완료되었습니다."))
                .andExpect(jsonPath("refreshResponseMap.status").value("200"))
                .andDo(document("RefreshTokenValidate", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("refreshToken","검증할 refreshToken")),
                        responseFields(
                                getDescription("refreshResponseMap.message", "재발급 메세지").type(JsonFieldType.STRING),
                                getDescription("refreshResponseMap.accessToken", "새로 발급된 AccessToken").type(JsonFieldType.STRING),
                                getDescription("refreshResponseMap.status","상태코드"))
                ));
    }




    private FieldDescriptor getDescription(String name, String description) {
        return fieldWithPath(name)
                .description(description);
    }

}