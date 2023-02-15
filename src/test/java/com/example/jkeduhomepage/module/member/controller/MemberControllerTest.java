package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.common.enums.YN;
import com.example.jkeduhomepage.module.member.dto.MemberRequestDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.MemberPhoneAuth;
import com.example.jkeduhomepage.module.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MemberControllerTest {
    private final String URL = "/member";

    @Autowired
    MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }


    @BeforeAll
    public void all() throws CoolsmsException {
        saveMember();
    }

    private void saveMember() throws CoolsmsException {
        MemberPhoneAuth memberPhoneAuth=memberService.certifiedPhone("12341234");
        memberService.certifiedPhoneCheck("12341234",memberPhoneAuth.getSmscode());

        MemberRequestDTO memberRequestDTO = new MemberRequestDTO();
        memberRequestDTO.setLoginId("memberTest");
        memberRequestDTO.setPassword("123456");
        memberRequestDTO.setEmail("aa@aa");
        memberRequestDTO.setName("momo");
        memberRequestDTO.setPhone("12341234");

        memberService.save(memberRequestDTO);
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
    // 9. Member 로그인 실패
    // 10. Member 로그인 성공

    @Test
    @DisplayName("1. Member 저장 실패")
    public void member_save_fail() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberRequestDTO memberRequestDTO = new MemberRequestDTO();
        memberRequestDTO.setEmail("test");
        memberRequestDTO.setPhone("010101");
        memberRequestDTO.setName("test");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberRequestDTO);

        mockMvc.perform(post(URL)
                        .content(paramString) // body
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("Member-Save-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                getDescription("loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING),
                                getDescription("email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING))
                ));

    }

    @Test
    @DisplayName("2. Member 저장 성공")
    public void member_save() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        MemberPhoneAuth memberPhoneAuth=memberService.certifiedPhone("memberPhone");
        memberService.certifiedPhoneCheck("memberPhone",memberPhoneAuth.getSmscode());

        MemberRequestDTO memberRequestDTO = new MemberRequestDTO();
        memberRequestDTO.setLoginId("memeberLoginId");
        memberRequestDTO.setPassword("memberPassword");
        memberRequestDTO.setEmail("memberEmail");
        memberRequestDTO.setName("memberName");
        memberRequestDTO.setPhone("memberPhone");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberRequestDTO);

        mockMvc.perform(post(URL)
                        .content(paramString) // body
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("회원가입이 완료되었습니다."))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Member-Save-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING),
                                getDescription("email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("3. Member 리스트 조회")
    public void member_list() throws Exception {

        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk()) // 7
                .andDo(document("Member-List", // 1
                        preprocessResponse(prettyPrint()), // 2
                        responseFields( // 3
                                subsectionWithPath("[]").description("회원가입 멤버"),
                                getDescription("[].id", "회원 고유번호(PK)").type(JsonFieldType.NUMBER),// 5
                                getDescription("[].loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("[].email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("[].name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("[].phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("[].status","회원 상태").type(JsonFieldType.STRING),
                                getDescription("[].createdDate", "회원가입 날짜").type(JsonFieldType.STRING),
                                getDescription("[].updatedDate","회원수정 날짜").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("4. member 조회 (id)")
    public void member() throws Exception {

        // When && Then
        // 조회
        mockMvc.perform(RestDocumentationRequestBuilders.get(URL + "/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .accept(MediaType.ALL)
                )
                .andExpect(jsonPath("loginId").value("memberTest"))
                .andExpect(jsonPath("email").value("aa@aa"))
                .andExpect(jsonPath("name").value("momo"))
                .andExpect(jsonPath("phone").value("12341234"))
                .andExpect(jsonPath("status").value("GREEN"))
                .andExpect(jsonPath("createdDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("updatedDate").value(String.valueOf(LocalDate.now())))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Member-Search",
                        preprocessRequest(),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                          parameterWithName("id").description("MemberId")
                        ),
                        responseFields(
                                getDescription("loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("status","회원 상태").type(JsonFieldType.STRING),
                                getDescription("role","회원 등급").type(JsonFieldType.STRING),
                                getDescription("createdDate", "회원가입 날짜").type(JsonFieldType.STRING),
                                getDescription("updatedDate","회원수정 날짜").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("5. member 조회 실패 ( id 값이 없을 때 )")
    public void member_fail() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(URL +"/{id}",1000)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.ALL)
                )
                .andExpect(content().string("얘는 누구냐"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("Member-Search-Fail" ,
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("MemberId"))
                ));
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

        mockMvc.perform(RestDocumentationRequestBuilders.put(URL + "/{id}",1)
                                .content(paramString)
                                .characterEncoding("UTF-8")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("비밀번호가 변경 되었습니다."))
                .andDo(document("Member-Update-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("MemberId")
                        ),
                        requestFields(
                                getDescription("email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING)
                        )
                ));

    }

    @Test
    @DisplayName("7. member 수정 {id} 값이 없을 때")
    public void member_update_fail() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();
        memberUpdateDTO.setEmail("modifyEmail");
        memberUpdateDTO.setPassword("modifyPassword");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberUpdateDTO);

        mockMvc.perform(RestDocumentationRequestBuilders.put(URL + "/{id}",1000)
                                .content(paramString)
                                .characterEncoding("UTF-8")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andExpect(content().string("해당 정보가 없습니다."))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("Member-Update-id-Fail",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("MemberId")
                        ),
                        requestFields(
                                getDescription("email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("8. member update 실패 (body 가 없을 때)")
    public void member_update_fail2() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberUpdateDTO);

        mockMvc.perform(RestDocumentationRequestBuilders.put(URL + "/{id}",1)
                                .content(paramString)
                                .characterEncoding("UTF-8")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL)
                )
                .andExpect(content().string("이메일을 입력하세요."))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("Member-Update-body-Fail",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("MemberId")
                        ),
                        requestFields(
                                getDescription("email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("9. Member 로그인 실패")
    public void member_login_fail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        MemberRequestDTO memberRequestDTO = new MemberRequestDTO();
        memberRequestDTO.setLoginId("");
        memberRequestDTO.setPassword("");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberRequestDTO);

        mockMvc.perform(post(URL+"/login")
                        .content(paramString) // body
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("아이디를 입력하세요."))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("Member-login-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("10. Member 로그인 성공")
    public void member_login_Success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        MemberRequestDTO memberRequestDTO = new MemberRequestDTO();
        memberRequestDTO.setLoginId("memberTest");
        memberRequestDTO.setPassword("123456");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberRequestDTO);

        mockMvc.perform(post(URL+"/login")
                        .content(paramString) // body
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Member-login-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("password","회원 비밀번호").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("accessToken","발급된 AccessToken").type(JsonFieldType.STRING))
                ));
    }

    //TODO: 문자 인증 테스트 진행 필요



    private FieldDescriptor getDescription(String name, String description) {
        return fieldWithPath(name)
                .description(description);
    }

}
