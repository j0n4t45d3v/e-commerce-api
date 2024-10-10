package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;
import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.CreateInventoryGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.CreateInventoryDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.InventoryRepositoryGateway;

public class CreateInventoryUseCase implements CreateInventoryGateway {

	private final InventoryRepositoryGateway inventoryRepositoryGateway;
	private final SearchProductGateway searchProductGateway;

	public CreateInventoryUseCase(
			InventoryRepositoryGateway inventoryRepositoryGateway,
			SearchProductGateway searchProductGateway
	) {
		this.inventoryRepositoryGateway = inventoryRepositoryGateway;
		this.searchProductGateway = searchProductGateway;
	}

	@Override
	public Long execute(CreateInventoryDTO createInventoryDTO) {
		InventoryDomain inventoryDomain = this.createInventoryDomain(createInventoryDTO);
		return inventoryRepositoryGateway.save(inventoryDomain);
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
}
