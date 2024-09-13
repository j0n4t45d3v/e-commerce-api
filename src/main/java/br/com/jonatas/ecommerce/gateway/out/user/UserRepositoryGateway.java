package br.com.jonatas.ecommerce.gateway.out.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryGateway {

	UserDomain save(UserDomain userDomain);
	Optional<UserDomain> findByEmail(String email);
	Optional<UserDomain> findById(long l);
	List<UserDomain> findAll();
	boolean existsById(long l);
	void deleteById(long l);

}
