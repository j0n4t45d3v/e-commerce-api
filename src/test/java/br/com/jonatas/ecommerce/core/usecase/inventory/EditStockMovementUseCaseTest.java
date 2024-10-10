package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditStockMovementDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementStatusType.CREATED;
import static br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementType.INPUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EditStockMovementUseCaseTest {

	@Mock
	private StockMovementRepositoryGateway stockMovementRepositoryGateway;

	@InjectMocks
	private EditStockMovementUseCase editStockMovementUseCase;

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
	@DisplayName("Should edit a stock movement if already exists")
	void executeCase1() {
		var editObject = new EditStockMovementDTO(12L, INPUT, CREATED, "motive");
		var stockMovementDomain =
				new StockMovementDomain(1L, 12L, INPUT, CREATED, LocalDateTime.now(), "motive", null);
		when(this.stockMovementRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(stockMovementDomain));
		when(this.stockMovementRepositoryGateway.save(any(StockMovementDomain.class))).thenReturn(1L);
		this.editStockMovementUseCase.execute(1L, editObject);
		verify(stockMovementRepositoryGateway, times(1)).findById(anyLong());
		verify(stockMovementRepositoryGateway, times(1)).save(any(StockMovementDomain.class));
	}

	@Test
	@DisplayName("Should not edit a stock movement if not exists")
	void executeCase2() {
		var editObject = new EditStockMovementDTO(12L, INPUT, CREATED, "motive");
		when(this.stockMovementRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
		NotFoundException thrown =
				assertThrows(NotFoundException.class, () -> this.editStockMovementUseCase.execute(1L, editObject));
		verify(stockMovementRepositoryGateway, times(1)).findById(anyLong());
		verify(stockMovementRepositoryGateway, times(0)).save(any(StockMovementDomain.class));
		assertThat(thrown.getMessage()).isEqualTo("Stock movement not found");
	}
}