package br.com.jonatas.ecommerce.adapter.out.impl;

import br.com.jonatas.ecommerce.adapter.out.UserRepository;
import br.com.jonatas.ecommerce.adapter.out.entity.UserEntity;
import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryGatewayImpl implements UserRepositoryGateway {

	private final UserRepository userRepository;

	@Override
	public UserDomain save(UserDomain userDomain) {
		UserEntity entitySaved = this.userRepository.save(this.domainToEntity(userDomain));
		return this.entityToDomain(entitySaved);
	}

	@Override
	public Optional<UserDomain> findByEmail(String email) {
		return this.userRepository
				.findByEmail(email)
				.map(this::entityToDomain);
	}

	@Override
	public Optional<UserDomain> findById(long l) {
		return this.userRepository
				.findById(l)
				.map(this::entityToDomain);
	}

	@Override
	public List<UserDomain> findAll() {
		return this.userRepository
				.findAll()
				.stream()
				.map(this::entityToDomain)
				.toList();
	}

	@Override
	public boolean existsById(long l) {
		return this.userRepository.existsById(l);
	}

	@Override
	public void deleteById(long l) {
		this.userRepository.deleteById(l);
	}

	private UserDomain entityToDomain(UserEntity userEntity) {
		return new UserDomain(
				userEntity.getId(),
				userEntity.getName(),
				userEntity.getPassword(),
				userEntity.getEmail(),
				userEntity.getRole(),
				userEntity.getBirthdate(),
				userEntity.getCreatedAt(),
				userEntity.getUpdatedAt()
		);
	}

	private UserEntity domainToEntity(UserDomain userDomain) {
		return UserEntity.builder()
				.id(userDomain.getId())
				.name(userDomain.getUsername())
				.email(userDomain.getEmail())
				.password(userDomain.getPassword())
				.role(userDomain.getRole())
				.birthdate(userDomain.getBirthday())
				.build();
	}
}
