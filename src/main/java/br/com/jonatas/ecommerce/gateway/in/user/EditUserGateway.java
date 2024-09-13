package br.com.jonatas.ecommerce.gateway.in.user;

import br.com.jonatas.ecommerce.gateway.in.user.dto.EditUserDTO;

public interface EditUserGateway {
		void execute(Long id, EditUserDTO userEdited);
}
