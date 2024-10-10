package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.RemoveProductGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class RemoveProductUseCase implements RemoveProductGateway {

	private final ProductRepositoryGateway productRepositoryGateway;

	public RemoveProductUseCase(ProductRepositoryGateway productRepositoryGateway) {
		this.productRepositoryGateway = productRepositoryGateway;
	}

	@Override
	public void execute(Long id) {
		if (!this.productRepositoryGateway.existsById(id)) {
			throw new NotFoundException("Product not found");
		}
		this.productRepositoryGateway.deleteById(id);
	}
}
