package br.com.jonatas.ecommerce.gateway.in.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;

import java.util.List;

public interface SearchUserGateway {
		UserDomain searchUserById(Long id);
		List<UserDomain> searchAll();
}
