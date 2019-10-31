package org.mial.it.spring;

import org.junit.jupiter.api.Test;
import org.mial.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findsUserById() {
        UserDTO task = restTemplate.getForObject("http://localhost:" + port + "/v1/user/1", UserDTO.class);

        // assert
        assertThat(task)
            .extracting(UserDTO::getId, UserDTO::getFirstName, UserDTO::getLastName, UserDTO::getEmail)
            .containsExactly(1L, "firstName-0", "lastName-0", "email-0");
    }
}
