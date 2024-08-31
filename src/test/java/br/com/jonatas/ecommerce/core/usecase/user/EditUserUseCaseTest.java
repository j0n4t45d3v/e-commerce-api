package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.in.user.dto.EditUserDTO;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EditUserUseCaseTest {

	@Mock
	private UserRepositoryGateway userRepositoryGateway;

	@InjectMocks
	private EditUserUseCase editUserUseCase;

	private AutoCloseable closeable;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	@DisplayName("Should edit user if user exists")
	void executeCase1() {
		EditUserDTO userEdited = new EditUserDTO("username", "password", "email@test.com");
		when(this.userRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(new UserDomain()));
		this.editUserUseCase.execute(1L, userEdited);
		verify(this.userRepositoryGateway, Mockito.times(1)).findById(anyLong());
		verify(this.userRepositoryGateway, Mockito.times(1)).save(any(UserDomain.class));
	}

	@Test
	@DisplayName("Should return exception if user not exists and not edit user")
	void executeCase2() {
		EditUserDTO userEdited = new EditUserDTO("username", "password", "email@test.com");
		when(this.userRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
		NotFoundException thrown = assertThrows(NotFoundException.class, () -> this.editUserUseCase.execute(1L, userEdited));
		assertEquals("User not found", thrown.getMessage());

		verify(this.userRepositoryGateway, Mockito.times(1)).findById(anyLong());
		verify(this.userRepositoryGateway, Mockito.times(0)).save(any(UserDomain.class));
	}
}