package br.com.jonatas.ecommerce.gateway.out.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.InventoryDomain;

import java.util.List;
import java.util.Optional;

public interface InventoryRepositoryGateway {
	Long save(InventoryDomain inventoryDomain);
	Optional<InventoryDomain> findById(Long id);
	boolean existsById(Long id);
	void deleteById(Long id);
	List<InventoryDomain> findAll();
}
