package br.com.jonatas.ecommerce.infra.config.order;

import br.com.jonatas.ecommerce.core.usecase.order.AddItemInOrderUseCase;
import br.com.jonatas.ecommerce.core.usecase.order.CreateOrderUseCase;
import br.com.jonatas.ecommerce.core.usecase.order.SearchOrderUseCase;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.in.order.AddItemInOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.CreateOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.SearchOrderGateway;
import br.com.jonatas.ecommerce.gateway.out.order.OrderItemRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.out.order.OrderRepositoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderUseCase {

  @Bean
  public CreateOrderGateway createOrderUseCase(OrderRepositoryGateway orderRepositoryGateway) {
    return new CreateOrderUseCase(orderRepositoryGateway);
  }

  @Bean
  public AddItemInOrderGateway addItemInOrderUseCase(
      OrderRepositoryGateway orderRepositoryGateway,
      SearchProductGateway searchProductGateway,
      OrderItemRepositoryGateway orderItemRepositoryGateway) {
    return new AddItemInOrderUseCase(
        orderRepositoryGateway, searchProductGateway, orderItemRepositoryGateway);
  }

  @Bean
  public SearchOrderGateway searchOrderUseCase(
      OrderRepositoryGateway orderRepositoryGateway,
      OrderItemRepositoryGateway orderItemRepositoryGateway) {
    return new SearchOrderUseCase(orderRepositoryGateway, orderItemRepositoryGateway);
  }
}
