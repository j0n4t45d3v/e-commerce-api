package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.AddProductDTO;
import br.com.jonatas.ecommerce.gateway.in.inventory.AddProductGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;

public class AddProductUseCase implements AddProductGateway {

	private final ProductRepositoryGateway productRepositoryGateway;

	public AddProductUseCase(ProductRepositoryGateway productRepositoryGateway) {
		this.productRepositoryGateway = productRepositoryGateway;
	}

	@Override
	public Long execute(AddProductDTO addProductDTO) {
		ProductDomain productDomain = new ProductDomain();
		productDomain.setName(addProductDTO.name());
		productDomain.setDescription(addProductDTO.description());
		productDomain.setPrice(addProductDTO.price());
		productDomain.setActive(true);
		return this.productRepositoryGateway.save(productDomain);
	}
}
