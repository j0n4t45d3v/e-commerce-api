package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.dto.RemoveStockMovementGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class RemoveStockMovementUseCase implements RemoveStockMovementGateway {
	private final StockMovementRepositoryGateway stockMovementRepositoryGateway;

	public RemoveStockMovementUseCase(StockMovementRepositoryGateway stockMovementRepositoryGateway) {
		this.stockMovementRepositoryGateway = stockMovementRepositoryGateway;
	}

	@Override
	public void execute(Long id) {
		if (this.stockMovementRepositoryGateway.existsById(id)) {
			this.stockMovementRepositoryGateway.deleteById(id);
			return;
		}
		throw new NotFoundException("Stock movement not found");
	}
}
