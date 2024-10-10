package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementStatusType;
import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementType;
import br.com.jonatas.ecommerce.gateway.in.inventory.CreateInventoryGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.CreateStockMovementGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateInventoryDTO;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateStockMovementDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;

import java.time.LocalDateTime;

public class CreateInventoryUseCase implements CreateInventoryGateway {

	private final InventoryRepositoryGateway inventoryRepositoryGateway;
	private final SearchProductGateway searchProductGateway;
	private final CreateStockMovementGateway createStockMovementGateway;

	public CreateInventoryUseCase(
			InventoryRepositoryGateway inventoryRepositoryGateway,
			SearchProductGateway searchProductGateway,
			CreateStockMovementGateway createStockMovementGateway
	) {
		this.inventoryRepositoryGateway = inventoryRepositoryGateway;
		this.searchProductGateway = searchProductGateway;
		this.createStockMovementGateway = createStockMovementGateway;
	}

	@Override
	public Long execute(CreateInventoryDTO createInventoryDTO) {
		InventoryDomain inventoryDomain = this.createInventoryDomain(createInventoryDTO);
		Long inventoryId = this.inventoryRepositoryGateway.save(inventoryDomain);
		CreateStockMovementDTO stockMovementDTO = this.createStockMovementDTO(inventoryDomain);
		this.createStockMovementGateway.execute(stockMovementDTO, inventoryId);
		return inventoryId;
	}

	private InventoryDomain createInventoryDomain(CreateInventoryDTO createInventoryDTO) {
		InventoryDomain inventoryDomain = new InventoryDomain();
		ProductDomain productDomain = this.searchProductGateway.searchProductById(createInventoryDTO.productId());
		inventoryDomain.setProduct(productDomain);
		inventoryDomain.setQuantity(createInventoryDTO.quantity());
		inventoryDomain.setMinimalStock(createInventoryDTO.minimalStock());
		inventoryDomain.setReservedQuantity(0L);
		return inventoryDomain;
	}

	private CreateStockMovementDTO createStockMovementDTO(InventoryDomain inventoryDomain) {
		return new CreateStockMovementDTO(
				inventoryDomain.getQuantity(),
				StockMovementType.INPUT,
				StockMovementStatusType.CREATED,
				LocalDateTime.now(),
				"Initial stock"
		);
	}
}
