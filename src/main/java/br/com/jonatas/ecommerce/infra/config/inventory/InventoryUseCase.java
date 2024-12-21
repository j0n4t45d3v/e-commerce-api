package br.com.jonatas.ecommerce.infra.config.inventory;

import br.com.jonatas.ecommerce.core.usecase.inventory.AddProductUseCase;
import br.com.jonatas.ecommerce.core.usecase.inventory.EditProductUseCase;
import br.com.jonatas.ecommerce.core.usecase.inventory.RemoveProductUseCase;
import br.com.jonatas.ecommerce.core.usecase.inventory.SearchProductUseCase;
import br.com.jonatas.ecommerce.gateway.in.inventory.AddProductGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.EditProductGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.RemoveProductGateway;
import br.com.jonatas.ecommerce.gateway.in.inventory.SearchProductGateway;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InventoryUseCase {

	@Bean
	public AddProductGateway addProductGateway(ProductRepositoryGateway productRepositoryGateway) {
		return new AddProductUseCase(productRepositoryGateway);
	}


	@Bean
	public SearchProductGateway searchProductGateway(ProductRepositoryGateway productRepositoryGateway) {
		return new SearchProductUseCase(productRepositoryGateway);
	}

	@Bean
	public EditProductGateway editProductGateway(ProductRepositoryGateway productRepositoryGateway) {
		return new EditProductUseCase(productRepositoryGateway);
	}

	@Bean
	public RemoveProductGateway removeProductGateway(ProductRepositoryGateway productRepositoryGateway) {
		return new RemoveProductUseCase(productRepositoryGateway);
	}

}
