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
        mvc.perform(get("/members"))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(ok));
    }

    @Test
    @DisplayName("memberRequestDTO의 입력값들이 유효하지 않을때")
    public void memberPostDataInValidateTest() throws Exception {
        //given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder().build();
        String body = (new ObjectMapper()).writeValueAsString(memberRequestDTO);

        //when
        mvc.perform(post("/members")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(status().isBadRequest());
    }



}