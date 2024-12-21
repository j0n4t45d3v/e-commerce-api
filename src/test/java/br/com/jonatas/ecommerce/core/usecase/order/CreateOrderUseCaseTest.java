package br.com.jonatas.ecommerce.core.usecase.order;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.core.domain.order.Payment;
import br.com.jonatas.ecommerce.core.domain.order.enums.Status;
import br.com.jonatas.ecommerce.gateway.in.order.dto.CreateOrderDto;
import br.com.jonatas.ecommerce.gateway.out.order.OrderRepositoryGateway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateOrderUseCaseTest {

	@Mock
	private OrderRepositoryGateway productRepositoryGateway;

	@InjectMocks
	private CreateOrderUseCase createOrderUseCase;

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
	@DisplayName("should create a new order")
	void execute() {
		var giveOrder = new CreateOrderDto(10.0, Status.APPROVED, new Payment(), new ArrayList<>());
		when(productRepositoryGateway.save(any(OrderDomain.class))).thenReturn(1L);
		this.createOrderUseCase.execute(giveOrder);
		verify(productRepositoryGateway, times(1)).save(any(OrderDomain.class));
	}

}