package org.mial.it.spring;

import org.junit.jupiter.api.Test;
import org.mial.repository.UserRepository;
import org.mial.repository.impl.UserRepositoryImpl;
import org.mial.service.UserService;
import org.mial.service.impl.UserServiceImpl;
import org.mial.service.mapper.UserMapper;
import org.mial.service.mapper.UserMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserRestControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findsUserById() throws Exception {
        mockMvc.perform(get("/v1/user/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\":1,\"firstName\":\"firstName-0\",\"lastName\":\"lastName-0\",\"email\":\"email-0\"}"));
    }

    @TestConfiguration
    @ComponentScan("org.mial")
    static class PlaceholderApiConfig {

    }
}
