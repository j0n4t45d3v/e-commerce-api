package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.CreateStockMovementGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateStockMovementDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;

public class CreateStockMovementUseCase implements CreateStockMovementGateway {

	private final StockMovementRepositoryGateway repositoryGateway;

	public CreateStockMovementUseCase(StockMovementRepositoryGateway repositoryGateway) {
		this.repositoryGateway = repositoryGateway;
	}

	@Override
	public Long execute(CreateStockMovementDTO createStockMovementDTO, Long inventoryId) {
		StockMovementDomain stockMovementDomain = this.toDomain(createStockMovementDTO, inventoryId);
		return this.repositoryGateway.save(stockMovementDomain);
	}

	private StockMovementDomain toDomain(CreateStockMovementDTO createStockMovementDTO, Long inventoryId) {
		InventoryDomain inventoryDomain = new InventoryDomain();
		inventoryDomain.setId(inventoryId);
		return new StockMovementDomain(
				null,
				createStockMovementDTO.quantity(),
				createStockMovementDTO.type(),
				createStockMovementDTO.statusType(),
				createStockMovementDTO.movementDate(),
				createStockMovementDTO.motive(),
				inventoryDomain
		);
	}
}
