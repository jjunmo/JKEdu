package com.example.jkedudemo.module.member.controller;

import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.enums.member.Status;
import com.example.jkedudemo.module.member.dto.request.ChangePasswordRequestDto;
import com.example.jkedudemo.module.member.dto.request.DeleteMemberRequestDto;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import com.example.jkedudemo.module.member.repository.MemberPhoneAuthRepository;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
    //5. 비밀번호 변경 실패 (비밀번호 불일치 )
    //6. 비밀번호 변경
    //7. 회원 탈퇴 실패 (비밀번호 불일치)
    //8. 회원 탈퇴
    //9. 아이디 찾기 실패 ( 인증 실패 )
    //10. 아이디 찾기 실패 ( 가입정보가 없음 )
    //11. 아이디 찾기 성공

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberPhoneAuthRepository memberPhoneAuthRepository;

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
    private final String URL = "/member";

    @BeforeAll
    public void all(){saveMember();}



    public void saveMember() {
        SECURITY_MEMBER_ID=memberRepository.save(Member.builder()
                .email("aaaa")
                .phone("01091097122")
                .role(Role.ROLE_ACADEMY)
                .password(passwordEncoder.encode("1234"))
                .status(Status.GREEN)
                .build()).getId();

        SECURITY_DELETE_ID=memberRepository.save(Member.builder()
                .email("aaaa2")
                .phone("01010101")
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
    @DisplayName("1. 문자 인증 요청")
    public void sendSMSTest()throws Exception{
        MultiValueMap<String, String> param = getStringStringMultiValueMap();

        mockMvc.perform(get(URL+"/cert")
                        .params(param)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("PhoneAuthCert-JOIN", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("phone").description("연락처"),
                                parameterWithName("phoneauth").description("인증유형")),
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
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("인증번호가 일치하지 않습니다."))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andDo(document("PhoneAuthCert-JOIN-CHECK-FAIL", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("phone").description("연락처"),
                                parameterWithName("smscode").description("인증번호"),
                                parameterWithName("phoneauth").description("인증유형")),
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
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("PhoneAuthCert-JOIN-CHECK-SUCCESS", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("phone").description("연락처"),
                                parameterWithName("smscode").description("인증번호"),
                                parameterWithName("phoneauth").description("인증유형")),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("4.마이페이지 실패")
    public void memberMyInfo_Fail() throws Exception{

        mockMvc.perform(get(URL+"/myinfo")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("Security Context에 인증 정보가 없습니다."))
                .andDo(document("Member-MyInfo-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("errortype","에러코드").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @WithUserDetails(value = "aaaa")
    @DisplayName("5.마이페이지")
    public void memberMyInfo() throws Exception{

        mockMvc.perform(get(URL+"/myinfo")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("email").value("aaaa"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("Member-MyInfo", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("phone", "연락처").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING),
                                getDescription("email", "이메일").type(JsonFieldType.STRING),
                                getDescription("testCount", "보유한 테스트 횟수").type(JsonFieldType.NUMBER))
                ));
    }

    @Test
    @WithUserDetails(value = "aaaa")
    @DisplayName("6.비밀번호 변경 실패")
    public void memberChangePassword_Fail() throws Exception{
        ChangePasswordRequestDto changePasswordRequestDto
                =ChangePasswordRequestDto.builder()
                .exPassword("1111")
                .newPassword("1111")
                .build();

        String paramString = objectMapper.writeValueAsString(changePasswordRequestDto);

        mockMvc.perform(put(URL+"/myinfo")
                        .content(paramString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("현재 비밀번호가 옳지 않습니다."))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andDo(document("Member-ChangePassword-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("exPassword","기존 비밀번호").type(JsonFieldType.STRING),
                                getDescription("newPassword","변경할 비밀번호").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("errortype","에러코드").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @WithUserDetails(value = "aaaa")
    @DisplayName("7.비밀번호 변경")
    public void memberChangePassword_Success() throws Exception{
        ChangePasswordRequestDto changePasswordRequestDto
                =ChangePasswordRequestDto.builder()
                .exPassword("1234")
                .newPassword("1111")
                .build();

        String paramString = objectMapper.writeValueAsString(changePasswordRequestDto);

        mockMvc.perform(put(URL+"/myinfo")
                        .content(paramString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("Member-ChangePassword-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("exPassword","기존 비밀번호").type(JsonFieldType.STRING),
                                getDescription("newPassword","변경할 비밀번호").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @WithUserDetails(value = "aaaa2")
    @DisplayName("8.회원 탈퇴 실패")
    public void memberDelete_Fail() throws Exception{
        DeleteMemberRequestDto deleteMemberRequestDto
                =DeleteMemberRequestDto.builder()
                .password("4321")
                .build();

        String paramString = objectMapper.writeValueAsString(deleteMemberRequestDto);

        mockMvc.perform(post(URL+"/myinfo")
                        .content(paramString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("비밀번호가 맞지 않습니다"))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andDo(document("Member-Delete-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("password","현재 비밀번호").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("errortype","에러코드").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @WithUserDetails(value = "aaaa2")
    @DisplayName("9.회원 탈퇴")
    public void memberDelete() throws Exception{
        DeleteMemberRequestDto deleteMemberRequestDto
                =DeleteMemberRequestDto.builder()
                .password("1111")
                .build();

        String paramString = objectMapper.writeValueAsString(deleteMemberRequestDto);

        mockMvc.perform(post(URL+"/myinfo")
                        .content(paramString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("Member-Delete-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("password","현재 비밀번호").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("10.ID 찾기 실패 ( 인증번호 )")
    public void memberFindID_Fai_PhoneAuth() throws Exception{
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("phone","010010010");
        param.add("smscode","1234");
        param.add("phoneauth","ID");

        mockMvc.perform(get(URL+"/check")
                        .params(param)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andExpect(jsonPath("errortype").value("forbidden"))
                .andExpect(jsonPath("status").value("403"))
                .andExpect(jsonPath("message").value("인증번호가 일치하지 않습니다."))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andDo(document("Member-FindId-Fail-PhoneAuth",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("phone").description("가입자 연락처"),
                                parameterWithName("smscode").description("ID찾기 인증번호"),
                                parameterWithName("phoneauth").description("인증유형")),
                        responseFields(
                                getDescription("errortype","에러타입").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
//                                getDescription("email", "이메일").type(JsonFieldType.STRING),
//                                getDescription("testCount", "보유한 테스트 횟수").type(JsonFieldType.NUMBER))
                ));
    }

    @Test
    @DisplayName("11.ID 찾기 실패 ( 해당하는 정보의 아이디없음 )")
    public void memberFindID_Fail_NoID() throws Exception{
        memberPhoneAuthRepository.save(MemberPhoneAuth.builder()
                .phone("010010010")
                .smscode("1111")
                .phoneauth(PhoneAuth.ID)
                .build());

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("phone","010010010");
        param.add("smscode","1111");
        param.add("phoneauth","ID");

        mockMvc.perform(get(URL+"/check")
                        .params(param)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errortype").value("not_found"))
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("message").value("해당 정보로 가입된 아이디가 없습니다."))
                .andDo(document("Member-FindId-Fail-NoID", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("phone").description("가입자 연락처"),
                                parameterWithName("smscode").description("ID찾기 인증번호"),
                                parameterWithName("phoneauth").description("인증유형")),
                        responseFields(
                                getDescription("errortype","에러타입").type(JsonFieldType.STRING),
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("12.ID 찾기")
    public void memberFindID_Fail_Success() throws Exception{
        memberPhoneAuthRepository.save(MemberPhoneAuth.builder()
                .phone("01091097122")
                .smscode("1111")
                .phoneauth(PhoneAuth.ID)
                .build());

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("phone","01091097122");
        param.add("smscode","1111");
        param.add("phoneauth","ID");

        mockMvc.perform(get(URL+"/check")
                        .params(param)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("200"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(document("Member-FindId-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("phone").description("가입자 연락처"),
                                parameterWithName("smscode").description("ID찾기 인증번호"),
                                parameterWithName("phoneauth").description("인증유형")),
                        responseFields(
                                getDescription("status", "성공여부").type(JsonFieldType.STRING),
                                getDescription("message", "메시지").type(JsonFieldType.STRING),
                                getDescription("email","찾은 아이디").type(JsonFieldType.STRING))
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