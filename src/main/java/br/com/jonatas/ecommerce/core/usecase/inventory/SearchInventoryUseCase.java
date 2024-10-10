package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchInventoryGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

import java.util.List;

public class SearchInventoryUseCase implements SearchInventoryGateway {
	private final InventoryRepositoryGateway inventoryRepositoryGateway;

	public SearchInventoryUseCase(InventoryRepositoryGateway inventoryRepositoryGateway) {
		this.inventoryRepositoryGateway = inventoryRepositoryGateway;
	}

	@Override
	public List<InventoryDomain> searchInventory() {
		return this.inventoryRepositoryGateway.findAll();
	}

	@Override
	public InventoryDomain searchInventoryById(Long id) {
		return this.inventoryRepositoryGateway.findById(id)
				.orElseThrow(() -> new NotFoundException("Inventory not found"));
	}
}
