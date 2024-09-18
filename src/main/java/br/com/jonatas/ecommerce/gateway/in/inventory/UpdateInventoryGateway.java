package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.dto.UpdateInventoryDTO;

public interface UpdateInventoryGateway {
	void execute(Long inventoryId, UpdateInventoryDTO updateInventoryDTO);
}
