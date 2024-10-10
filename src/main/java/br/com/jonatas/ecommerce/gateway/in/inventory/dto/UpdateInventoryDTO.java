package br.com.jonatas.ecommerce.gateway.in.inventory.dto;

public record UpdateInventoryDTO(
		Long quantity,
		Long reservedQuantity,
		Long minimumQuantity
) {
}
