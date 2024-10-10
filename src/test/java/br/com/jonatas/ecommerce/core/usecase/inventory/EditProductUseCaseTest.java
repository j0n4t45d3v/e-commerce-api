package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditProductDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
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

class EditProductUseCaseTest {

	@Mock
	private ProductRepositoryGateway productRepositoryGateway;

	@InjectMocks
	private EditProductUseCase editProductUseCase;

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
	@DisplayName("should edit one product when it exists")
	void executeCase1() {
		var editProductDTO = new EditProductDTO("Smartphone", "A new smartphone", 1000.0, true);
		when(this.productRepositoryGateway.existsById(1L)).thenReturn(true);
		this.editProductUseCase.execute(editProductDTO, 1L);
		verify(this.productRepositoryGateway, times(1)).existsById(1L);
		verify(this.productRepositoryGateway, times(1)).save(any(ProductDomain.class));
	}

	@Test
	@DisplayName("should return an exception when the product does not exist")
	void executeCase2() {
		var editProductDTO = new EditProductDTO("Smartphone", "A new smartphone", 1000.0, true);
		when(this.productRepositoryGateway.existsById(1L)).thenReturn(false);
		var thrown = assertThrows(NotFoundException.class, () -> this.editProductUseCase.execute(editProductDTO, 1L));
		verify(this.productRepositoryGateway, times(1)).existsById(1L);
		verify(this.productRepositoryGateway, never()).save(any(ProductDomain.class));
		assertThat(thrown)
				.isNotNull()
				.hasMessage("Product not found");
	}
}