package br.com.jonatas.ecommerce.gateway.out.order;

import java.util.Optional;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.gateway.out.CrudRepositoryGateway;
import java.util.List;

public interface OrderItemRepositoryGateway extends CrudRepositoryGateway<OrderItemDomain, Long>{
	Optional<OrderItemDomain> findByProductId(Long productId);
  List<OrderItemDomain> findAllByOrder(long anyLong);
}
