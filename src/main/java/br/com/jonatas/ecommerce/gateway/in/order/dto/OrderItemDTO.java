package br.com.jonatas.ecommerce.gateway.in.order.dto;

public record OrderItemDTO(
	Long idOrder,
	Long idProduct,
	Integer quantity
) {}
