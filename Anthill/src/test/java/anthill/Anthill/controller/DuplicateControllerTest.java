package anthill.Anthill.controller;

import anthill.Anthill.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class DuplicateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("닉네임 중복 테스트 false 반환")
    public void checkNicknameDuplicateTest() throws Exception{
        //given
        boolean response = true;
        String nickName = "test";

        //when
        ResultActions resultActions = mvc.perform(get("/user-nickname/"+nickName));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

}