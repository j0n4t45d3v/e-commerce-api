package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseTest {

	@Mock
	private UserRepositoryGateway userRepositoryGateway;

	@InjectMocks
	private DeleteUserUseCase deleteUserUseCase;

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
	@DisplayName("Should delete user if user exists")
	void executeCase1() {
		when(this.userRepositoryGateway.existsById(anyLong())).thenReturn(true);

		this.deleteUserUseCase.execute(1L);

		verify(this.userRepositoryGateway, times(1)).existsById(anyLong());
		verify(this.userRepositoryGateway, times(1)).deleteById(anyLong());
	}

	@Test
	@DisplayName("Should return exception if user not exists and not delete user")
	void executeCase2() {
		when(this.userRepositoryGateway.existsById(anyLong())).thenReturn(false);

		NotFoundException thrown =  assertThrows(NotFoundException.class, () -> this.deleteUserUseCase.execute(1L));
		assertEquals("User not found", thrown.getMessage());
		verify(this.userRepositoryGateway, times(1)).existsById(anyLong());
		verify(this.userRepositoryGateway, times(0)).deleteById(anyLong());
	}
}