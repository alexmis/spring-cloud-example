package org.mial.it.spring;

import org.junit.jupiter.api.Test;
import org.mial.model.User;
import org.mial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserRestControllerWebMvcWithMockBeanTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void findsTaskById() throws Exception {
        // arrange
        User userToReturn = User.builder()
            .id(1L)
            .firstName("firstName")
            .lastName("lastName")
            .email("email")
            .build();

        when(userRepository.findUserById(1L)).thenReturn(Optional.of(userToReturn));

        // act and assert
        mockMvc.perform(get("/v1/user/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\"}"));
    }

    @TestConfiguration
    @ComponentScan("org.mial")
    static class PlaceholderApiConfig {

    }
}
