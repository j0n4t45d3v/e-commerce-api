package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.gateway.in.user.DeleteUserGateway;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

public class DeleteUserUseCase implements DeleteUserGateway {

	private final UserRepositoryGateway userRepositoryGateway;

	public DeleteUserUseCase(UserRepositoryGateway userRepositoryGateway) {
		this.userRepositoryGateway = userRepositoryGateway;
	}

	@Override
	public void execute(Long id) {
		if (!this.userRepositoryGateway.existsById(id)) {
			throw new NotFoundException("User not found");
		}
		this.userRepositoryGateway.deleteById(id);
	}
}
