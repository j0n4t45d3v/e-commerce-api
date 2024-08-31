package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SearchUserUseCaseTest {

	@Mock
	private UserRepositoryGateway userRepositoryGateway;

	@InjectMocks
	private SearchUserUseCase searchUserUseCase;

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
	@DisplayName("Should return user when user exists")
	void searchUserByIdCase1() {
		UserDomain user = new UserDomain();
		when(userRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(user));

		UserDomain userDomain = this.searchUserUseCase.searchUserById(1L);
		verify(userRepositoryGateway, times(1)).findById(anyLong());
		assertThat(userDomain).isEqualTo(user);
	}

	@Test
	@DisplayName("Should return throw exception when user not found")
	void searchUserByIdCase2() {
		when(userRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());

		NotFoundException thrown = assertThrows(NotFoundException.class, () -> this.searchUserUseCase.searchUserById(1L));
		verify(userRepositoryGateway, times(1)).findById(anyLong());
		assertEquals("User not found", thrown.getMessage());
	}

	@Test
	@DisplayName("Should return list when there are users")
	void searchAllCase2() {
		when(this.userRepositoryGateway.findAll()).thenReturn(List.of(new UserDomain()));
		assertThat(this.searchUserUseCase.searchAll()).isNotEmpty();
		verify(userRepositoryGateway, times(1)).findAll();
	}

	@Test
	@DisplayName("Should return empty list when there are no users")
	void searchAllCase1() {
		when(this.userRepositoryGateway.findAll()).thenReturn(List.of());
		assertThat(this.searchUserUseCase.searchAll()).isEmpty();
		verify(userRepositoryGateway, times(1)).findAll();
	}

}