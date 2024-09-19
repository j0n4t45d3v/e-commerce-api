package br.com.jonatas.ecommerce.gateway.in.inventory;

import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditProductDTO;

public interface EditProductGateway {
	void execute(EditProductDTO editProductDTO, Long id);
}
