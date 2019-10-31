package org.mial.it.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findsUserById() throws Exception {
        ResultActions perform = mockMvc.perform(get("/v1/user/1"));
        perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\":1,\"firstName\":\"firstName-0\",\"lastName\":\"lastName-0\",\"email\":\"email-0\"}"))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.firstName", is("firstName-0")))
            .andExpect(jsonPath("$.lastName", is("lastName-0")))
            .andExpect(jsonPath("$.email", is("email-0")));
    }
}
