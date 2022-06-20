package anthill.Anthill.controller;

import anthill.Anthill.domain.member.Address;
import anthill.Anthill.dto.member.MemberLoginRequestDTO;
import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.dto.member.MemberResponseDTO;
import anthill.Anthill.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("헬로우 테스트")
    public void returnOkMessage() throws Exception {
        //given
        String ok = "ok";

        //when
        ResultActions resultActions = mvc.perform(get("/members"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(ok));
    }

    @Test
    @DisplayName("회원가입 입력값들이 유효하지 않음")
    public void memberPostDataInValidateTest() throws Exception {
        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                                                            .build();
        String body = (new ObjectMapper()).writeValueAsString(memberRequestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 입력값들이 유효함")
    public void memberPostDataValidateTest() throws Exception {
        //given
        MemberRequestDTO memberRequestDTO = getMemberRequestDTO();
        String body = (new ObjectMapper()).writeValueAsString(memberRequestDTO);

        //when
        ResultActions resultActions = mvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원 중복 발생시 409 상태코드 반환")
    public void memberDuplicateTest() throws Exception {
        //given
        MemberRequestDTO memberRequestDTO = getMemberRequestDTO();
        String body = (new ObjectMapper()).writeValueAsString(memberRequestDTO);
        boolean duplicateResult = true;
        given(memberService.validateIsDuplicate(any())).willReturn(duplicateResult);


        //when
        ResultActions resultActions = mvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("회원 중복 발생안됬을 시 201 상태코드 반환")
    public void memberNonDuplicateTest() throws Exception {
        //given
        MemberRequestDTO memberRequestDTO = getMemberRequestDTO();
        String body = (new ObjectMapper()).writeValueAsString(memberRequestDTO);
        boolean duplicateResult = false;
        given(memberService.validateIsDuplicate(any())).willReturn(duplicateResult);

        //when
        ResultActions resultActions = mvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("member-join-success",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").description("아이디"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("nickName").description("닉네임"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("phoneNumber").description("전화 번호"),
                                fieldWithPath("address.address1").description("주소")
                                                                 .optional(),
                                fieldWithPath("address.address2").description("상세 주소")
                                                                 .optional(),
                                fieldWithPath("address.zipCode").description("우편 번호")
                                                                .optional()
                        )
                ));
    }

    @Test
    @DisplayName("로그인 성공시 200 상태코드 반환")
    public void memberLoginSuccessTest() throws Exception {
        //given
        MemberLoginRequestDTO memberLoginRequestDTO = getMemberLoginRequestDto();
        String body = (new ObjectMapper()).writeValueAsString(memberLoginRequestDTO);
        boolean loginResult = true;
        given(memberService.login(any())).willReturn(loginResult);

        //when
        ResultActions resultActions = mvc.perform(post("/members/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("member-login-success",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").description("아이디"),
                                fieldWithPath("password").description("비밀번호")
                        )
                ));
    }

    @Test
    @DisplayName("로그인 실패시 401 상태코드 반환")
    public void memberLoginFailTest() throws Exception {
        //given
        MemberLoginRequestDTO memberLoginRequestDTO = getMemberLoginRequestDto();
        String body = (new ObjectMapper()).writeValueAsString(memberLoginRequestDTO);
        given(memberService.login(any())).willThrow(new IllegalStateException());

        //when
        ResultActions resultActions = mvc.perform(post("/members/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void 회원조회성공시200_OK() throws Exception {
        //given
        MemberResponseDTO memberResponseDTO = getMemberResponseDTO();
        given(memberService.findByUserID(any())).willReturn(memberResponseDTO);
        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/members/{userid}", memberResponseDTO.getUserId()));
        //then
        resultActions.andExpect(status().isOk())
                     .andDo(document("member-get-by-id-success",
                             preprocessResponse(prettyPrint()),
                             pathParameters(
                                     parameterWithName("userid").description("아이디")
                             ),
                             responseFields(
                                     fieldWithPath("userId").description("아이디"),
                                     fieldWithPath("name").description("이름"),
                                     fieldWithPath("nickName").description("닉네임"),
                                     fieldWithPath("phoneNumber").description("전화 번호"),
                                     fieldWithPath("address.address1").description("주소")
                                                                      .optional(),
                                     fieldWithPath("address.address2").description("상세 주소")
                                                                      .optional(),
                                     fieldWithPath("address.zipCode").description("우편 번호")
                                                                     .optional()
                             )
                     ));

    }

    @Test
    public void 회원조회실패시404_NOT_FOUND() throws Exception {
        //given
        MemberResponseDTO memberResponseDTO = getMemberResponseDTO();
        given(memberService.findByUserID(any())).willThrow(new IllegalArgumentException());
        //when
        ResultActions resultActions = mvc.perform(get("/members/" + "test"));
        //then
        resultActions.andExpect(status().isNotFound());

    }


    private MemberResponseDTO getMemberResponseDTO() {
        Address myAddress = Address.builder()
                                   .address1("경기도 시흥시")
                                   .address2("XX아파트 XX호")
                                   .zipCode("429-010")
                                   .build();

        MemberResponseDTO memberResponseDTO = MemberResponseDTO.builder()
                                                               .userId("test")
                                                               .name("test")
                                                               .nickName("test")
                                                               .phoneNumber("01012345678")
                                                               .address(myAddress)
                                                               .build();
        return memberResponseDTO;
    }


    private MemberRequestDTO getMemberRequestDTO() {

        Address myAddress = Address.builder()
                                   .address1("경기도 시흥시")
                                   .address2("XX아파트 XX호")
                                   .zipCode("429-010")
                                   .build();

        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                                                            .userId("junwooKim")
                                                            .name("KIM")
                                                            .nickName("junuuu")
                                                            .password("123456789")
                                                            .phoneNumber("01012345678")
                                                            .address(myAddress)
                                                            .build();
        return memberRequestDTO;
    }

    private MemberLoginRequestDTO getMemberLoginRequestDto() {
        MemberLoginRequestDTO memberLoginRequestDTO = MemberLoginRequestDTO.builder()
                                                                           .userId("test")
                                                                           .password("123456789")
                                                                           .build();
        return memberLoginRequestDTO;
    }

}