package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveStockMovementUseCaseTest {
	@Mock
	private StockMovementRepositoryGateway stockMovementRepositoryGateway;

	@InjectMocks
	private RemoveStockMovementUseCase removeStockMovementUseCase;

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
	@DisplayName("should remove stock movement if movement exists")
	void executeCase1() {
		when(this.stockMovementRepositoryGateway.existsById(1L)).thenReturn(true);
		doNothing().when(this.stockMovementRepositoryGateway).deleteById(anyLong());
		this.removeStockMovementUseCase.execute(1L);
		verify(this.stockMovementRepositoryGateway, times(1)).existsById(1L);
		verify(this.stockMovementRepositoryGateway, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("should not remove stock movement if movement does not exist")
	void executeCase2() {
		when(this.stockMovementRepositoryGateway.existsById(1L)).thenReturn(false);
		doNothing().when(this.stockMovementRepositoryGateway).deleteById(anyLong());
		NotFoundException thrown = assertThrows(NotFoundException.class, () -> this.removeStockMovementUseCase.execute(1L));
		verify(this.stockMovementRepositoryGateway, times(1)).existsById(1L);
		verify(this.stockMovementRepositoryGateway, times(0)).deleteById(1L);
		assertThat(thrown.getMessage()).isEqualTo("Stock movement not found" );
	}
}