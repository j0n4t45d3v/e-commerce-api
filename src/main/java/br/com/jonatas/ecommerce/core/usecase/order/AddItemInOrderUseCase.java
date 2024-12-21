package br.com.jonatas.ecommerce.core.usecase.order;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.in.order.AddItemInOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.dto.OrderItemDTO;
import br.com.jonatas.ecommerce.gateway.out.order.OrderItemRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.out.order.OrderRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class AddItemInOrderUseCase implements AddItemInOrderGateway {

  private final OrderRepositoryGateway orderRepositoryGateway;
  private final SearchProductGateway searchProductGateway;
  private final OrderItemRepositoryGateway orderItemRepositoryGateway;

  public AddItemInOrderUseCase(
      OrderRepositoryGateway orderRepositoryGateway,
      SearchProductGateway searchProductGateway,
      OrderItemRepositoryGateway orderItemRepositoryGateway
	) {
    this.orderRepositoryGateway = orderRepositoryGateway;
    this.searchProductGateway = searchProductGateway;
    this.orderItemRepositoryGateway = orderItemRepositoryGateway;
  }

  @Override
  public Long execute(OrderItemDTO orderItemDTO) {
    this.orderExists(orderItemDTO.idOrder());
    ProductDomain product = this.searchProductGateway.searchProductById(orderItemDTO.idProduct());
    OrderItemDomain item = this.getItem(orderItemDTO);
    int quantity = this.getQuantity(orderItemDTO, item);
    item.setTotal(product.getPrice() * quantity);
    item.setQuantity(quantity);
    item.setProduct(product);
    return this.orderItemRepositoryGateway.save(item);
  }

  private void orderExists(Long orderId) {
    this.orderRepositoryGateway
        .findById(orderId)
        .orElseThrow(() -> new NotFoundException("Not found order selected"));
  }

  private OrderItemDomain getItem(OrderItemDTO orderItemDTO) {
    return this.orderItemRepositoryGateway
        .findByProductId(orderItemDTO.idProduct())
        .orElse(new OrderItemDomain());
  }

  private int getQuantity(OrderItemDTO orderItemDTO, OrderItemDomain item) {
    int quantity = orderItemDTO.quantity();
    if (item.getQuantity() != null) {
      quantity += item.getQuantity();
    }
    return quantity;
  }
}
