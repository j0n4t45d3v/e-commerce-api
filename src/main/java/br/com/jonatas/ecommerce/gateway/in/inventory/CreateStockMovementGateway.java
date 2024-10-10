package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateStockMovementDTO;

public interface CreateStockMovementGateway {
		Long execute(CreateStockMovementDTO createStockMovementDTO, Long inventoryId);
}
