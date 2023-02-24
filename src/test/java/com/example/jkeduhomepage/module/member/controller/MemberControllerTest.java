package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.member.dto.MemberRequestDTO;
import com.example.jkeduhomepage.module.member.dto.MemberUpdateDTO;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.entity.MemberPhoneAuth;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    private MemberService memberService;

    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Member SECURITY_ADMIN_MEMBER;

    private static Member SECURITY_DELETE_MEMBER;

    private static Member SECURITY_MEMBER;

    private static Member RED_MEMBER;


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
        MemberPhoneAuth memberPhoneAuth=memberService.certifiedPhone("memberPhone2");
        memberService.certifiedPhoneCheck("memberPhone2",memberPhoneAuth.getSmscode());

        MemberRequestDTO memberRequestDTO = new MemberRequestDTO();
        memberRequestDTO.setLoginId("memberTest");
        memberRequestDTO.setPassword("123456");
        memberRequestDTO.setEmail("aa@aa");
        memberRequestDTO.setName("momo");
        memberRequestDTO.setPhone("memberPhone2");
        memberService.save(memberRequestDTO);


        SECURITY_ADMIN_MEMBER=memberRepository.save(Member.builder()
                .loginId("adminMember")
                .email("abc@abc")
                .phone("123456789")
                .name("jkjk")
                .role(Role.ROLE_ADMIN)
                .password(passwordEncoder.encode("4321"))
                .build());

        SECURITY_MEMBER=memberRepository.save(Member.builder()
                .loginId("userMember")
                .email("cba@cba")
                .phone("123456789")
                .name("jkjk")
                .role(Role.ROLE_USER)
                .password(passwordEncoder.encode("4321"))
                .build());

        RED_MEMBER=memberRepository.save(Member.builder()
                .loginId("redMember")
                .email("red")
                .phone("221133")
                .name("redred")
                .password(passwordEncoder.encode("4321"))
                .status(Status.RED)
                .build());


        SECURITY_DELETE_MEMBER=memberRepository.save(Member.builder()
                .loginId("deleteMember")
                .email("abc@abc")
                .phone("123321")
                .name("momo")
                .role(Role.ROLE_ADMIN)
                .password(passwordEncoder.encode("1234"))
                .build());
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
    // 8. Member 수정 실패 ( body 빈 값 일때 )
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
    @WithUserDetails("adminMember")
    @DisplayName("3. Member 리스트 조회")
    public void member_list() throws Exception {

        mockMvc.perform(get(URL+"/management")
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
                .andExpect(jsonPath("phone").value("memberPhone2"))
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
                .andExpect(content().string("조회에 실패했습니다."))
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
                                getDescription("name","사용자 이름").type(JsonFieldType.STRING),
                                getDescription("accessToken","발급된 AccessToken").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @DisplayName("11. 인증문자 발송")
    public void phone_cert() throws Exception {

        mockMvc.perform(post(URL+"/cert?phone=01012341234")
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("인증문자 발송 완료."))
                .andExpect(status().isOk())
                .andDo(document("Phone-SMS-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("phone").description("인증요청 번호"))
                ));
    }

    @Test
    @DisplayName("12. 인증문자 발송 실패")
    public void phone_cert_fail() throws Exception {

        mockMvc.perform(post(URL+"/cert?phone=memberPhone2")
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("이미 회원입니다."))
                .andExpect(status().isBadRequest())
                .andDo(document("Phone-SMS-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("phone").description("인증요청 번호"))
                ));
    }

    @Test
    @DisplayName("13.인증문자 확인")
    public void phone_cert_check() throws Exception {
        MemberPhoneAuth memberPhoneAuth=memberService.certifiedPhone("memberPhone4");

        mockMvc.perform(put(URL+"/cert?phone=memberPhone4&smscode="+memberPhoneAuth.getSmscode())
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("인증 완료 되었습니다."))
                .andExpect(status().isOk())
                .andDo(document("Phone-SMS-Check-Success", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("phone").description("인증요청 번호"),
                                parameterWithName("smscode").description("인증번호"))
                ));
    }

    @Test
    @DisplayName("14.인증문자 확인실패( 해당번호로 문자요청을 하지 않은 경우 )")
    public void phone_cert_check_fail_phone() throws Exception {

        mockMvc.perform(put(URL+"/cert?phone=01010101&smscode=0000")
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("휴대폰 인증을 다시 요청하세요"))
                .andExpect(status().isNotFound())
                .andDo(document("Phone-SMS-Check-Fail-NoPhone", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("phone").description("인증요청 번호"),
                                parameterWithName("smscode").description("인증번호"))
                ));
    }

    @Test
    @DisplayName("15.인증문자 확인실패( 인증번호가 불일치 )")
    public void phone_cert_check_fail_smscode() throws Exception {
        memberService.certifiedPhone("memberPhone3");

        mockMvc.perform(put(URL+"/cert?phone=memberPhone3&smscode=0000")
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("인증번호가 일치하지 않습니다."))
                .andExpect(status().isBadRequest())
                .andDo(document("Phone-SMS-Check-Fail-NoSMS", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("phone").description("인증요청 번호"),
                                parameterWithName("smscode").description("인증번호"))
                ));
    }

    @Test
    @DisplayName(" Member 저장 실패 ( 문자 인증 미진행 )")
    public void member_save_fail_noSMS() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

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
                .andExpect(content().string("휴대폰 인증을 완료하세요"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("Member-Save-Fail-NoSMS", // 1
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
    @WithUserDetails("memberTest")
    @DisplayName("Member 내 정보")
    public void member_info() throws Exception {
        mockMvc.perform(get(URL)
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Member-My-Info", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getDescription("loginId","로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("name","이름").type(JsonFieldType.STRING),
                                getDescription("email","이메일").type(JsonFieldType.STRING),
                                getDescription("phone","휴대전화").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @WithUserDetails("memberTest")
    @DisplayName("Member 비밀번호 변경")
    public void member_password_change() throws Exception {
        String password ="{\"newPassword\" : \"123456\"}";

        mockMvc.perform(put(URL)
                        .content(password)
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("비밀번호가 변경 되었습니다."))
                .andDo(document("Member-Password-Change", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("newPassword","새로운 비밀번호"))
                ));
    }


    @Test
    @WithUserDetails("memberTest")
    @DisplayName("Member 회원 탈퇴 실패")
    public void member_delete_fail() throws Exception {


        String password ="{\"password\" : \"12312312\"}";

        mockMvc.perform(delete(URL)
                        .content(password)
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("현재 비밀번호가 옳지 않습니다."))
                .andDo(document("Member-Delete-Fail", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("password","해당 Member 비밀번호"))
                ));
    }

    @Test
    @WithUserDetails("deleteMember")
    @DisplayName("Member 회원 탈퇴")
    public void member_delete() throws Exception {

        String password ="{\"password\" : \"1234\"}";

        mockMvc.perform(delete(URL)
                        .content(password)
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("회원탈퇴 완료"))
                .andDo(document("Member-Delete", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getDescription("password","해당 Member 비밀번호"))
                ));
    }

    @Test
    @WithUserDetails("adminMember")
    @DisplayName("승인이 필요한 Member 리스트")
    public void member_approval_list() throws Exception {

        mockMvc.perform(get(URL+"/approval")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk()) // 7
                .andDo(document("Member-Approval-List", // 1
                        preprocessResponse(prettyPrint()), // 2
                        responseFields( // 3
                                getDescription("next","다음 페이지 유무"),
                                subsectionWithPath("memberResponseDTOList[]").description("승인이 필요한 멤버"),
                                getDescription("memberResponseDTOList[].id", "회원 고유번호(PK)").type(JsonFieldType.NUMBER),// 5
                                getDescription("memberResponseDTOList[].loginId", "회원 로그인 아이디").type(JsonFieldType.STRING),
                                getDescription("memberResponseDTOList[].email", "회원 이메일").type(JsonFieldType.STRING),
                                getDescription("memberResponseDTOList[].name","회원 이름").type(JsonFieldType.STRING),
                                getDescription("memberResponseDTOList[].phone", "회원 휴대번호").type(JsonFieldType.STRING),
                                getDescription("memberResponseDTOList[].status","회원 상태").type(JsonFieldType.STRING),
                                getDescription("memberResponseDTOList[].createdDate", "회원가입 승인요청 날짜").type(JsonFieldType.STRING))
                ));
    }

    @Test
    @WithUserDetails("userMember")
    @DisplayName("승인이 필요한 Member 리스트 보기 실패")
    public void member_approval_list_Fail() throws Exception {

        mockMvc.perform(get(URL+"/approval")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.ALL))
                .andExpect(content().string("잘못된 접근"))
                .andDo(print())
                .andExpect(status().isForbidden()) // 7
                .andDo(document("Member-Approval-List-Fail"));

    }

    @Test
    @WithUserDetails("adminMember")
    @DisplayName("회원가입 승인")
    public void member_approval() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders.post(URL+"/approval/{id}",RED_MEMBER.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.ALL))
                .andExpect(content().string("회원가입을 승인하였습니다."))
                .andDo(print())
                .andExpect(status().isOk()) // 7
                .andDo(document("Member-Approval", // 1
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("해당 회원번호"))// 2
                ));
    }

    @Test
    @WithUserDetails("userMember")
    @DisplayName("회원가입 승인 실패")
    public void member_approval_fail() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders.post(URL+"/approval/{id}",RED_MEMBER.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.ALL))
                .andExpect(content().string("잘못된 접근"))
                .andDo(print())
                .andExpect(status().isForbidden()) // 7
                .andDo(document("Member-Approval-fail"));

    }





    private FieldDescriptor getDescription(String name, String description) {
        return fieldWithPath(name)
                .description(description);
    }

}
