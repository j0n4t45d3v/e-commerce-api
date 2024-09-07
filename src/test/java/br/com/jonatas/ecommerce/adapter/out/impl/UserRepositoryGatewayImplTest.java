package br.com.jonatas.ecommerce.adapter.out.impl;

import br.com.jonatas.ecommerce.adapter.out.entity.UserEntity;
import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.out.user.UserRepositoryGateway;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(UserRepositoryGatewayImpl.class)
class UserRepositoryGatewayImplTest {

	@Autowired
	private UserRepositoryGateway userRepository;
	@Autowired
	private EntityManager entityManager;

	@Test
	@DisplayName("Should save user in database")
	void save() {
		var userDomain = new UserDomain();
		userDomain.setUsername("test");
		userDomain.setEmail("test@unit.com");
		userDomain.setPassword("123456");
		var result = this.userRepository.save(userDomain);
		assertNotNull(result.getId());
	}

	@Test
	@DisplayName("Should return all users")
	void findAll() {
		this.createUser();
		var result = this.userRepository.findAll();
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	@DisplayName("Should find user by email")
	void findByEmail() {
		var user = this.createUser();
		var result = this.userRepository.findByEmail("test@unit.com");
		assertTrue(result.isPresent());
		assertEquals(user.getEmail(), result.get().getEmail());
	}

	@Test
	@DisplayName("Should find user by ID")
	void findById() {
		var userEntity = this.createUser();
		var result = this.userRepository.findById(userEntity.getId());
		assertTrue(result.isPresent());
		assertEquals(userEntity.getId(), result.get().getId());
	}

	@Test
	@DisplayName("Should verify if user exists by ID")
	void existsById() {
		var userEntity = this.createUser();
		var exists = this.userRepository.existsById(userEntity.getId());
		assertTrue(exists);
	}

	@Test
	@DisplayName("Should delete user by ID")
	void deleteById() {
		var userEntity = this.createUser();
		this.userRepository.deleteById(userEntity.getId());
		var exists = this.userRepository.existsById(userEntity.getId());
		assertFalse(exists);
	}

	private UserEntity createUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("test@unit.com");
		userEntity.setName("test");
		userEntity.setPassword("123456");
		entityManager.persist(userEntity);
		entityManager.flush();
		return userEntity;
	}

}