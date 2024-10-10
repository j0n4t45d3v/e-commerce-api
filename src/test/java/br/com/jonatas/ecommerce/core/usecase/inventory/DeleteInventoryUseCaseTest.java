package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteInventoryUseCaseTest {

	@Mock
	private InventoryRepositoryGateway inventoryRepositoryGateway;

	@InjectMocks
	private DeleteInventoryUseCase deleteInventoryUseCase;

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
	@DisplayName("should delete inventory when inventory exists")
	void executeCase1() {
		when(inventoryRepositoryGateway.existsById(1L)).thenReturn(true);
		this.deleteInventoryUseCase.execute(1L);
		verify(inventoryRepositoryGateway, times(1)).existsById(1L);
		verify(inventoryRepositoryGateway, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("should not delete inventory when inventory does not exist and return exception")
	void executeCase2() {
		when(inventoryRepositoryGateway.existsById(1L)).thenReturn(false);
		var thrown = assertThrows(NotFoundException.class, () -> this.deleteInventoryUseCase.execute(1L));
		verify(inventoryRepositoryGateway, times(1)).existsById(1L);
		verify(inventoryRepositoryGateway, never()).deleteById(1L);
		assertEquals("Inventory not found", thrown.getMessage());
	}

}