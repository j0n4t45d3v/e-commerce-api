package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateInventoryDTO;

public interface CreateInventoryGateway {
	Long execute(CreateInventoryDTO createInventoryDTO);
}
