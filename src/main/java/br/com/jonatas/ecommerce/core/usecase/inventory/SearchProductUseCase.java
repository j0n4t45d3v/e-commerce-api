package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

import java.util.List;

public class SearchProductUseCase implements SearchProductGateway {

	private final ProductRepositoryGateway productRepositoryGateway;

	public SearchProductUseCase(ProductRepositoryGateway productRepositoryGateway) {
		this.productRepositoryGateway = productRepositoryGateway;
	}

	@Override
	public ProductDomain searchProductById(Long id) {
		return this.productRepositoryGateway.findById(id)
				.orElseThrow(() -> new NotFoundException("Product not found"));
	}

	@Override
	public List<ProductDomain> searchAllProducts() {
		return this.productRepositoryGateway.findAll();
	}
}
