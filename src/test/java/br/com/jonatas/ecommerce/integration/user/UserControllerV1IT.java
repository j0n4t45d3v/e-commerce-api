package br.com.jonatas.ecommerce.integration.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.core.domain.user.enums.Role;
import br.com.jonatas.ecommerce.gateway.in.user.dto.EditUserDTO;
import br.com.jonatas.ecommerce.gateway.in.user.dto.RegisterUserDTO;
import br.com.jonatas.ecommerce.infra.common.http.ResponseErrorV0;
import br.com.jonatas.ecommerce.infra.common.http.ResponseV0;
import br.com.jonatas.ecommerce.integration.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerV1IT extends BaseIntegrationTest {

	@Test
	@Order(1)
	@DisplayName("should register a user")
	void register() {
		var userDTO = new RegisterUserDTO(
				"John Doe",
				"john123",
				"john@doe.com",
				LocalDate.of(1990, 1, 1),
				Role.CLIENT
		);
		var responseString = new ParameterizedTypeReference<ResponseV0<String>>() {
		};
		var response = this.restTemplate.exchange("/v1/users/register", HttpMethod.POST,new HttpEntity<>(userDTO), responseString);
		var body = response.getBody();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(body).isNotNull();
		assertThat(body.data()).isNotEmpty();
		assertThat(response.getHeaders().getLocation()).isNotNull();
		assertThat(response.getHeaders().getLocation().getPath()).isNotEmpty();
		assertThat(body.status()).isEqualTo(201);
	}

	@Test
	@Order(2)
	@DisplayName("should return all users")
	void getAllUsers() {
		var responseListUserDomain = new ParameterizedTypeReference<ResponseV0<List<UserDomain>>>() {
		};
		var response = this.restTemplate.exchange("/v1/users", HttpMethod.GET, null, responseListUserDomain);
		var body = response.getBody();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(body).isNotNull();
		assertThat(body.data()).hasSize(1);
		assertThat(body.status()).isEqualTo(200);
	}

	@Test
	@Order(3)
	@DisplayName("should return a user by id")
	void getById() {
		var	responseUserDomain = new ParameterizedTypeReference<ResponseV0<UserDomain>>() {
		};
		var response =
				this.restTemplate.exchange("/v1/users/{id}", HttpMethod.GET, null, responseUserDomain, 1L);
		var body = response.getBody();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(body).isNotNull();
		assertThat(body.data()).isNotNull();
		assertThat(body.data().getId()).isEqualTo(1L);
	}

	@Test
	@Order(4)
	@DisplayName("should edit a user")
	void edit() {
		var requestBody = new EditUserDTO("John Doe updated", "john123", "john@doe.com");
		var response = this.restTemplate.exchange(
				"/v1/users/{id}",
				HttpMethod.PUT,
				new HttpEntity<>(requestBody),
				Void.class,
				1L
		);

		var responseUserDomain = new ParameterizedTypeReference<ResponseV0<UserDomain>>() {
		};
		var responseGet = this.restTemplate.exchange(
				"/v1/users/{id}",
				HttpMethod.GET,
				null,
				responseUserDomain,
				1L
		);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(responseGet.getBody()).isNotNull();
		assertThat(responseGet.getBody().data().getUsername()).isEqualTo("John Doe updated");
	}

	@Test
	@DisplayName("should delete a user")
	void delete() {
		var response = this.restTemplate.exchange(
				"/v1/users/{id}",
				HttpMethod.DELETE,
				null,
				Void.class,
				1L
		);

		var responseGet = this.restTemplate.exchange(
				"/v1/users/{id}",
				HttpMethod.GET,
				null,
				ResponseErrorV0.class,
				1L
		);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(responseGet.getBody()).isNotNull();
		assertThat(responseGet.getBody().error()).isEqualTo("User not found");
	}

}
