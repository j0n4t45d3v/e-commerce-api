package br.com.jonatas.ecommerce.infra.config.user;

import br.com.jonatas.ecommerce.core.usecase.user.RegisterUserUseCase;
import br.com.jonatas.ecommerce.gateway.in.user.RegisterUserGateway;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.in.crypto.Crypto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserUseCase {
	@Bean
	public RegisterUserGateway userUseCase(UserRepositoryGateway userRepositoryGateway, Crypto crypto) {
		return new RegisterUserUseCase(userRepositoryGateway, crypto);
	}
}
