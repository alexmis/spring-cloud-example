package org.mial.it.assured;

import org.junit.jupiter.api.Test;
import org.mial.dto.UserDTO;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerAssuredIntegrationTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    // https://github.com/rest-assured/rest-assured/wiki/usage

    @Test
    void findsUserById() {
        UserDTO result = get(uri + "/v1/user/1").then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .and()
            .body("id", equalTo(1))
            .body("firstName", equalTo("firstName-0"))
            .extract()
            //.jsonPath()
            .as(UserDTO.class);

        assertThat(result.getFirstName()).isEqualTo(UserDTO.builder().firstName("firstName-0").build().getFirstName());
    }
}
