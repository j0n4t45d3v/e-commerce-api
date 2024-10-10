package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;
import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SearchStockMovementUseCaseTest {

	@Mock
	private StockMovementRepositoryGateway stockMovementRepositoryGateway;

	@InjectMocks
	private SearchStockMovementUseCase searchStockMovementUseCase;

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
	@DisplayName("Should search all stock movements")
	void searchAll() {
		var stockMovementDomain = new StockMovementDomain();
		when(this.stockMovementRepositoryGateway.findAll()).thenReturn(List.of(stockMovementDomain));
		var resultSearchAll = this.searchStockMovementUseCase.searchAll();
		verify(stockMovementRepositoryGateway, times(1)).findAll();
		assertNotNull(resultSearchAll);
		assertThat(resultSearchAll).hasSize(1);
	}

	@Test
	@DisplayName("Should search a stock movement by id")
	void searchByIdCase1() {
		var stockMovementDomain = new StockMovementDomain();
		when(this.stockMovementRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(stockMovementDomain));
		var resultSearchById = this.searchStockMovementUseCase.searchById(1L);
		verify(stockMovementRepositoryGateway, times(1)).findById(1L);
		assertNotNull(resultSearchById);
	}

	@Test
	@DisplayName("Should return throw exception when search a stock movement by id")
	void searchByIdCase2() {
		when(this.stockMovementRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
		NotFoundException thrown = assertThrows(NotFoundException.class, () -> this.searchStockMovementUseCase.searchById(1L));
		verify(stockMovementRepositoryGateway, times(1)).findById(1L);
		assertThat(thrown.getMessage()).isEqualTo("Stock movement not found");
	}
}