package br.com.jonatas.ecommerce.core.usecase.order;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.gateway.in.order.CreateOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.dto.CreateOrderDto;
import br.com.jonatas.ecommerce.gateway.out.order.OrderRepositoryGateway;

public class CreateOrderUseCase implements CreateOrderGateway {

	private final OrderRepositoryGateway orderRepositoryGateway;

	public CreateOrderUseCase(OrderRepositoryGateway orderRepositoryGateway) {
		this.orderRepositoryGateway = orderRepositoryGateway;
	}

	@Override
	public void execute(CreateOrderDto createOrderDto) {
		OrderDomain orderDomain = OrderDomain.create(createOrderDto);
		this.orderRepositoryGateway.save(orderDomain);
	}
}
