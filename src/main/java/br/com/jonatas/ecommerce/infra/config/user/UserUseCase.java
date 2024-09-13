package br.com.jonatas.ecommerce.infra.config.user;

import br.com.jonatas.ecommerce.core.usecase.user.DeleteUserUseCase;
import br.com.jonatas.ecommerce.core.usecase.user.EditUserUseCase;
import br.com.jonatas.ecommerce.core.usecase.user.RegisterUserUseCase;
import br.com.jonatas.ecommerce.core.usecase.user.SearchUserUseCase;
import br.com.jonatas.ecommerce.gateway.in.user.DeleteUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.EditUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.RegisterUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.SearchUserGateway;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.in.crypto.Crypto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserUseCase {

	@Bean
	public RegisterUserGateway registerUserUseCase(UserRepositoryGateway userRepositoryGateway, Crypto crypto) {
		return new RegisterUserUseCase(userRepositoryGateway, crypto);
	}

	@Bean
	public SearchUserGateway searchUserUseCase(UserRepositoryGateway userRepositoryGateway) {
		return new SearchUserUseCase(userRepositoryGateway);
	}

	@Bean
	public EditUserGateway editUserUseCase(UserRepositoryGateway userRepositoryGateway, Crypto crypto) {
		return new EditUserUseCase(userRepositoryGateway, crypto);
	}

	@Bean
	public DeleteUserGateway deleteUserUseCase(UserRepositoryGateway userRepositoryGateway) {
		return new DeleteUserUseCase(userRepositoryGateway);
	}

}
