package anthill.Anthill.controller;

import anthill.Anthill.dto.board.BoardDeleteDTO;
import anthill.Anthill.dto.board.BoardRequestDTO;
import anthill.Anthill.dto.board.BoardUpdateDTO;
import anthill.Anthill.service.BoardService;
import anthill.Anthill.service.JwtService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private BoardService boardService;

    @MockBean
    private JwtService jwtService;

    @Test
    void 게시글_작성_인증실패() throws Exception {

        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO("test");

        String body = new ObjectMapper().writeValueAsString(boardRequestDTO);

        ResultActions resultActions = mvc.perform(post("/boards")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isUnauthorized());

    }

    @Test
    void 게시글_작성_인증성공() throws Exception {

        BoardRequestDTO boardRequestDTO = makeBoardRequestDTO("test");
        String token = "header.payload.sign";
        String accessTokenHeader = "access-token";
        String body = new ObjectMapper().writeValueAsString(boardRequestDTO);

        given(jwtService.isUsable(any())).willReturn(true);

        ResultActions resultActions = mvc.perform(post("/boards")
                .content(body)
                .header(accessTokenHeader, token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                     .andDo(document("board-posting-success",
                                     preprocessRequest(prettyPrint()),
                                     preprocessResponse(prettyPrint()),
                                     requestHeaders(
                                             headerWithName("access-token").description("로그인 시 발급된 토큰")
                                     ),
                                     requestFields(
                                             fieldWithPath("title").description("제목"),
                                             fieldWithPath("content").description("본문"),
                                             fieldWithPath("writer").description("작성자")
                                     ),
                                     responseFields(
                                             fieldWithPath("message").description("메시지"),
                                             fieldWithPath("responseData").description("반환값"),
                                             fieldWithPath("errorMessage").description("에러 메시지")
                                     )
                             )
                     );
    }

    @Test
    void 게시물_수정_실패() throws Exception {
        //given
        BoardUpdateDTO boardUpdateDTO = makeBoardUpdateDTO();
        String body = new ObjectMapper().writeValueAsString(boardUpdateDTO);
        given(jwtService.isUsable(any())).willReturn(false);

        //when
        ResultActions resultActions = mvc.perform(put("/boards/{boardId}", boardUpdateDTO.getId())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    void 게시물_수정_성공() throws Exception {
        //given
        BoardUpdateDTO boardUpdateDTO = makeBoardUpdateDTO();
        String token = "header.payload.sign";
        String accessTokenHeader = "access-token";
        String body = new ObjectMapper().writeValueAsString(boardUpdateDTO);
        given(jwtService.isUsable(any())).willReturn(true);

        //when
        ResultActions resultActions = mvc.perform(put("/boards/{boardId}", boardUpdateDTO.getId())
                .content(body)
                .header(accessTokenHeader, token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                     .andDo(document("board-update-success",
                                     preprocessRequest(prettyPrint()),
                                     preprocessResponse(prettyPrint()),
                                     requestHeaders(
                                             headerWithName("access-token").description("로그인 시 발급된 토큰")
                                     ),
                                     requestFields(
                                             fieldWithPath("id").description("글번호"),
                                             fieldWithPath("title").description("제목"),
                                             fieldWithPath("content").description("본문"),
                                             fieldWithPath("writer").description("작성자")
                                     ),
                                     responseFields(
                                             fieldWithPath("message").description("메시지"),
                                             fieldWithPath("responseData").description("반환값"),
                                             fieldWithPath("errorMessage").description("에러 메시지")
                                     )
                             )
                     );
    }

    @Test
    void 게시물_삭제_성공() throws Exception{
        //given
        BoardDeleteDTO boardDeleteDTO = makeBoardDeleteDTO();
        String token = "header.payload.sign";
        String accessTokenHeader = "access-token";
        String body = new ObjectMapper().writeValueAsString(boardDeleteDTO);
        given(jwtService.isUsable(any())).willReturn(true);

        //when
        ResultActions resultActions = mvc.perform(delete("/boards/{boardId}", boardDeleteDTO.getId())
                .content(body)
                .header(accessTokenHeader, token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                     .andDo(document("board-delete-success",
                                     preprocessRequest(prettyPrint()),
                                     preprocessResponse(prettyPrint()),
                                     requestHeaders(
                                             headerWithName("access-token").description("로그인 시 발급된 토큰")
                                     ),
                                     requestFields(
                                             fieldWithPath("id").description("글번호"),
                                             fieldWithPath("writer").description("작성자")
                                     ),
                                     responseFields(
                                             fieldWithPath("message").description("메시지"),
                                             fieldWithPath("responseData").description("반환값"),
                                             fieldWithPath("errorMessage").description("에러 메시지")
                                     )
                             )
                     );

    }

    private BoardDeleteDTO makeBoardDeleteDTO() {
        BoardDeleteDTO boardDeleteDTO = BoardDeleteDTO.builder()
                                                      .id(1L)
                                                      .writer("test")
                                                      .build();
        return boardDeleteDTO;
    }


    private BoardUpdateDTO makeBoardUpdateDTO() {
        BoardUpdateDTO boardUpdateDTO = BoardUpdateDTO.builder()
                                                      .id(1L)
                                                      .title("changedTitle")
                                                      .content("changedContent")
                                                      .writer("test")
                                                      .build();
        return boardUpdateDTO;
    }

    private BoardRequestDTO makeBoardRequestDTO(String value) {
        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
                                                         .title(value)
                                                         .content(value)
                                                         .writer(value)
                                                         .build();
        return boardRequestDTO;
    }

}