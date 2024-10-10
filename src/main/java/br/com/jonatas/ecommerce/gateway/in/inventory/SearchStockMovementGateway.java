package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;

import java.util.List;

public interface SearchStockMovementGateway {
	List<StockMovementDomain> searchAll();
	StockMovementDomain searchById(Long id);
}
