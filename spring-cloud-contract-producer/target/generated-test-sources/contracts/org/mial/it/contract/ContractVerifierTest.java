package org.mial.it.contract;

import org.mial.it.contract.UserRestControllerApiBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@SuppressWarnings("rawtypes")
public class ContractVerifierTest extends UserRestControllerApiBase {

	@Test
	public void validate_should_Create_the_user_with_id_1() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"id\":\"1\",\"firstName\":\"First Name\",\"lastName\":\"Last Name\",\"email\":\"Email\"}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/v1/user/");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json");

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			String responseBody = response.getBody().asString();
			assertThat(responseBody).isEqualTo("1");
	}

	@Test
	public void validate_should_Return_the_user_with_id_1() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json");

		// when:
			ResponseOptions response = given().spec(request)
					.get("/v1/user/222");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json");

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['id']").isEqualTo("1");
			assertThatJson(parsedJson).field("['firstName']").isEqualTo("First Name");
			assertThatJson(parsedJson).field("['lastName']").isEqualTo("Last Name");
			assertThatJson(parsedJson).field("['email']").isEqualTo("Email");
	}

}
