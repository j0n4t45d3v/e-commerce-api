package br.com.jonatas.ecommerce.gateway.in.order;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;

import java.util.List;

public interface SearchOrderGateway {
  List<OrderDomain> all();
  List<OrderItemDomain> items(Long orderId);
  OrderDomain byId(Long orderId);
}
