package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.CreateStockMovementGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateInventoryDTO;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateStockMovementDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateInventoryUseCaseTest {

	@Mock
	private InventoryRepositoryGateway inventoryRepositoryGateway;
	@Mock
	private SearchProductGateway searchProductGateway;
	@Mock
	private CreateStockMovementGateway createStockMovementGateway;

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
		when(searchProductGateway.searchProductById(anyLong())).thenReturn(new ProductDomain());
		when(inventoryRepositoryGateway.save(any(InventoryDomain.class))).thenReturn(1L);
		when(createStockMovementGateway.execute(any(CreateStockMovementDTO.class), anyLong())).thenReturn(1L);
		var inventoryId = createInventoryUseCase.execute(createInventoryDTO);
		verify(searchProductGateway, times(1)).searchProductById(anyLong());
		verify(inventoryRepositoryGateway, times(1)).save(any(InventoryDomain.class));
		verify(createStockMovementGateway, times(1))
				.execute(any(CreateStockMovementDTO.class), anyLong());
		assertThat(inventoryId)
				.isNotNull()
				.isEqualTo(1L);
	}

	@Test
	@DisplayName("should not save a new inventory if product does not exist")
	void executeCase2() {
		var createInventoryDTO = new CreateInventoryDTO(1L, 12L, 10L);
		doThrow(new NotFoundException("Product not found"))
				.when(searchProductGateway)
				.searchProductById(anyLong());

		var thrown = assertThrows(NotFoundException.class, () -> createInventoryUseCase.execute(createInventoryDTO));
		verify(searchProductGateway, times(1)).searchProductById(anyLong());
		verify(inventoryRepositoryGateway, times(0)).save(any(InventoryDomain.class));
		verify(createStockMovementGateway, times(0))
				.execute(any(CreateStockMovementDTO.class), anyLong());
		assertEquals("Product not found", thrown.getMessage());
	}
}