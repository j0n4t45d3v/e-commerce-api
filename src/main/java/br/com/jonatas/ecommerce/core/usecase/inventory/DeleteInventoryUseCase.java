package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.DeleteInventoryGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class DeleteInventoryUseCase implements DeleteInventoryGateway {
	private final InventoryRepositoryGateway inventoryRepositoryGateway;

	public DeleteInventoryUseCase(InventoryRepositoryGateway inventoryRepositoryGateway) {
		this.inventoryRepositoryGateway = inventoryRepositoryGateway;
	}

	@Override
	public void execute(Long id) {
		if (this.inventoryRepositoryGateway.existsById(id)) {
			this.inventoryRepositoryGateway.deleteById(id);
			return;
		}
		throw new NotFoundException("Inventory not found");

	}
}
