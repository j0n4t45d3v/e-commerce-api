package br.com.jonatas.ecommerce.gateway.out.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.StockMovementDomain;

import java.util.List;
import java.util.Optional;

public interface StockMovementRepositoryGateway {
	Long save(StockMovementDomain domainObject);
	Optional<StockMovementDomain> findById(Long id);
	boolean existsById(Long id);
	void deleteById(Long l);
	List<StockMovementDomain> findAll();
}
