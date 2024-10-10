package br.com.jonatas.ecommerce.gateway.out.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryGateway {
	Long save(ProductDomain productDomain);
	boolean existsById(Long id);
	void deleteById(Long id);
	List<ProductDomain> findAll();
	Optional<ProductDomain> findById(Long id);
}
