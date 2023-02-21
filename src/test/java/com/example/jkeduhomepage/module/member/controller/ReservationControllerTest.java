package com.example.jkeduhomepage.module.member.controller;

import com.example.jkeduhomepage.module.member.dto.MemberReservationDTO;
import com.example.jkeduhomepage.module.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class ReservationControllerTest {

    private final String URL = "/reservation";

    private MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("예약하기")
    public void exam_reservation() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberReservationDTO memberReservationDTO=new MemberReservationDTO();
        memberReservationDTO.setDay(java.sql.Date.valueOf("2020-12-12"));
        memberReservationDTO.setName("준모");
        memberReservationDTO.setSchool("준모초등학교");
        memberReservationDTO.setGrade("13학년");
        memberReservationDTO.setPhone("01091097122");
        memberReservationDTO.setRelationship("아무도모름");

        // Object -> json String
        String paramString = objectMapper.writeValueAsString(memberReservationDTO);

        mockMvc.perform(post(URL)
                        .content(paramString) // body
                        .accept(MediaType.ALL)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Test-Reservation", // 1
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                getDescription("day", "예약 날짜").type(JsonFieldType.NUMBER),
                                getDescription("name","이름").type(JsonFieldType.STRING),
                                getDescription("school", "다니는 학교").type(JsonFieldType.STRING),
                                getDescription("grade","학년").type(JsonFieldType.STRING),
                                getDescription("phone", "휴대번호").type(JsonFieldType.STRING),
                                getDescription("relationship","예약자와의 관계").type(JsonFieldType.STRING))

                ));

    }

    private FieldDescriptor getDescription(String name, String description) {
        return fieldWithPath(name)
                .description(description);
    }


}