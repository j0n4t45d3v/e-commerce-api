package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
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
import static org.mockito.Mockito.*;

class SearchInventoryUseCaseTest {
	@Mock
	private InventoryRepositoryGateway inventoryRepositoryGateway;

	@InjectMocks
	private SearchInventoryUseCase searchInventoryUseCase;

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
	@DisplayName("should return a list of inventory")
	void searchInventory() {
		when(this.inventoryRepositoryGateway.findAll()).thenReturn(List.of(new InventoryDomain()));
		var result = this.searchInventoryUseCase.searchInventory();
		verify(this.inventoryRepositoryGateway, times(1)).findAll();
		assertThat(result)
				.isNotEmpty()
				.hasSize(1);
	}

	@Test
	@DisplayName("should return a inventory by id when found inventory ")
	void searchInventoryByIdCase1() {
		when(this.inventoryRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(new InventoryDomain()));
		var result = this.searchInventoryUseCase.searchInventoryById(1L);
		verify(this.inventoryRepositoryGateway, times(1)).findById(anyLong());
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("should return exception when not found inventory by id")
	void searchInventoryByIdCase2() {
		when(this.inventoryRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
		var thrown = assertThrows(NotFoundException.class, () -> this.searchInventoryUseCase.searchInventoryById(1L));
		verify(this.inventoryRepositoryGateway, times(1)).findById(anyLong());
		assertThat(thrown)
				.isNotNull()
				.isInstanceOf(NotFoundException.class)
				.hasMessage("Inventory not found");}
}