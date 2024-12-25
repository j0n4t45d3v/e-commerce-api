package br.com.jonatas.ecommerce.gateway.out.order;

import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.gateway.out.CrudRepositoryGateway;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepositoryGateway extends CrudRepositoryGateway<OrderItemDomain, Long> {
  Optional<OrderItemDomain> findByProductId(Long productId);
  List<OrderItemDomain> findAllByOrder(long anyLong);
}
