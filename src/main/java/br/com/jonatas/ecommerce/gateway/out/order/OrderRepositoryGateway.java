package br.com.jonatas.ecommerce.gateway.out.order;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.gateway.out.CrudRepositoryGateway;

public interface OrderRepositoryGateway extends CrudRepositoryGateway<OrderDomain, Long> {
}
