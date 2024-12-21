package br.com.jonatas.ecommerce.core.domain.order;

import br.com.jonatas.ecommerce.core.domain.order.enums.Status;
import br.com.jonatas.ecommerce.gateway.in.order.dto.CreateOrderDto;

import java.util.List;

public class OrderDomain {
	private Long id;
	private Double total;
	private Status status;
	private Payment payment;
	private List<OrderItemDomain> items;

	public OrderDomain(Double total, Status status, Payment payment, List<OrderItemDomain> items) {
		this.total = total;
		this.status = status;
		this.payment = payment;
		this.items = items;
	}

	public OrderDomain() {
	}

	public Long getId() {
		return id;
	}

	public Double getTotal() {
		return total;
	}

	public Status getStatus() {
		return status;
	}

	public Payment getPayment() {
		return payment;
	}

	public List<OrderItemDomain> getItems() {
		return items;
	}

	public static OrderDomain create(CreateOrderDto createOrderDto) {
		return new OrderDomain(
				createOrderDto.total(),
				createOrderDto.status(),
				createOrderDto.payment(),
				createOrderDto.items()
		);
	}
}
