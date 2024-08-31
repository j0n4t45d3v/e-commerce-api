package br.com.jonatas.ecommerce.adapter.out.impl;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryGatewayImpl implements UserRepositoryGateway {

	@Override
	public UserDomain save(UserDomain userDomain) {
		return null;
	}

	@Override
	public Optional<UserDomain> findByEmail(String email) {
		return Optional.empty();
	}

	@Override
	public Optional<UserDomain> findById(long l) {
		return Optional.empty();
	}

	@Override
	public List<UserDomain> findAll() {
		return List.of();
	}
}
