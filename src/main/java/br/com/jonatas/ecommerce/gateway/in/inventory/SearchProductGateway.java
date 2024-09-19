package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;

import java.util.List;

public interface SearchProductGateway {
		ProductDomain searchProductById(Long id);
		List<ProductDomain> searchAllProducts();
}
