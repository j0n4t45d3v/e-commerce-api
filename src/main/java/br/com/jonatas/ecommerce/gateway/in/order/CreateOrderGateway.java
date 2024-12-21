package br.com.jonatas.ecommerce.gateway.in.order;

import br.com.jonatas.ecommerce.gateway.in.order.dto.CreateOrderDto;

public interface CreateOrderGateway {
	void execute(CreateOrderDto createOrderDto);
}
