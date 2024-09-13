package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.in.user.SearchUserGateway;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

import java.util.List;

public class SearchUserUseCase implements SearchUserGateway {

	private final UserRepositoryGateway userRepositoryGateway;

	public SearchUserUseCase(UserRepositoryGateway userRepositoryGateway) {
		this.userRepositoryGateway = userRepositoryGateway;
	}

	@Override
	public UserDomain searchUserById(Long id) {
		return this.userRepositoryGateway.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	public List<UserDomain> searchAll() {
		return this.userRepositoryGateway.findAll();
	}
}
