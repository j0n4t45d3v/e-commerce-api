package br.com.jonatas.ecommerce.core.usecase.order;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.gateway.in.order.SearchOrderGateway;
import br.com.jonatas.ecommerce.gateway.out.order.OrderItemRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.out.order.OrderRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import java.util.List;

public class SearchOrderUseCase implements SearchOrderGateway {

  private final OrderRepositoryGateway orderRepositoryGateway;
  private final OrderItemRepositoryGateway orderItemRepositoryGateway;

  public SearchOrderUseCase(
      OrderRepositoryGateway orderRepositoryGateway,
      OrderItemRepositoryGateway orderItemRepositoryGateway) {
    this.orderRepositoryGateway = orderRepositoryGateway;
    this.orderItemRepositoryGateway = orderItemRepositoryGateway;
  }

  @Override
  public List<OrderDomain> all() {
    return this.orderRepositoryGateway.findAll();
  }

  @Override
  public List<OrderItemDomain> items(Long orderId) {
		this.byId(orderId);
    return this.orderItemRepositoryGateway.findAllByOrder(orderId);
  }

  @Override
  public OrderDomain byId(Long orderId) {
    return this.orderRepositoryGateway
        .findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order Not Found"));
  }
}
