package anthill.Anthill.controller;

import anthill.Anthill.dto.member.MemberDuplicateResponseDTO;
import anthill.Anthill.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
        String nickName = "testNickName";
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
                                fieldWithPath("message").description("중복 시 true 중복 되지 않을 시 false")
                        )

                ));

    }

    @Test
    @DisplayName("닉네임 중복 테스트 true 반환")
    public void checkNicknameDuplicateTrueTest() throws Exception {
        //given
        boolean test = true;
        String nickName = "testNickName";
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
                                fieldWithPath("message").description("중복 시 true 중복 되지 않을 시 false")
                        )
                ));

    }

    private MemberDuplicateResponseDTO makeMemberDuplicateResponseDTO(boolean result) {
        return MemberDuplicateResponseDTO.builder()
                                         .message(String.valueOf(result))
                                         .build();
    }

    @Test
    public void 유저아이디중복테스트false반환() throws Exception {
        //given
        boolean test = false;
        String userId = "testUserId";
        given(memberService.checkUserIdDuplicate(userId)).willReturn(test);


        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/user-id/{userId}", userId));


        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("user-id-non-duplicate",
                        pathParameters(
                                parameterWithName("userId").description("멤버 아이디")
                        ),

                        responseFields(
                                fieldWithPath("message").description("중복 시 true 중복 되지 않을 시 false")
                        )

                ));

    }

    @Test
    public void 유저아이디중복테스트true반환() throws Exception {
        //given
        boolean test = true;
        String userId = "testUserId";
        given(memberService.checkUserIdDuplicate(userId)).willReturn(test);


        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/user-id/{userId}", userId));


        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("user-id-duplicate",
                        pathParameters(
                                parameterWithName("userId").description("멤버 아이디")
                        ),

                        responseFields(
                                fieldWithPath("message").description("중복 시 true 중복 되지 않을 시 false")
                        )

                ));

    }

    @Test
    public void 유저전화번호중복테스트false반환() throws Exception {
        //given
        boolean test = false;
        String phoneNumber = "01012345678";
        given(memberService.checkPhoneNumberDuplicate(phoneNumber)).willReturn(test);


        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/user-phonenumber/{phonenumber}", phoneNumber));


        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("user-phonenumber-non-duplicate",
                        pathParameters(
                                parameterWithName("phonenumber").description("멤버 전화번호")
                        ),

                        responseFields(
                                fieldWithPath("message").description("중복 시 true 중복 되지 않을 시 false")
                        )

                ));

    }

    @Test
    public void 유저전화번호중복테스트true반환() throws Exception {
        //given
        boolean test = true;
        String phoneNumber = "01012345678";
        given(memberService.checkPhoneNumberDuplicate(phoneNumber)).willReturn(test);


        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/user-phonenumber/{phonenumber}", phoneNumber));


        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("user-phonenumber-duplicate",
                        pathParameters(
                                parameterWithName("phonenumber").description("멤버 전화번호")
                        ),

                        responseFields(
                                fieldWithPath("message").description("중복 시 true 중복 되지 않을 시 false")
                        )

                ));

    }

}