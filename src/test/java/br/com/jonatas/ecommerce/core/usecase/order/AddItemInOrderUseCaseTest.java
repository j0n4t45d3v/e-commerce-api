package br.com.jonatas.ecommerce.core.usecase.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.in.order.dto.OrderItemDTO;
import br.com.jonatas.ecommerce.gateway.out.order.OrderItemRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.out.order.OrderRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class AddItemInOrderUseCaseTest {

	@Mock
	private OrderRepositoryGateway orderRepository;

	@Mock
	private SearchProductGateway searchProductGateway;

	@Mock 
	private OrderItemRepositoryGateway orderItemRepository;

	@InjectMocks
	private AddItemInOrderUseCase addItemInOrderUseCase;

	private AutoCloseable closeable;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	@DisplayName("should add item in order selected if order already exists and item not exists in order")
	void testCase1() {
		var itemOfOrder = new OrderItemDTO(1L, 2L, 4);

		var mockOrderDomain = mock(OrderDomain.class);
		when(this.searchProductGateway.searchProductById(anyLong()))
			.thenReturn(mock(ProductDomain.class));
		when(this.orderRepository.findById(anyLong()))
			.thenReturn(Optional.of(mockOrderDomain));
		when(this.orderItemRepository.findByProductId(anyLong()))
			.thenReturn(Optional.empty());

		this.addItemInOrderUseCase.execute(itemOfOrder);

		verify(this.searchProductGateway, times(1)).searchProductById(anyLong());
		verify(this.orderRepository, times(1)).findById(anyLong());
		verify(this.orderItemRepository, times(1)).findByProductId(anyLong());
		verify(this.orderItemRepository, times(1)).save(any(OrderItemDomain.class));
	}

	@Test
	@DisplayName("should update quantity item in order selected when product already exists in order")
	void testCase2() {
		var itemOfOrder = new OrderItemDTO(1L, 2L, 4);
		var mockOrderDomain = mock(OrderDomain.class);
		var mockOrderItemDomain = mock(OrderItemDomain.class);

		when(this.searchProductGateway.searchProductById(anyLong()))
			.thenReturn(mock(ProductDomain.class));
		when(this.orderRepository.findById(anyLong()))
			.thenReturn(Optional.of(mockOrderDomain));
		when(this.orderItemRepository.findByProductId(anyLong()))
			.thenReturn(Optional.of(mockOrderItemDomain));

		this.addItemInOrderUseCase.execute(itemOfOrder);

		verify(this.searchProductGateway, times(1)).searchProductById(anyLong());
		verify(this.orderRepository, times(1)).findById(anyLong());
		verify(this.orderItemRepository, times(1)).findByProductId(anyLong());
		verify(this.orderItemRepository, times(1)).save(any(OrderItemDomain.class));
	}

	@Test
	@DisplayName("should add new item in order when not exists product")
	void testCase3() {
		var itemOfOrder = new OrderItemDTO(1L, 2L, 4);

		doThrow(new NotFoundException("Product not found"))
			.when(this.searchProductGateway)
			.searchProductById(anyLong());

		var thrown = assertThrows(
			NotFoundException.class,
			() -> this.addItemInOrderUseCase.execute(itemOfOrder)
		);
		assertEquals("Product not found", thrown.getMessage());
		assertEquals(404, thrown.getStatus());

		verify(this.searchProductGateway, times(1)).searchProductById(anyLong());
		verify(this.orderRepository, times(0)).findById(anyLong());
		verify(this.orderItemRepository, times(0)).findByProductId(anyLong());
		verify(this.orderItemRepository, times(0)).save(any(OrderItemDomain.class));
	}

	@Test
	@DisplayName("should return exception when order not exists")
	void testCase4() {
		var itemOfOrder = new OrderItemDTO(1L, 2L, 4);

		when(this.searchProductGateway.searchProductById(anyLong()))
			.thenReturn(mock(ProductDomain.class));
		when(this.orderRepository.findById(anyLong()))
			.thenReturn(Optional.empty());

		var thrown = assertThrows(
			NotFoundException.class,
			() -> this.addItemInOrderUseCase.execute(itemOfOrder)
		);
		assertEquals("Not found order selected", thrown.getMessage());
		assertEquals(404, thrown.getStatus());

		verify(this.searchProductGateway, times(1)).searchProductById(anyLong());
		verify(this.orderRepository, times(1)).findById(anyLong());
		verify(this.orderItemRepository, times(0)).findByProductId(anyLong());
		verify(this.orderItemRepository, times(0)).save(any(OrderItemDomain.class));
	}
}
