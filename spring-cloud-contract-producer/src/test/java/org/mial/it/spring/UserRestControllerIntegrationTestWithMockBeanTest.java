package org.mial.it.spring;

import org.junit.jupiter.api.Test;
import org.mial.dto.UserDTO;
import org.mial.model.User;
import org.mial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerIntegrationTestWithMockBeanTest {

    @LocalServerPort
    private int port;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findUserById() {
        // arrange
        User userToReturn = User.builder()
            .id(1L)
            .firstName("firstName")
            .lastName("lastName")
            .email("email")
            .build();

        when(userRepository.findUserById(1L)).thenReturn(Optional.of(userToReturn));

        // act
        UserDTO task = restTemplate.getForObject("http://localhost:" + port + "/v1/user/1", UserDTO.class);

        // assert
        assertThat(task)
            .extracting(UserDTO::getId, UserDTO::getFirstName, UserDTO::getLastName, UserDTO::getEmail)
            .containsExactly(1L, "firstName", "lastName", "email");
    }
}
