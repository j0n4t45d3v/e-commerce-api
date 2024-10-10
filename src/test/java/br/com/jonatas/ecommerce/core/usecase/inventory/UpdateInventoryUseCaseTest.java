package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.UpdateInventoryDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateInventoryUseCaseTest {
	@Mock
	private InventoryRepositoryGateway inventoryRepositoryGateway;

	@InjectMocks
	private UpdateInventoryUseCase updateInventoryUseCase;

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
	@DisplayName("should updating inventory with fields filled")
	void executeCase1() {
		var inventoryDTO = new UpdateInventoryDTO(12L, 10L, 1L);
		when(inventoryRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(new InventoryDomain()));
		when(inventoryRepositoryGateway.save(any(InventoryDomain.class))).thenReturn(1L);
		this.updateInventoryUseCase.execute(1L, inventoryDTO);
		verify(inventoryRepositoryGateway, times(1)).findById(anyLong());
		verify(inventoryRepositoryGateway, times(1)).save(any(InventoryDomain.class));
	}

	@Test
	@DisplayName("should updating inventory when reserved quantity is null")
	void executeCase2() {
		var inventoryDTO = new UpdateInventoryDTO(12L, null, 1L);
		when(inventoryRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(new InventoryDomain()));
		when(inventoryRepositoryGateway.save(any(InventoryDomain.class))).thenReturn(1L);
		this.updateInventoryUseCase.execute(1L, inventoryDTO);
		verify(inventoryRepositoryGateway, times(1)).findById(anyLong());
		verify(inventoryRepositoryGateway, times(1)).save(any(InventoryDomain.class));
	}

	@Test
	@DisplayName("should updating inventory when minimum quantity is null")
	void executeCase3() {
		var inventoryDTO = new UpdateInventoryDTO(12L, 10L, null);
		when(inventoryRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(new InventoryDomain()));
		when(inventoryRepositoryGateway.save(any(InventoryDomain.class))).thenReturn(1L);
		this.updateInventoryUseCase.execute(1L, inventoryDTO);
		verify(inventoryRepositoryGateway, times(1)).findById(anyLong());
		verify(inventoryRepositoryGateway, times(1)).save(any(InventoryDomain.class));
	}

	@Test
	@DisplayName("should updating inventory when reserved quantity and minimum quantity is null")
	void executeCase4() {
		var inventoryDTO = new UpdateInventoryDTO(12L, null, null);
		when(inventoryRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(new InventoryDomain()));
		when(inventoryRepositoryGateway.save(any(InventoryDomain.class))).thenReturn(1L);
		this.updateInventoryUseCase.execute(1L, inventoryDTO);
		verify(inventoryRepositoryGateway, times(1)).findById(anyLong());
		verify(inventoryRepositoryGateway, times(1)).save(any(InventoryDomain.class));
	}

	@Test
	@DisplayName("should returning exception when inventory not found")
	void executeCase5() {
		var inventoryDTO = new UpdateInventoryDTO(12L, 10L, 1L);
		when(inventoryRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
		when(inventoryRepositoryGateway.save(any(InventoryDomain.class))).thenReturn(1L);
		var thrown = assertThrows(NotFoundException.class, () -> this.updateInventoryUseCase.execute(1L, inventoryDTO));
		verify(inventoryRepositoryGateway, times(1)).findById(anyLong());
		verify(inventoryRepositoryGateway, times(0)).save(any(InventoryDomain.class));
		assertThat(thrown.getMessage()).isEqualTo("Inventory not found");
	}
}