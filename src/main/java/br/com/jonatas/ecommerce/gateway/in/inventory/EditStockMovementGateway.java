package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditStockMovementDTO;

public interface EditStockMovementGateway {
		void execute(Long id, EditStockMovementDTO editStockMovementDTO);
}
