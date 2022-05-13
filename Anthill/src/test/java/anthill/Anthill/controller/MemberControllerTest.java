package anthill.Anthill.controller;

import anthill.Anthill.dto.member.MemberRequestDTO;
import anthill.Anthill.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("Hello Test")
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
    @DisplayName("memberRequestDTO의 입력값들이 유효X")
    public void memberPostDataInValidateTest() throws Exception {
        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder().build();
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
    @DisplayName("memberRequestDTO의 입력값들이 유효O")
    public void memberPostDataValidateTest() throws Exception {
        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder().userId("junwooKim").name("KIM").nickName("junuuu").password("123456789").phoneNumber("01012345678").build();
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


}