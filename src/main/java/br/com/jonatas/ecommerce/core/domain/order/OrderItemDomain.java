package br.com.jonatas.ecommerce.core.domain.order;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;

public class OrderItemDomain {
	private Long id;
	private Integer quantity;
	private Double total;
	private ProductDomain product;

	public OrderItemDomain(Long id, Integer quantity, Double total, ProductDomain product) {
		this.id = id;
		this.quantity = quantity;
		this.total = total;
		this.product = product;
	}

	public OrderItemDomain() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public ProductDomain getProduct() {
		return product;
	}

	public void setProduct(ProductDomain product) {
		this.product = product;
	}
}
