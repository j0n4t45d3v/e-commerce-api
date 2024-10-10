package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.EditStockMovementGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditStockMovementDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.StockMovementRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class EditStockMovementUseCase implements EditStockMovementGateway {

	private final StockMovementRepositoryGateway repositoryGateway;

	public EditStockMovementUseCase(StockMovementRepositoryGateway repositoryGateway) {
		this.repositoryGateway = repositoryGateway;
	}

	@Override
	public void execute(Long id, EditStockMovementDTO editStockMovementDTO) {
		StockMovementDomain stockMovementDomainFound = this.repositoryGateway.findById(id)
				.orElseThrow(() -> new NotFoundException("Stock movement not found"));
		this.mapValues(stockMovementDomainFound, editStockMovementDTO);
		this.repositoryGateway.save(stockMovementDomainFound);
	}

	private void mapValues(StockMovementDomain stockMovementDomain, EditStockMovementDTO editStockMovementDTO) {
		stockMovementDomain.setQuantity(editStockMovementDTO.quantity());
		stockMovementDomain.setType(editStockMovementDTO.type());
		stockMovementDomain.setMovementStatus(editStockMovementDTO.statusType());
		stockMovementDomain.setMotive(editStockMovementDTO.motive());
	}

}
