package br.com.jonatas.ecommerce.gateway.in.order;

import br.com.jonatas.ecommerce.gateway.in.order.dto.OrderItemDTO;

public interface AddItemInOrderGateway {

	Long execute(OrderItemDTO orderItemDTO);

}
