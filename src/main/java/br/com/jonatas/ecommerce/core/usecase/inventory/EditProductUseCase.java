package br.com.jonatas.ecommerce.core.usecase.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.EditProductGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditProductDTO;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class EditProductUseCase implements EditProductGateway {
	private final ProductRepositoryGateway productRepositoryGateway;

	public EditProductUseCase(ProductRepositoryGateway productRepositoryGateway) {
		this.productRepositoryGateway = productRepositoryGateway;
	}

	@Override
	public void execute(EditProductDTO editProductDTO, Long id) {
		if (!this.productRepositoryGateway.existsById(id)) {
			throw new NotFoundException("Product not found");
		}
		ProductDomain productDomain = new ProductDomain();
		productDomain.setId(id);
		productDomain.setName(editProductDTO.name());
		productDomain.setDescription(editProductDTO.description());
		productDomain.setPrice(editProductDTO.price());
		productDomain.setActive(editProductDTO.active());

		this.productRepositoryGateway.save(productDomain);
	}
}
