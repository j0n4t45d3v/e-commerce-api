package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.core.domain.user.enums.Role;
import br.com.jonatas.ecommerce.gateway.in.crypto.Crypto;
import br.com.jonatas.ecommerce.gateway.in.user.dto.RegisterUserDTO;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.AlreadyExistsException;
import br.com.jonatas.ecommerce.infra.common.exception.BadRequestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterUserUseCaseTest {

	@Mock
	private UserRepositoryGateway userRepositoryGateway;

	@Mock
	private Crypto crypto;

	@InjectMocks
	private RegisterUserUseCase registerUserUseCase;

	private static AutoCloseable closeable;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	static void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	@DisplayName("Should register a user")
	void executeCase1() {
		RegisterUserDTO userDTO = new RegisterUserDTO(
				"john doe",
				"johndoe123",
				"john@doe.com",
				LocalDate.of(2000, 1, 1),
				Role.CLIENT
		);

		when(this.userRepositoryGateway.findByEmail(userDTO.email())).thenReturn(Optional.empty());

		this.registerUserUseCase.execute(userDTO);

		verify(this.userRepositoryGateway, Mockito.times(1)).findByEmail(anyString());
		verify(this.crypto, Mockito.times(1)).encode(anyString());
		verify(this.userRepositoryGateway, Mockito.times(1)).save(any());
	}

	@Test
	@DisplayName("Should not register user when email already exists")
	void executeCase2() {
		RegisterUserDTO userDTO = new RegisterUserDTO(
				"john doe",
				"johndoe123",
				"john@doe.com",
				LocalDate.of(2000, 1, 1),
				Role.CLIENT
		);

		when(this.userRepositoryGateway.findByEmail(userDTO.email())).thenReturn(Optional.of(new UserDomain()));

		AlreadyExistsException thrown = assertThrows(AlreadyExistsException.class, () -> this.registerUserUseCase.execute(userDTO));

		assertEquals("User already exists", thrown.getMessage());

		verify(this.userRepositoryGateway, Mockito.times(1)).findByEmail(anyString());
		verify(this.crypto, Mockito.times(0)).encode(anyString());
		verify(this.userRepositoryGateway, Mockito.times(0)).save(any());
	}

	@Test
	@DisplayName("Should not register user when age is less than 18")
	void executeCase3() {
		RegisterUserDTO userDTO = new RegisterUserDTO(
				"john doe",
				"johndoe123",
				"john@doe.com",
				LocalDate.of(2008, 1, 1),
				Role.CLIENT
		);

		when(this.userRepositoryGateway.findByEmail(userDTO.email())).thenReturn(Optional.empty());

		BadRequestException thrown = assertThrows(BadRequestException.class, () -> this.registerUserUseCase.execute(userDTO));

		assertEquals("Age user is less than 18", thrown.getMessage());

		verify(this.userRepositoryGateway, Mockito.times(1)).findByEmail(anyString());
		verify(this.crypto, Mockito.times(0)).encode(anyString());
		verify(this.userRepositoryGateway, Mockito.times(0)).save(any());
	}
}