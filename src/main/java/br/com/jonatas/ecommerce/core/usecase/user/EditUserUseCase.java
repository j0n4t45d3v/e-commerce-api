package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.in.user.EditUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.dto.EditUserDTO;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class EditUserUseCase implements EditUserGateway {

	private final UserRepositoryGateway userRepositoryGateway;

	public EditUserUseCase(UserRepositoryGateway userRepositoryGateway) {
		this.userRepositoryGateway = userRepositoryGateway;
	}

	@Override
	public void execute(Long id, EditUserDTO userEdited) {
		UserDomain userFound = this.userRepositoryGateway.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found"));
		this.setValues(userEdited, userFound);
		this.userRepositoryGateway.save(userFound);
	}

	private void setValues(EditUserDTO userEdited, UserDomain userFound) {
		userFound.setUsername(userEdited.username());
		userFound.setPassword(userEdited.password());
		userFound.setEmail(userEdited.email());
	}
}
