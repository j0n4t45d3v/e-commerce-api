package br.com.jonatas.ecommerce.adapter.in.controller.order;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.gateway.in.order.AddItemInOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.CreateOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.SearchOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.dto.CreateOrderDto;
import br.com.jonatas.ecommerce.gateway.in.order.dto.OrderItemDTO;
import br.com.jonatas.ecommerce.infra.common.http.ResponseV0;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderControllerV1 {
  private final CreateOrderGateway createOrderGateway;
  private final SearchOrderGateway searchOrderGateway;
  private final AddItemInOrderGateway addItemInOrderGateway;

  public OrderControllerV1(
      CreateOrderGateway createOrderGateway,
      SearchOrderGateway searchOrderGateway,
      AddItemInOrderGateway addItemInOrderGateway
	) {
    this.createOrderGateway = createOrderGateway;
    this.searchOrderGateway = searchOrderGateway;
    this.addItemInOrderGateway = addItemInOrderGateway;
  }

  @PostMapping
  public ResponseEntity<ResponseV0<String>> createOrder(
		@RequestBody CreateOrderDto order
	) {
    this.createOrderGateway.execute(order);
    var message = ResponseV0.of(201, "Order Created");
    return ResponseEntity.status(201).body(message);
  }

  @PutMapping("/items")
  public ResponseEntity<ResponseV0<String>> addItem(@RequestBody OrderItemDTO item) {
    this.addItemInOrderGateway.execute(item);
    var message = ResponseV0.of(200, "Add item in order");
    return ResponseEntity.ok().body(message);
  }

  @GetMapping
  public ResponseEntity<ResponseV0<List<OrderDomain>>> allOrders() {
    var orders = this.searchOrderGateway.all();
    var message = ResponseV0.of(200, orders);
    return ResponseEntity.ok().body(message);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ResponseV0<OrderDomain>> order( @PathVariable("orderId") Long orderId) {
    var orders = this.searchOrderGateway.byId(orderId);
    var message = ResponseV0.of(200, orders);
    return ResponseEntity.ok().body(message);
  }

  @GetMapping("/{orderId}/items")
  public ResponseEntity<ResponseV0<List<OrderItemDomain>>> orderItems(
      @PathVariable("orderId") Long orderId) {
    var orders = this.searchOrderGateway.items(orderId);
    var message = ResponseV0.of(200, orders);
    return ResponseEntity.ok().body(message);
  }
}
