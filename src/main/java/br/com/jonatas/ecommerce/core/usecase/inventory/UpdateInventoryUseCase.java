package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.UpdateInventoryGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.UpdateInventoryDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class UpdateInventoryUseCase implements UpdateInventoryGateway {

	private final InventoryRepositoryGateway inventoryRepositoryGateway;

	public UpdateInventoryUseCase(InventoryRepositoryGateway inventoryRepositoryGateway) {
		this.inventoryRepositoryGateway = inventoryRepositoryGateway;
	}

	@Override
	public void execute(Long inventoryId, UpdateInventoryDTO updateInventoryDTO) {
		InventoryDomain inventoryDomain = inventoryRepositoryGateway.findById(inventoryId)
				.orElseThrow(() -> new NotFoundException("Inventory not found"));
		this.mapValuesDifferentFromNull(inventoryDomain, updateInventoryDTO);
		inventoryRepositoryGateway.save(inventoryDomain);
	}

	private void mapValuesDifferentFromNull(InventoryDomain inventoryDomain, UpdateInventoryDTO updateInventoryDTO) {
		inventoryDomain.setQuantity(updateInventoryDTO.quantity());
		if (updateInventoryDTO.reservedQuantity() != null) {
			inventoryDomain.setReservedQuantity(updateInventoryDTO.reservedQuantity());
		}
		if (updateInventoryDTO.minimumQuantity() != null) {
			inventoryDomain.setMinimalStock(updateInventoryDTO.minimumQuantity());
		}
	}
}
