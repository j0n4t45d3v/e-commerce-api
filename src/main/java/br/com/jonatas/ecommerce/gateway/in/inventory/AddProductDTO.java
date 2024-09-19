package br.com.jonatas.ecommerce.gateway.in.inventory;

public record AddProductDTO(
		String name,
		String description,
		Double price
) {
}
