package org.mial.it.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mial.model.User;
import org.mial.repository.UserRepository;
import org.mial.rest.UserRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMessageVerifier
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerApiBase {

    @Autowired
    UserRestController userRestController;

    @MockBean
    private UserRepository repository;

    @BeforeEach
    public void setup() {
        User userToReturn = User.builder()
            .id(1L)
            .firstName("First Name")
            .lastName("Last Name")
            .email("Email")
            .build();

        when(repository.findUserById(any(Long.class))).thenReturn(Optional.of(userToReturn));
        when(repository.saveUser(any(User.class))).thenReturn(userToReturn.getId());

        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(userRestController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
