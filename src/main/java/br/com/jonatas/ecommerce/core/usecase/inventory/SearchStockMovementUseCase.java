package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchStockMovementGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

import java.util.List;

public class SearchStockMovementUseCase implements SearchStockMovementGateway {
	private final StockMovementRepositoryGateway stockMovementRepositoryGateway;

	public SearchStockMovementUseCase(StockMovementRepositoryGateway stockMovementRepositoryGateway) {
		this.stockMovementRepositoryGateway = stockMovementRepositoryGateway;
	}

	@Override
	public List<StockMovementDomain> searchAll() {
		return this.stockMovementRepositoryGateway.findAll();
	}

	@Override
	public StockMovementDomain searchById(Long id) {
		return this.stockMovementRepositoryGateway.findById(id)
				.orElseThrow(() -> new NotFoundException("Stock movement not found"));
	}
}
