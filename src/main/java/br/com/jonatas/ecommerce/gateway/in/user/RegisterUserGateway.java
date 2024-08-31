package br.com.jonatas.ecommerce.gateway.in.user;

import br.com.jonatas.ecommerce.gateway.in.user.dto.RegisterUserDTO;

public interface RegisterUserGateway {
	void execute(RegisterUserDTO registerUserDTO);
}
