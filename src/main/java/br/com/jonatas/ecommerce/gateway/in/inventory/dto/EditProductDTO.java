package br.com.jonatas.ecommerce.gateway.in.inventory.dto;

public record EditProductDTO(
	String name,
	String description,
	Double price,
	boolean active
) {
}
