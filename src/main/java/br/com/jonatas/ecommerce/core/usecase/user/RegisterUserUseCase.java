package br.com.jonatas.ecommerce.core.usecase.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.in.user.RegisterUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.dto.RegisterUserDTO;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.AlreadyExistsException;
import br.com.jonatas.ecommerce.infra.common.exception.BadRequestException;
import br.com.jonatas.ecommerce.gateway.in.crypto.Crypto;

import java.time.LocalDate;

public class RegisterUserUseCase implements RegisterUserGateway {

	private final UserRepositoryGateway userRepositoryGateway;
	private final Crypto crypto;

	public RegisterUserUseCase(UserRepositoryGateway userRepositoryGateway, Crypto crypto) {
		this.userRepositoryGateway = userRepositoryGateway;
		this.crypto = crypto;
	}

	@Override
	public void execute(RegisterUserDTO registerUserDTO) {

		if (this.isEmailAlreadyExists(registerUserDTO.email())) {
			throw new AlreadyExistsException("User already exists");
		}

		if (!this.ageIsGreaterOrEqualThan18Years(registerUserDTO.birthdate())) {
			throw new BadRequestException("Age user is less than 18");
		}

		UserDomain userDomain = this.toDomain(registerUserDTO);
		this.encodePassword(userDomain);
		this.userRepositoryGateway.save(userDomain);
	}

	private boolean isEmailAlreadyExists(String email) {
		return this.userRepositoryGateway.findByEmail(email).isPresent();
	}

	private boolean ageIsGreaterOrEqualThan18Years(LocalDate birthdate) {
		LocalDate now = LocalDate.now();
		LocalDate birthdatePlus18Years = birthdate.plusYears(18);
		return birthdatePlus18Years.isBefore(now) || birthdatePlus18Years.isEqual(now);
	}

	private void encodePassword(UserDomain userDomain) {
		String encodedPassword = this.crypto.encode(userDomain.getPassword());
		userDomain.setPassword(encodedPassword);
	}

	private UserDomain toDomain(RegisterUserDTO registerUserDTO) {
		return new UserDomain(
				registerUserDTO.username(),
				registerUserDTO.password(),
				registerUserDTO.email(),
				registerUserDTO.role(),
				registerUserDTO.birthdate()
		);
	}
}
