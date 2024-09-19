package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.AddProductDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddProductUseCaseTest {

	@Mock
	private ProductRepositoryGateway productRepositoryGateway;

	@InjectMocks
	private AddProductUseCase addProductUseCase;

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
	@DisplayName("should add a new product")
	void execute() {
		var productDto = new AddProductDTO("Smartphone", "A new smartphone", 1000.0);
		when(this.productRepositoryGateway.save(any(ProductDomain.class))).thenReturn(1L);
		var productSaved = this.addProductUseCase.execute(productDto);
		verify(this.productRepositoryGateway, times(1)).save(any(ProductDomain.class));
		assertThat(productSaved)
				.isNotNull()
				.isEqualTo(1L);
	}
}