package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
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

class SearchProductUseCaseTest {

	@Mock
	private ProductRepositoryGateway productRepositoryGateway;

	@InjectMocks
	private SearchProductUseCase searchProductUseCase;

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
	@DisplayName("Should return all products")
	void searchAllProducts() {
		when(this.productRepositoryGateway.findAll()).thenReturn(List.of(new ProductDomain()));
		var result = this.searchProductUseCase.searchAllProducts();
		assertThat(result)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
	}

	@Test
	@DisplayName("Should return a product by id")
	void searchProductByIdCase1() {
		when(this.productRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(new ProductDomain()));
		var result = this.searchProductUseCase.searchProductById(1L);
		assertThat(result)
				.isNotNull();
	}

	@Test
	@DisplayName("Should return exception when product not found")
	void searchProductByIdCase2() {
		when(this.productRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
		var thrown = assertThrows(NotFoundException.class, () -> this.searchProductUseCase.searchProductById(1L));
		verify(this.productRepositoryGateway, times(1)).findById(anyLong());
		assertThat(thrown)
				.isNotNull()
				.hasMessage("Product not found");
	}

}