package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;

import java.util.List;

public interface SearchInventoryGateway {
	List<InventoryDomain> searchInventory();
	InventoryDomain searchInventoryById(Long id);
}
