package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.core.usecase.user.RegisterUserUseCase;
import br.com.jonatas.ecommerce.gateway.in.crypto.Crypto;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateInventoryDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateInventoryUseCaseTest {

	@Mock
	private InventoryRepositoryGateway inventoryRepositoryGateway;

	@InjectMocks
	private CreateInventoryUseCase createInventoryUseCase;

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
	@DisplayName("should save a new inventory")
	void executeCase1() {
		var createInventoryDTO = new CreateInventoryDTO(1L, 12L, 10L);
		when(inventoryRepositoryGateway.save(any(InventoryDomain.class))).thenReturn(1L);
		var inventoryId = createInventoryUseCase.execute(createInventoryDTO);
		verify(inventoryRepositoryGateway, times(1)).save(any(InventoryDomain.class));
		assertThat(inventoryId)
				.isNotNull()
				.isEqualTo(1L);
	}
}