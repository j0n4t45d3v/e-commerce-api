package br.com.jonatas.ecommerce.gateway.in.order.dto;

import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.core.domain.order.Payment;
import br.com.jonatas.ecommerce.core.domain.order.enums.Status;

import java.util.List;

public record CreateOrderDto(
		Double total,
		Status status,
		Payment payment,
		List<OrderItemDomain> items
) {
}
