package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateStockMovementDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementStatusType.CREATED;
import static br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementType.INPUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateStockMovementUseCaseTest {

	@Mock
	private StockMovementRepositoryGateway stockMovementRepositoryGateway;

	@InjectMocks
	private CreateStockMovementUseCase createStockMovementUseCase;

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
	@DisplayName("Should create a stock movement")
	void executeCase1() {
		var createObject = new CreateStockMovementDTO(12L, INPUT, CREATED, LocalDateTime.now(), "motive");

		when(this.stockMovementRepositoryGateway.save(any(StockMovementDomain.class))).thenReturn(1L);
		var idStockMovement = this.createStockMovementUseCase.execute(createObject, 1L);
		verify(stockMovementRepositoryGateway, times(1)).save(any(StockMovementDomain.class));

		assertThat(idStockMovement).isEqualTo(1L);
	}

}