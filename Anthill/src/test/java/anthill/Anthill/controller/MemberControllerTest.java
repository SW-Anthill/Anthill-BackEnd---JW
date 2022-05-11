package anthill.Anthill.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@WebMvcTest
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Hello Test")
    public void returnOkMessage() throws Exception {
        //given
        String ok = "ok";

        //when

        //then
        mvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(content().string(ok));
    }

}