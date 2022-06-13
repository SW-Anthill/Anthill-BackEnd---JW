package anthill.Anthill.controller;

import anthill.Anthill.dto.member.MemberDuplicateResponseDTO;
import anthill.Anthill.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@WebMvcTest(DuplicateController.class)
class DuplicateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("닉네임 중복 테스트 false 반환")
    public void checkNicknameDuplicateFalseTest() throws Exception {
        //given
        boolean test = false;
        String nickName = "test";
        given(memberService.checkNicknameDuplicate(nickName)).willReturn(test);


        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/user-nickname/{nickname}", nickName));


        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("nick-name-non-duplicate",
                        pathParameters(
                                parameterWithName("nickname").description("멤버 닉네임")
                        ),

                        responseFields(
                                fieldWithPath("message").description("성공 실패 여부")
                        )

                ));

    }

    @Test
    @DisplayName("닉네임 중복 테스트 true 반환")
    public void checkNicknameDuplicateTrueTest() throws Exception {
        //given
        boolean test = true;
        String nickName = "test";
        given(memberService.checkNicknameDuplicate(nickName)).willReturn(test);

        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/user-nickname/{nickname}", nickName));

        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("nick-name-duplicate",
                        pathParameters(
                                parameterWithName("nickname").description("멤버 닉네임")
                        ),

                        responseFields(
                                fieldWithPath("message").description("성공 실패 여부")
                        )
                ));

    }

    private MemberDuplicateResponseDTO makeMemberDuplicateResponseDTO(boolean result) {
        return MemberDuplicateResponseDTO.builder()
                                         .message(String.valueOf(result))
                                         .build();
    }

}