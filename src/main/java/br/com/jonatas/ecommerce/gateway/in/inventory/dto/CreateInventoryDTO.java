package br.com.jonatas.ecommerce.gateway.in.inventory.dto;

public record CreateInventoryDTO(
		Long productId,
		Long quantity,
		Long minimalStock
) {
}
