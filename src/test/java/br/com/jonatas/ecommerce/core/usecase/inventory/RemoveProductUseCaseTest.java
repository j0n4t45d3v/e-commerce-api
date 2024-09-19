package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveProductUseCaseTest {
	@Mock
	private ProductRepositoryGateway productRepositoryGateway;

	@InjectMocks
	private RemoveProductUseCase removeProductUseCase;

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
	@DisplayName("should remove one product when it exists")
	void executeCase1() {
		when(this.productRepositoryGateway.existsById(anyLong())).thenReturn(true);
		this.removeProductUseCase.execute(1L);
		verify(this.productRepositoryGateway, times(1)).existsById(anyLong());
		verify(this.productRepositoryGateway, times(1)).deleteById(anyLong());
	}

	@Test
	@DisplayName("should return an exception when the product does not exist")
	void executeCase2() {
		when(this.productRepositoryGateway.existsById(1L)).thenReturn(false);
		var thrown = assertThrows(Exception.class, () -> this.removeProductUseCase.execute(1L));
		verify(this.productRepositoryGateway, times(1)).existsById(anyLong());
		verify(this.productRepositoryGateway, never()).deleteById(anyLong());
		assertThat(thrown)
				.isNotNull()
				.hasMessage("Product not found");
	}
}