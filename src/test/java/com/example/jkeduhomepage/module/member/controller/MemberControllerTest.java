package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberInsertDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        mockMvc.perform(post(URL)
                        .content(paramString) // body
                        .accept(MediaType.ALL)
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
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("status","회원 상태").type(JsonFieldType.STRING))
                ));

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

        mockMvc.perform(post(URL)
                        .content(paramString) // body
                        .accept(MediaType.ALL)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("loginId").value("memeberLoginId"))
                .andExpect(jsonPath("email").value("memberEmail"))
                .andExpect(jsonPath("name").value("memberName"))
                .andExpect(jsonPath("phone").value("memberPhone"))
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
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("status","회원 상태").type(JsonFieldType.STRING)),
                        responseFields(
                                getDescription("loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("phone", "회원 휴대번호").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("3. Member 리스트 조회")
    public void member_list() throws Exception {
        saveMember();

        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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
                                .accept(MediaType.ALL)
                )
                .andExpect(jsonPath("loginId").value("aaa"))
                .andExpect(jsonPath("email").value("aa@aa"))
                .andExpect(jsonPath("name").value("momo"))
                .andExpect(jsonPath("phone").value("123456789"))
                .andExpect(jsonPath("status").value("WHITE"))
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

    private FieldDescriptor getDescription(String name, String description) {
        return fieldWithPath(name)
                .description(description);
    }

}
